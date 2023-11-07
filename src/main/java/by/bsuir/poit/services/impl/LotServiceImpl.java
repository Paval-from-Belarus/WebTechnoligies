package by.bsuir.poit.services.impl;

import by.bsuir.poit.bean.ClientFeedback;
import by.bsuir.poit.bean.DeliveryPoint;
import by.bsuir.poit.bean.EnglishLot;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.context.Service;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 08/11/2023
 */
@Service
@RequiredArgsConstructor
public class LotServiceImpl implements LotService {
@Override
public List<Lot> findAllByClient(long clientId) throws ResourceNotFoundException {
      return null;
}

@Override
public List<Lot> findAllByAuction(long auctionId) throws ResourceNotFoundException {
      return null;
}

@Override
public List<ClientFeedback> findAllByLot(long lotId) throws ResourceNotFoundException {
      return null;
}

@Override
public EnglishLot findEnglishLot(long lotId) throws ResourceNotFoundException {
      return null;
}

@Override
public DeliveryPoint findDeliveryPointByLot(long lotId) throws ResourceNotFoundException {
      return null;
}
}
