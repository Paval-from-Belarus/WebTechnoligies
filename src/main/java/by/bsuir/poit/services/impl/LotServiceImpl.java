package by.bsuir.poit.services.impl;

import by.bsuir.poit.bean.ClientFeedback;
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
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public List<Lot> findAllByAuction(long auctionId) throws ResourceBusyException {
      try {
	    return lotDao.findAllByAuctionId(auctionId);
      } catch (DataModifyingException e) {
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
      } catch (DataModifyingException e) {
	    LOGGER.error(e);
	    throw new ResourceNotFoundException(e);
      }
}

@Override
public void saveLot(Lot lot) throws ResourceModifyingException {
      try {
	    lotDao.save(lot);
      } catch (DataAccessException e) {
	    LOGGER.error(e);
	    throw new ResourceModifyingException(e);
      }
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
