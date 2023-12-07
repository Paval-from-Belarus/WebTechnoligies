package by.bsuir.poit.services.impl;

import by.bsuir.poit.dto.LotDto;
import by.bsuir.poit.dao.DeliveryPointRepository;
import by.bsuir.poit.dao.LotRepository;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.model.DeliveryPoint;
import by.bsuir.poit.model.Lot;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Paval Shlyk
 * @since 21/11/2023
 */
@SpringBootTest
class LotDtoServiceImplTest {
@MockBean
private LotRepository lotDao;
@MockBean
private DeliveryPointRepository deliveryPointRepository;
@Autowired
private LotService lotService;

@Test
void findAllBySellerId() {
      long lotId = 42;
      Lot lot = mock(Lot.class);
      doReturn(lotId).when(lot).getId();
      List<Lot> emptyList = List.of();
      List<Lot> notEmptyList = List.of(lot);
      doReturn(emptyList).when(lotDao).findAllBySellerClientId(1);
      doReturn(notEmptyList).when(lotDao).findAllBySellerClientId(2);
      assertTrue(lotService.findAllBySellerId(1).isEmpty());
      List<LotDto> dtoList = lotService.findAllBySellerId(2);
      assertTrue(dtoList.size() == 1 && dtoList.getFirst().getId() == lotId);
      doThrow(DataAccessException.class).when(lotDao).findAllBySellerClientId(anyLong());
      assertThrows(ResourceBusyException.class, () -> lotService.findAllBySellerId(anyLong()));
      doThrow(IllegalStateException.class).when(lotDao).findAllBySellerClientId(anyLong());
      assertThrows(IllegalStateException.class, () -> lotService.findAllBySellerId(anyLong()));
}


@Test
void findDeliveryPointByLot() {
      Lot lot = mock(Lot.class);
      DeliveryPoint point = DeliveryPoint.builder()
				.id(42L)
				.build();
      doReturn(Optional.of(lot)).when(lotDao).findWithDeliveryPoint(anyLong());
      doReturn(point).when(lot).getDeliveryPoint();
      assertEquals(lotService.findDeliveryPointByLot(anyLong()).getId(), (long) point.getId());
      doThrow(DataAccessException.class).when(lotDao).findWithDeliveryPoint(anyLong());
      assertThrows(ResourceBusyException.class, () -> lotService.findDeliveryPointByLot(anyLong()));
      doReturn(Optional.empty()).when(lotDao).findWithDeliveryPoint(anyLong());
      assertThrows(ResourceNotFoundException.class, () -> lotService.findDeliveryPointByLot(anyLong()));
}

@Test
void deleteIfPossible() {
      doThrow(DataAccessException.class).when(lotDao).deleteById(anyLong());
      assertFalse(lotService.deleteIfPossible(anyLong()));
      doNothing().when(lotDao).deleteById(anyLong());
      assertTrue(lotService.deleteIfPossible(anyLong()));
}
}