package by.bsuir.poit.services;

import by.bsuir.poit.bean.*;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface AuctionService {
List<Auction> findWithEventDate(Date date);

List<Auction> findByClientId(long clientId);

BlindAuction findBlindByAuctionId(long auctionId) throws ResourceNotFoundException;

BlitzAuction findBlitzByAuctionId(long auctionId) throws ResourceNotFoundException;

List<AuctionBet> findAllBets(long auctionId) throws ResourceNotFoundException;

List<AuctionBet> findAllBetsByClientId(long auctionId, long clientId) throws ResourceNotFoundException;

List<AuctionType> findAllTypes();
}
