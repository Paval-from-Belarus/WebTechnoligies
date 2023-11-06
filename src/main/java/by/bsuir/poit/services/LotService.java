package by.bsuir.poit.services;

import by.bsuir.poit.bean.ClientFeedback;
import by.bsuir.poit.bean.DeliveryPoint;
import by.bsuir.poit.bean.EnglishLot;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 27/10/2023
 */
public interface LotService {

List<Lot> findAllByClient(long clientId) throws ResourceNotFoundException;

List<Lot> findAllByAuction(long auctionId) throws ResourceNotFoundException;

List<ClientFeedback> findAllByLot(long lotId) throws ResourceNotFoundException;

EnglishLot findEnglishLot(long lotId) throws ResourceNotFoundException;

DeliveryPoint findDeliveryPointByLot(long lotId) throws ResourceNotFoundException;
}
