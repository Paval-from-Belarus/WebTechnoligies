package by.bsuir.poit.services.impl;

import by.bsuir.poit.dao.*;
import by.bsuir.poit.dto.*;
import by.bsuir.poit.dto.mappers.AuctionBetMapper;
import by.bsuir.poit.dto.mappers.AuctionMapper;
import by.bsuir.poit.dto.mappers.AuctionTypeMapper;
import by.bsuir.poit.model.*;
import by.bsuir.poit.services.AuctionService;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.services.exception.authorization.UserAccessViolationException;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

/**
 * @author Paval Shlyk
 * @since 20/11/2023
 */
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
private static final Logger LOGGER = LogManager.getLogger(AuctionServiceImpl.class);
private final AuctionRepository auctionRepository;
private final AuctionMemberRepository auctionMemberRepository;
private final AuctionBetRepository auctionBetRepository;
private final AuctionTypeRepository auctionTypeDao;
private final LotRepository lotRepository;
private final AuctionMapper auctionMapper;
private final AuctionBetMapper auctionBetMapper;
private final AuctionTypeMapper auctionTypeMapper;
private final AuthorizationService authorizationService;
private final UserRepository userDao;
private final ClientRepository clientRepository;

@Override
public List<AuctionDto> findAfterEventDate(Date date) {
      List<AuctionDto> list;
      try {
	    List<Auction> auctions = auctionRepository.findAllAfterEventDate(date);
	    list = auctions.stream()
		       .map(auctionMapper::toDto)
		       .toList();
      } catch (DataAccessException e) {
	    final String msg = String.format("Failed to find auctions after event date=%s", date.toString());
	    LOGGER.error(msg);
	    throw new ResourceBusyException(msg);
      }
      return list;
}

@Override
@Transactional //to prevent lazy exception
public List<AuctionDto> findByClientId(long clientId) {
      List<AuctionDto> auctions;
      try {
	    List<AuctionMember> members = auctionMemberRepository.findAllByClientId(clientId);
	    auctions = members.stream()
			   .map(AuctionMember::getAuction)
			   .map(auctionMapper::toDto)
			   .toList();
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
	    auction = auctionRepository.findById(auctionId)
			  .map(auctionMapper::toDto)
			  .orElseThrow(() -> newAuctionNotFoundException(auctionId));
      } catch (DataAccessException e) {
	    LOGGER.error("Failed find auction by id={}", auctionId);
	    throw new ResourceBusyException(e);
      }
      return auction;
}

@Override
@Transactional
public List<AuctionBetDto> findAllBets(long auctionId) throws ResourceNotFoundException {
      List<AuctionBetDto> bets;
      try {
	    Auction auction = auctionRepository.findById(auctionId)
				  .orElseThrow(() -> newAuctionNotFoundException(auctionId));
	    bets = auction.getBets().stream()
		       .map(auctionBetMapper::toDto)
		       .toList();
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
	    List<AuctionBet> auctionBets = auctionBetRepository.findAllByAuctionIdAndClientId(auctionId, clientId);
	    bets = auctionBets.stream()
		       .map(auctionBetMapper::toDto)
		       .toList();
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to find bets by clientId={} and auctionId={}", clientId, auctionId);
	    throw new ResourceBusyException(e);
      }
      return bets;
}

@Override
//not transactional because auction type fetching is eager
public AuctionTypeDto findTypeByAuctionId(long auctionId) throws ResourceNotFoundException {
      AuctionTypeDto type;
      try {
	    Auction auction = auctionRepository.findById(auctionId)
				  .orElseThrow(() -> newAuctionNotFoundException(auctionId));
	    type = auctionTypeMapper.toDto(auction.getAuctionType());
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to find auction type by given actionId={}", auctionId);
	    throw new ResourceBusyException(e);
      }
      return type;
}

@Override
public List<AuctionDto> findHeadersByPrincipal(long adminId) throws ResourceBusyException {
      List<AuctionDto> dto;
      try {
	    List<Auction> auctions = auctionRepository.findHeadersAllByAdminIdOrderByEndDateDesc(adminId);
	    dto = auctions.stream()
		      .map(auctionMapper::toDto)
		      .toList();
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to fetch all auction by adminId={}", adminId);
	    throw new ResourceBusyException(e);
      }
      return dto;
}

@Override
public List<AuctionTypeDto> findAllTypes() {
      try {
	    return auctionTypeDao.findAll().stream()
		       .map(auctionTypeMapper::toDto)
		       .toList();
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to find all auction type");
	    throw new ResourceBusyException(e);
      }
}

@Override
public void assignLot(Principal principal, long auctionId, long lotId) throws UserAccessViolationException, ResourceModifyingException {
      try {
	    authorizationService.verifyByUserRole(principal, User.ADMIN);
	    lotRepository.setAuctionId(lotId, auctionId);
      } catch (DataAccessException e) {
	    LOGGER.info("Failed to update auctionId={} and auction status for given auction={}", auctionId, lotId);
	    throw new ResourceBusyException(e);
      }
}

@Override
@Transactional
public void saveAuction(Principal principal, AuctionDto dto) throws ResourceModifyingException {
      authorizationService.verifyByUserRole(principal, User.ADMIN);
      try {
	    long adminId = authorizationService.getUserIdByPrincipal(principal);
	    Auction auction = auctionMapper.toEntity(dto);
	    auction.setAdmin(userDao.getReferenceById(adminId));
	    auction.setAuctionType(auctionTypeDao.getReferenceById(dto.getAuctionTypeId()));
	    auctionRepository.save(auction);
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to save auction {}", dto);
	    throw new ResourceModifyingException(e);
      }
}
//the whole method should be replaced by stored routine in a database
@Override
@Transactional
public void saveBet(Principal principal, AuctionBetDto bet) throws ResourceNotFoundException {
      authorizationService.verifyByUserRole(principal, User.CLIENT);//only client can do such things -> or do such check on db sice
      long clientId = authorizationService.getUserIdByPrincipal(principal);
      try {
	    Lot lot = lotRepository.findById(bet.getLotId()).orElseThrow(() -> newLotNotFoundException(bet.getLotId()));
	    if (lot.getAuctionPrice() != null && lot.getAuctionPrice() > bet.getBet()) {
		  throw newIllegalBetValue(bet);
	    }
	    AuctionBet entity = auctionBetMapper.toEntity(bet);
	    entity.setAuction(auctionRepository.getReferenceById(bet.getAuctionId()));
	    entity.setLot(lotRepository.getReferenceById(bet.getLotId()));
	    entity.setClient(clientRepository.getReferenceById(clientId));
	    auctionBetRepository.save(entity);
	    lotRepository.setActualPrice(lot.getId(), bet.getBet());
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
