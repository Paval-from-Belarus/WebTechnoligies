package by.bsuir.poit.services;

import by.bsuir.poit.bean.*;
import by.bsuir.poit.services.exception.authorization.UserAccessViolationException;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;

import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * This is an interface for an auction service.
 * It allows finding auctions that occur after a specific date.
 *
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface AuctionService {
/**
 * Finds and returns a list of auctions that occur after the specified date.
 *
 * @param date the date to compare with auction event dates
 * @return a list of auctions occurring after the specified date
 */
List<Auction> findAfterEventDate(Date date);

List<Auction> findByClientId(long clientId);

/**
 * @param auctionId the id of desirable auction
 * @return auction (or derived instance) for given id. If auction is not present this method will throw
 */
Auction findById(long auctionId) throws ResourceNotFoundException;


/**
 * Retrieves all bets for the specified auction.
 *
 * @param auctionId the ID of the auction
 * @return a list of AuctionBet objects representing all the bets for the auction
 * @throws ResourceNotFoundException if the auction is not found
 */
List<AuctionBet> findAllBets(long auctionId) throws ResourceNotFoundException;

/**
 * Finds all bets made by a client for a specific auction.
 *
 * @param auctionId The ID of the auction.
 * @param clientId  The ID of the client.
 * @return A list of AuctionBet objects made by the client for the auction.
 * @throws ResourceNotFoundException If the auction or client is not found.
 */
List<AuctionBet> findAllBetsByClientId(long auctionId, long clientId) throws ResourceNotFoundException;

/**
 * Finds the type of an auction by its ID.
 *
 * @param auctionId The ID of the auction.
 * @return The AuctionType of the auction.
 * @throws ResourceNotFoundException If the auction is not found.
 */
AuctionType findTypeByAuctionId(long auctionId) throws ResourceNotFoundException;

/**
 * Finds the headers of all auctions managed by an admin.
 *
 * @param adminId The ID of the admin.
 * @return A list of Auction objects representing the headers of the auctions.
 * @throws ResourceBusyException If the resource is currently busy.
 */
List<Auction> findHeadersByAdminId(long adminId) throws ResourceBusyException;

/**
 * Finds all auction types.
 *
 * @return A list of all AuctionType objects.
 */
List<AuctionType> findAllTypes();

/**
 * Assigns a lot to an auction.
 *
 * @param principal The principal user.
 * @param auctionId The ID of the auction.
 * @param lotId     The ID of the lot.
 * @throws UserAccessViolationException If the user does not have access.
 * @throws ResourceModifyingException   If there is an error modifying the resource.
 */
void assignLot(Principal principal, long auctionId, long lotId) throws UserAccessViolationException, ResourceModifyingException;

/**
 * Saves an auction.
 *
 * @param principal The principal user.
 * @param auction   The Auction object to be saved.
 * @throws ResourceModifyingException If there is an error modifying the resource.
 */
void saveAuction(Principal principal, Auction auction) throws ResourceModifyingException;

/**
 * Saves a bet.
 *
 * @param principal The principal user.
 * @param bet       The AuctionBet object to be saved.
 * @throws ResourceNotFoundException If the bet is not found.
 */
void saveBet(Principal principal, AuctionBet bet) throws ResourceNotFoundException;
}
