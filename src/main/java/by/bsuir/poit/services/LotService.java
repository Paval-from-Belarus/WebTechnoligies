package by.bsuir.poit.services;

import by.bsuir.poit.dto.DeliveryPointDto;
import by.bsuir.poit.dto.EnglishLotDto;
import by.bsuir.poit.dto.LotDto;
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
/**
 * Returns a list of all lots that are scheduled for auction.
 *
 * @return List of lots
 */

List<LotDto> findAllBeforeAuctionLots();

/**
 * Returns a list of all lots owned by a particular seller.
 *
 * @param clientId ID of the seller
 * @return List of lots
 * @throws ResourceBusyException if the resource is currently being modified by another user
 */
List<LotDto> findAllBySellerId(long clientId) throws ResourceBusyException;

/**
 * Returns a list of all lots with a particular status.
 *
 * @param status Status of the lots to be returned
 * @return List of lots
 */
List<LotDto> findAllByStatus(short status);

/**
 * Returns a list of all lots associated with a particular auction.
 *
 * @param auctionId ID of the auction
 * @return List of lots
 * @throws ResourceBusyException if the resource is currently being modified by another user
 */
List<LotDto> findAllByAuction(long auctionId) throws ResourceBusyException;

/**
 * Returns the EnglishLot object associated with a particular lot ID.
 *
 * @param lotId ID of the lot
 * @return EnglishLot object
 * @throws ResourceNotFoundException if the resource is not found
 */
LotDto findEnglishLot(long lotId) throws ResourceNotFoundException;

/**
 * Returns the DeliveryPoint object associated with a particular lot ID.
 *
 * @param lotId ID of the lot
 * @return DeliveryPoint object
 * @throws ResourceNotFoundException if the resource is not found
 */
DeliveryPointDto findDeliveryPointByLot(long lotId) throws ResourceNotFoundException;

/**
 * Saves a new lot to the system.
 *
 * @param principal Principal object representing the user performing the action
 * @param lot       Lot object to be saved
 * @throws ResourceModifyingException if the resource is currently being modified by another user
 */
void save(@NotNull Principal principal, LotDto lot) throws ResourceModifyingException;

/**
 * Updates the auction associated with a particular lot.
 *
 * @param lotId     ID of the lot
 * @param auctionId ID of the new auction
 * @throws ResourceNotFoundException if the resource is not found
 */
void updateLotAuction(long lotId, long auctionId) throws ResourceNotFoundException;

/**
 * Updates the status of a particular lot.
 *
 * @param lotId     ID of the lot
 * @param lotStatus New status of the lot
 * @throws ResourceNotFoundException if the resource is not found
 */
void updateLotStatus(long lotId, short lotStatus) throws ResourceNotFoundException;

/**
 * Updates the customer associated with a particular lot.
 *
 * @param lotId      ID of the lot
 * @param customerId ID of the new customer
 * @throws ResourceNotFoundException if the resource is not found
 */
void updateLotCustomer(long lotId, long customerId) throws ResourceNotFoundException;

/**
 * Updates the delivery point associated with a particular lot.
 *
 * @param lotId           ID of the lot
 * @param deliveryPointId ID of the new delivery point
 * @throws ResourceNotFoundException if the resource is not found
 */
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
