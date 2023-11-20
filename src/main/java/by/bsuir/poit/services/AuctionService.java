package by.bsuir.poit.services;

import by.bsuir.poit.bean.*;
import by.bsuir.poit.services.exception.resources.ResourceModifyingException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import by.bsuir.poit.servlets.command.impl.AuctionCreationHandler;

import java.util.Date;
import java.util.List;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface AuctionService {
List<Auction> findWithEventDate(Date date);

List<Auction> findByClientId(long clientId);

/**
 * @param auctionId the id of desirable auction
 * @return auction (or derived instance) for given id. If auction is not present this method will throw
 */
Auction findById(long auctionId) throws ResourceNotFoundException;

@Deprecated
BlindAuction findBlindByAuctionId(long auctionId) throws ResourceNotFoundException;

@Deprecated
BlitzAuction findBlitzByAuctionId(long auctionId) throws ResourceNotFoundException;

List<AuctionBet> findAllBets(long auctionId) throws ResourceNotFoundException;

List<AuctionBet> findAllBetsByClientId(long auctionId, long clientId) throws ResourceNotFoundException;

AuctionType findTypeByAuctionId(long auctionId) throws ResourceNotFoundException;

List<AuctionType> findAllTypes();

void saveAuction(Auction auction) throws ResourceModifyingException;

void saveBet(AuctionBet bet) throws ResourceNotFoundException;
}
