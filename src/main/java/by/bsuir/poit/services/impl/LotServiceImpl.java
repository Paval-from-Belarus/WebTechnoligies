package by.bsuir.poit.services.impl;

import by.bsuir.poit.bean.DeliveryPoint;
import by.bsuir.poit.bean.EnglishLot;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.context.Service;
import by.bsuir.poit.dao.DeliveryPointDao;
import by.bsuir.poit.dao.LotDao;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.dao.exception.DataModifyingException;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import by.bsuir.poit.servlets.UserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.Principal;
import java.util.List;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService {
private static final Logger LOGGER = LogManager.getLogger(LotServiceImpl.class);
private final LotDao lotDao;
private final DeliveryPointDao deliveryPointDao;

@Override
public List<Lot> findAllBySellerId(long clientId) throws ResourceBusyException {
      try {
	    return lotDao.findAllBySellerId(clientId);
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to fetch lot by seller id {}", clientId);
	    throw new ResourceBusyException(e);
      }

}

@Override
public List<Lot> findAllByStatus(short status) {
      try {
	    return lotDao.findAllByStatus(status);
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to fetch lot by status={}", status);
	    throw new ResourceBusyException(e);
      }
}

@Override
public List<Lot> findAllByAuction(long auctionId) throws ResourceBusyException {
      try {
	    return lotDao.findAllByAuctionId(auctionId);
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to fetch lot by auction id {}", auctionId);
	    throw new ResourceBusyException(e);
      }

}

@Override
public EnglishLot findEnglishLot(long lotId) throws ResourceNotFoundException {
      try {
	    return lotDao.findEnglishLotById(lotId).orElseThrow(() -> newLotNotFoundException(lotId));
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceNotFoundException(e);
      }
}

@Override
public DeliveryPoint findDeliveryPointByLot(long lotId) throws ResourceNotFoundException {
      try {
	    Lot lot = lotDao.findById(lotId).orElseThrow(() -> newLotNotFoundException(lotId));
	    Long deliveryPointId = lot.getDeliveryPointId();
	    if (deliveryPointId == null) {
		  throw newDeliveryPointNotFoundException(lotId, "lot holds null for delivery point");
	    }
	    return deliveryPointDao.findById(deliveryPointId).orElseThrow(() -> newDeliveryPointNotFoundException(lotId, "dao failed to find delivery point"));
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceNotFoundException(e);
      }
}

@Override
public void save(Principal principal, Lot lot) throws ResourceModifyingException {
      try {
	    UserDetails details = (UserDetails) principal;
	    assert details != null;
	    lot.setStatus(Lot.BEFORE_AUCTION_STATUS);
	    lot.setSellerId(details.id());
	    lotDao.save(lot);
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
	    lotDao.delete(lotId);
      } catch (DataModifyingException e) {
	    isDeleted = false;
      } catch (DataAccessException e) {
	    LOGGER.error("Failed to try delete lot by id={}", lotId);
	    throw new ResourceBusyException(e);
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
