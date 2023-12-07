package by.bsuir.poit.services.impl;

import by.bsuir.poit.dto.*;
import by.bsuir.poit.context.Service;
import by.bsuir.poit.dao.*;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.services.AuctionService;
import by.bsuir.poit.services.exception.authorization.UserAccessViolationException;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import by.bsuir.poit.servlets.UserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Paval Shlyk
 * @since 20/11/2023
 */
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
private static final Logger LOGGER = LogManager.getLogger(AuctionServiceImpl.class);
private final AuctionDao auctionDao;
private final AuctionMemberDao auctionMemberDao;
private final AuctionBetDao auctionBetDao;
private final AuctionTypeDao auctionTypeDao;
private final LotDao lotDao;

@Override
public List<AuctionDto> findAfterEventDate(Date date) {
      List<AuctionDto> auctions;
      try {
	    auctions = auctionDao.findAllAfterEventDate(date);
      } catch (DataAccessException e) {
	    final String msg = String.format("Failed to find auctions after event date=%s", date.toString());
	    LOGGER.error(msg);
	    throw new ResourceBusyException(msg);
      }
      return auctions;
}

@Override
public List<AuctionDto> findByClientId(long clientId) {
      List<AuctionDto> auctions;
      try {
	    List<AuctionMemberDto> members = auctionMemberDao.findAllByClientId(clientId);
	    auctions = new ArrayList<>();
	    for (AuctionMemberDto member : members) {
		  AuctionDto auction = auctionDao
					.findById(member.getAuctionId())
					.orElseThrow(() -> newAuctionNotFoundException(member.getAuctionId()));
		  auctions.add(auction);
	    }
      } catch (DataAccessException e) {
	    final String msg = String.format("Failed to find auctions by given clientId=%d", clientId);
	    LOGGER.error(msg);
	    throw new ResourceBusyException(msg);
      }
      return auctions;
}

@Override
public AuctionDto findById(long auctionId) throws ResourceNotFoundException {
      AuctionDto auction;
      try {
	    auction = auctionDao.findById(auctionId).orElseThrow(() -> newAuctionNotFoundException(auctionId));
      } catch (DataAccessException e) {
	    LOGGER.error("Failed find auction by id={}", auctionId);
	    throw new ResourceBusyException(e);
      }
      return auction;
}

@Override
public List<AuctionBetDto> findAllBets(long auctionId) throws ResourceNotFoundException {
      List<AuctionBetDto> bets;
      try {
	    bets = auctionBetDao.findAllByAuctionId(auctionId);
      } catch (DataAccessException e) {
	    LOGGER.error("Failed find bets by given auctionId={}", auctionId);
	    throw new ResourceBusyException(e);
      }
      return bets;
}

@Override
public List<AuctionBetDto> findAllBetsByClientId(long auctionId, long clientId) throws ResourceNotFoundException {
      List<AuctionBetDto> bets;
      try {
	    bets = auctionBetDao.findAllByAuctionIdAndClientId(auctionId, clientId);
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to find bets by clientId={} and auctionId={}", clientId, auctionId);
	    throw new ResourceBusyException(e);
      }
      return bets;
}

@Override
public AuctionTypeDto findTypeByAuctionId(long auctionId) throws ResourceNotFoundException {
      AuctionTypeDto type;
      try {
	    AuctionDto auction = auctionDao.findById(auctionId).orElseThrow(() -> newAuctionNotFoundException(auctionId));
	    type = auctionTypeDao.findById(auction.getAuctionTypeId()).orElseThrow(() -> newAuctionTypeNotFoundException(auction.getAuctionTypeId()));
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to find auction type by given actionId={}", auctionId);
	    throw new ResourceBusyException(e);
      }
      return type;
}

@Override
public List<AuctionDto> findHeadersByAdminId(long adminId) throws ResourceBusyException {
      try {
	    return auctionDao.findHeadersAllByAdminIdSortedByEventDateDesc(adminId);
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to fetch all auction by adminId={}", adminId);
	    throw new ResourceBusyException(e);
      }
}

@Override
public List<AuctionTypeDto> findAllTypes() {
      try {
	    return auctionTypeDao.findAll();
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to find all auction type");
	    throw new ResourceBusyException(e);
      }
}

@Override
//@Transactional
public void assignLot(Principal principal, long auctionId, long lotId) throws UserAccessViolationException, ResourceModifyingException {
      try {
	    //current no check
	    UserDetails details = (UserDetails) principal;
	    if (details.role() != UserDto.ADMIN) {
		  final String msg = String.format("Assign lot to auction can only admin. Not user with id = %s", details.role());
		  LOGGER.info(msg);
		  throw new UserAccessViolationException(msg);
	    }
	    lotDao.assignLotWithStatusToAuction(lotId, LotDto.AUCTION_STATUS, auctionId);
      } catch (DataAccessException e) {
	    LOGGER.info("Failed to update auctionId={} and auction status for given auction={}", auctionId, lotId);
	    throw new ResourceBusyException(e);
      }
}

@Override
public void saveAuction(Principal principal, AuctionDto auction) throws ResourceModifyingException {
      try {
	    UserDetails details = (UserDetails) principal;
	    auction.setAdminId(details.id());
	    auctionDao.save(auction);
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to save auction {}", auction);
	    throw new ResourceModifyingException(e);
      }
}

@Override
//@Transactional
public void saveBet(Principal principal, AuctionBetDto bet) throws ResourceNotFoundException {
      if (bet.getLotId() == null || bet.getAuctionId() == null) {
	    final String msg = String.format("Failed to save bet because on of the bet field is null: %s", bet);
	    LOGGER.warn(msg);
	    throw new ResourceModifyingException(msg);
      }
      UserDetails details = (UserDetails) principal;
      bet.setClientId(details.id());
      try {
	    LotDto lot = lotDao.findById(bet.getLotId()).orElseThrow(() -> newLotNotFoundException(bet.getLotId()));
	    if (lot.getActualPrice() != null && lot.getActualPrice() > bet.getBet()) {
		  throw newIllegalBetValue(bet);
	    }
	    auctionBetDao.save(bet);
	    lotDao.setActualPrice(lot.getId(), bet.getBet());
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to save auction bet {}", bet);
	    throw new ResourceModifyingException(e);
      }
}

private ResourceModifyingException newIllegalBetValue(AuctionBetDto bet) {
      final String msg = String.format("The given bet has invalid value %s", bet);
      LOGGER.info(msg);
      throw new ResourceModifyingException(msg);

}

private ResourceNotFoundException newLotNotFoundException(long lotId) {
      final String msg = String.format("Failed to find lot by given id=%d", lotId);
      LOGGER.info(msg);
      throw new ResourceNotFoundException(msg);

}

private ResourceNotFoundException newAuctionTypeNotFoundException(long auctionTypeId) {
      final String msg = String.format("Failed to find auction type by given id=%d", auctionTypeId);
      LOGGER.info(msg);
      throw new ResourceNotFoundException(msg);

}

private ResourceNotFoundException newAuctionNotFoundException(long auctionId) {
      final String msg = String.format("Failed to find auction by given id=%d", auctionId);
      LOGGER.info(msg);
      return new ResourceNotFoundException(msg);
}
}
