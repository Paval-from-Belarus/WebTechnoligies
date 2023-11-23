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
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface AuctionService {
List<Auction> findAfterEventDate(Date date);

List<Auction> findByClientId(long clientId);

/**
 * @param auctionId the id of desirable auction
 * @return auction (or derived instance) for given id. If auction is not present this method will throw
 */
Auction findById(long auctionId) throws ResourceNotFoundException;

List<AuctionBet> findAllBets(long auctionId) throws ResourceNotFoundException;

List<AuctionBet> findAllBetsByClientId(long auctionId, long clientId) throws ResourceNotFoundException;

AuctionType findTypeByAuctionId(long auctionId) throws ResourceNotFoundException;

List<Auction> findHeadersByAdminId(long adminId) throws ResourceBusyException;

List<AuctionType> findAllTypes();

void assignLot(Principal principal, long auctionId, long lotId) throws UserAccessViolationException, ResourceModifyingException;

void saveAuction(Principal principal, Auction auction) throws ResourceModifyingException;

void saveBet(Principal principal, AuctionBet bet) throws ResourceNotFoundException;
}
