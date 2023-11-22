package by.bsuir.poit.services;

import by.bsuir.poit.bean.DeliveryPoint;
import by.bsuir.poit.bean.EnglishLot;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import jakarta.validation.constraints.NotNull;

import java.security.Principal;
import java.util.List;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface LotService {
List<Lot> findAllBeforeAuctionLots();

List<Lot> findAllBySellerId(long clientId) throws ResourceBusyException;

List<Lot> findAllByStatus(short status);

List<Lot> findAllByAuction(long auctionId) throws ResourceBusyException;

EnglishLot findEnglishLot(long lotId) throws ResourceNotFoundException;

DeliveryPoint findDeliveryPointByLot(long lotId) throws ResourceNotFoundException;

void save(@NotNull Principal principal, Lot lot) throws ResourceModifyingException;

void updateLotAuction(long lotId, long auctionId) throws ResourceNotFoundException;

void updateLotStatus(long lotId, short lotStatus) throws ResourceNotFoundException;

void updateLotCustomer(long lotId, long customerId) throws ResourceNotFoundException;

void updateLotDeliveryPoint(long lotId, long deliveryPointId) throws ResourceNotFoundException;

/**
 * The method try to delete a lot if no restriction of business logic was corrupted.<br/>
 * One of the restrictions is to attempt to remove the lot it if already assigned to auction (no way back)
 *
 * @param lotId the id of lot
 * @return the lot had been really deleted or not
 * @throws ResourceBusyException when database doesn't response
 */
boolean deleteIfPossible(long lotId) throws ResourceBusyException;
}
