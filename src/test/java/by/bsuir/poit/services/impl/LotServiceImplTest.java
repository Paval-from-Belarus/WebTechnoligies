package by.bsuir.poit.services.impl;

import by.bsuir.poit.bean.DeliveryPoint;
import by.bsuir.poit.bean.Lot;
import by.bsuir.poit.dao.DeliveryPointDao;
import by.bsuir.poit.dao.LotDao;
import by.bsuir.poit.dao.exception.DataAccessException;
import by.bsuir.poit.dao.exception.DataModifyingException;
import by.bsuir.poit.services.LotService;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Paval Shlyk
 * @since 21/11/2023
 */
class LotServiceImplTest {
private LotDao lotDao;
private DeliveryPointDao deliveryPointDao;
private LotService lotService;

@BeforeEach
public void initMock() {
      lotDao = mock(LotDao.class);
      deliveryPointDao = mock(DeliveryPointDao.class);
      lotService = new LotServiceImpl(lotDao, deliveryPointDao);
}

@Test
void findAllBySellerId() {
      List<Lot> emptyList = List.of();
      List<Lot> fullList = List.of(
	  Lot.builder().id(12).build(),
	  Lot.builder().id(13).build());
      when(lotDao.findAllBySellerId(1)).thenReturn(emptyList);
      when(lotDao.findAllBySellerId(2)).thenReturn(fullList);
      assertEquals(lotService.findAllBySellerId(1), emptyList);
      assertEquals(lotService.findAllBySellerId(2), fullList);
      doThrow(DataAccessException.class).when(lotDao).findAllBySellerId(anyLong());
      assertThrows(ResourceBusyException.class, () -> lotService.findAllBySellerId(anyLong()));
      doThrow(IllegalStateException.class).when(lotDao).findAllBySellerId(anyLong());
      assertThrows(IllegalStateException.class, () -> lotService.findAllBySellerId(anyLong()));
}


@Test
void findDeliveryPointByLot() {
      Lot lot = Lot.builder()
		    .deliveryPointId(12L)
		    .build();
      DeliveryPoint point = DeliveryPoint.builder()
				.id(42L)
				.build();
      doReturn(Optional.of(lot)).when(lotDao).findById(anyLong());
      doReturn(Optional.of(point)).when(deliveryPointDao).findById(12);
      assertEquals(point, lotService.findDeliveryPointByLot(anyLong()));
      lot.setDeliveryPointId(13L);
      assertThrows(ResourceNotFoundException.class, () -> lotService.findDeliveryPointByLot(anyLong()));
      doReturn(Optional.empty()).when(deliveryPointDao).findById(anyLong());
      assertThrows(ResourceNotFoundException.class, () -> lotService.findDeliveryPointByLot(anyLong()));
}

@Test
void deleteIfPossible() {
      doThrow(DataModifyingException.class).when(lotDao).delete(anyLong());
      assertFalse(lotService.deleteIfPossible(anyLong()));
      doNothing().when(lotDao).delete(anyLong());
      assertTrue(lotService.deleteIfPossible(anyLong()));
      doThrow(DataAccessException.class).when(lotDao).delete(anyLong());
      assertThrows(ResourceBusyException.class, () -> lotService.deleteIfPossible(anyLong()));

}
}