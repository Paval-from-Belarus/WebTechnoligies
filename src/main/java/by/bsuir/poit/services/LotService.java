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

List<Lot> findAllByAuction(long auctionId) throws ResourceBusyException;

EnglishLot findEnglishLot(long lotId) throws ResourceNotFoundException;

DeliveryPoint findDeliveryPointByLot(long lotId) throws ResourceNotFoundException;

void save(Lot lot) throws ResourceModifyingException;

void update(Lot lot) throws ResourceModifyingException;
}
