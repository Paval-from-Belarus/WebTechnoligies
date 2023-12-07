package by.bsuir.poit.services.impl;

import by.bsuir.poit.dao.DeliveryPointRepository;
import by.bsuir.poit.dao.LotRepository;
import by.bsuir.poit.dto.DeliveryPointDto;
import by.bsuir.poit.dto.LotDto;
import by.bsuir.poit.dto.mappers.DeliveryPointMapper;
import by.bsuir.poit.dto.mappers.LotMapper;
import by.bsuir.poit.model.EnglishLot;
import by.bsuir.poit.model.Lot;
import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService {
private static final Logger LOGGER = LogManager.getLogger(LotServiceImpl.class);
private final LotRepository lotDao;
private final DeliveryPointRepository deliveryPointRepository;
private final AuthorizationService authorizationService;
private final LotMapper lotMapper;
private final DeliveryPointMapper deliveryPointMapper;

@Override
public List<LotDto> findAllBeforeAuctionLots() {
      try {
	    List<Lot> lots = lotDao.findAllByStatusOrderByStartPriceDesc(Lot.BEFORE_AUCTION_STATUS);
	    return lots.stream()
		       .map(lotMapper::toDto)
		       .toList();
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to fetch lots by before_auction status");
	    throw new ResourceBusyException(e);
      }
}

@Override
public List<LotDto> findAllBySellerId(long clientId) throws ResourceBusyException {
      try {
	    return lotDao.findAllBySellerClientId(clientId).stream()
		       .map(lotMapper::toDto)
		       .toList();
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to fetch lot by seller id {}", clientId);
	    throw new ResourceBusyException(e);
      }

}

@Override
public List<LotDto> findAllByStatus(short status) {
      try {
	    return lotDao.findAllByStatus(status).stream()
		       .map(lotMapper::toDto)
		       .toList();
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to fetch lot by status={}", status);
	    throw new ResourceBusyException(e);
      }
}

@Override
public List<LotDto> findAllByAuction(long auctionId) throws ResourceBusyException {
      try {
	    return lotDao.findAllByAuctionId(auctionId).stream()
		       .map(lotMapper::toDto)
		       .collect(Collectors.toList());
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to fetch lot by auction id {}", auctionId);
	    throw new ResourceBusyException(e);
      }

}

@Override
public LotDto findEnglishLot(long lotId) throws ResourceNotFoundException {
      LotDto dto;
      try {
	    EnglishLot englishLot = lotDao.findEnglishWithLotById(lotId)
					.orElseThrow(() -> newLotNotFoundException(lotId));
	    dto = lotMapper.toEnglishLotDto(englishLot);
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceNotFoundException(e);
      }
      return dto;
}

@Override
public DeliveryPointDto findDeliveryPointByLot(long lotId) throws ResourceNotFoundException {
      try {
	    Lot lot = lotDao.findWithDeliveryPoint(lotId).orElseThrow(() -> newLotNotFoundException(lotId));
	    if (lot.getDeliveryPoint() == null) {
		  throw newDeliveryPointNotFoundException(lotId, "lot holds null for delivery point");
	    }
	    return deliveryPointMapper.toDto(lot.getDeliveryPoint());
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceBusyException(e);
      }
}

@Override
public void save(Principal principal, LotDto lot) throws ResourceModifyingException {
      try {
	    long userId = authorizationService.getUserIdByPrincipal(principal);
	    Lot entity = lotMapper.toEntity(lot);
	    entity.setStatus(Lot.BEFORE_AUCTION_STATUS);
	    lot.setSellerId(userId);
	    lotDao.save(entity);
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceModifyingException(e);
      }
}

@Override
public void updateLotAuction(long lotId, long auctionId) throws ResourceNotFoundException {
      try {
	    lotDao.setAuctionId(lotId, auctionId);
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceNotFoundException(e);
      }
}

@Override
public void updateLotStatus(long lotId, short lotStatus) throws ResourceNotFoundException {
      try {
	    lotDao.setLotStatus(lotId, lotStatus);
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceNotFoundException(e);
      }

}

@Override
public void updateLotCustomer(long lotId, long customerId) throws ResourceNotFoundException {
      try {
	    lotDao.setCustomerId(lotId, customerId);
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceNotFoundException(e);
      }
}

@Override
public void updateLotDeliveryPoint(long lotId, long deliveryPointId) throws ResourceNotFoundException {
      try {
	    lotDao.setDeliveryPointId(lotId, deliveryPointId);
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceNotFoundException(e);
      }
}

@Override
public boolean deleteIfPossible(long lotId) throws ResourceModifyingException {
      boolean isDeleted = true;
      try {
	    lotDao.deleteById(lotId);
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to try delete lot by id={}", lotId);
	    isDeleted = false;
      }
      return isDeleted;
}


private ResourceNotFoundException newLotNotFoundException(long lotId) {
      final String msg = String.format("THe lot not found by id=%s", lotId);
      LOGGER.error(msg);
      return new ResourceNotFoundException(msg);
}

private ResourceNotFoundException newDeliveryPointNotFoundException(long lotId, String message) {
      final String msg = String.format("Failed to find delivery point for given lot=%d with message = %s", lotId, message);
      LOGGER.error(msg);
      return new ResourceNotFoundException(msg);
}

}
