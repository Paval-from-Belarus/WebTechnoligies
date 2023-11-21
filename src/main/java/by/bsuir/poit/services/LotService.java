package by.bsuir.poit.services;

import by.bsuir.poit.bean.DeliveryPoint;
import by.bsuir.poit.bean.EnglishLot;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface LotService {
List<Lot> findAllBySellerId(long clientId) throws ResourceBusyException;

List<Lot> findAllByStatus(short status);

List<Lot> findAllByAuction(long auctionId) throws ResourceBusyException;

EnglishLot findEnglishLot(long lotId) throws ResourceNotFoundException;

DeliveryPoint findDeliveryPointByLot(long lotId) throws ResourceNotFoundException;

void save(Lot lot) throws ResourceModifyingException;

void updateLotAuction(long lotId, long auctionId) throws ResourceNotFoundException;

void updateLotStatus(long lotId, short lotStatus) throws ResourceNotFoundException;

void updateLotCustomer(long lotId, long customerId) throws ResourceNotFoundException;

void updateLotDeliveryPoint(long lotId, long deliveryPointId) throws ResourceNotFoundException;

boolean deleteIfPossible(long lotId) throws ResourceModifyingException;
}
