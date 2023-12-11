package by.bsuir.poit.services.impl;

import by.bsuir.poit.dao.*;
import by.bsuir.poit.dto.AuctionBetDto;
import by.bsuir.poit.dto.AuctionDto;
import by.bsuir.poit.model.Auction;
import by.bsuir.poit.model.AuctionBet;
import by.bsuir.poit.model.AuctionMember;
import by.bsuir.poit.model.AuctionType;
import by.bsuir.poit.services.exception.resources.ResourceBusyException;
import by.bsuir.poit.services.exception.resources.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Paval Shlyk
 * @since 20/11/2023
 */
@SpringBootTest
class AuctionServiceImplTest {
@MockBean
private AuctionRepository auctionRepository;
@MockBean
private AuctionMemberRepository auctionMemberRepository;
@MockBean
private AuctionBetRepository auctionBetRepository;
@MockBean
private AuctionTypeRepository auctionTypeDao;
@MockBean
private LotRepository lotRepository;
@Autowired
private AuctionServiceImpl auctionService;

@Test
void findAfterEventDate() {
      Auction entity = mock(Auction.class);
      doReturn(42).when(entity).getId();
      Date date = new Date();
      doReturn(List.of(entity)).when(auctionRepository).findAllAfterEventDate(date);
      List<AuctionDto> dto = auctionService.findAfterEventDate(date);
      assertTrue(dto.size() == 1 && dto.getFirst().getId() == 42);
      doThrow(DataAccessException.class).when(auctionRepository).findAllAfterEventDate(date);
      assertThrows(ResourceBusyException.class, () -> auctionService.findAfterEventDate(date));
      doThrow(IllegalStateException.class).when(auctionRepository).findAllAfterEventDate(date);
      assertThrows(IllegalStateException.class, () -> auctionService.findAfterEventDate(date));
}

@Test
void findByClientId() {
      AuctionMember member = mock(AuctionMember.class);
      Auction auction = mock(Auction.class);
      doReturn(auction).when(member).getAuction();
      doReturn(42).when(auction).getId();
      when(auctionMemberRepository.findAllByClientId(anyLong())).thenReturn(List.of(member));
      assertTrue(
	  auctionService.findByClientId(anyLong()).stream().allMatch(dto -> dto.getId() == 42)
      );
      when(auctionMemberRepository.findAllByClientId(anyLong())).thenThrow(DataAccessException.class);
      assertThrows(ResourceBusyException.class, () -> auctionService.findByClientId(anyLong()));
}

@Test
void findById() {
      doThrow(DataAccessException.class).when(auctionRepository).findById(anyLong());
      assertThrows(ResourceBusyException.class, () -> auctionService.findById(anyLong()));
      Auction auction = mock(Auction.class);
      doReturn(42).when(auction).getId();
      doReturn(Optional.of(auction)).when(auctionRepository).findById(anyLong());
      assertEquals(42, auctionService.findById(anyLong()).getId());
}

@Test
void findAllBetsByClientId() {
      AuctionBet bet = mock(AuctionBet.class);
      Auction auction = mock(Auction.class);
      long auctionId = 42;
      doReturn(auction).when(bet).getAuction();
      doReturn(auction).when(auction).getId();
      List<AuctionBet> firstUserBets = List.of();
      List<AuctionBet> secondUserBets = List.of(bet);
      doReturn(firstUserBets).when(auctionBetRepository).findAllByAuctionIdAndClientId(auctionId, 1);
      doReturn(secondUserBets).when(auctionBetRepository).findAllByAuctionIdAndClientId(auctionId, 2);
      assertTrue(auctionService.findAllBetsByClientId(auctionId, 1).isEmpty());
      List<AuctionBetDto> dtoList = auctionService.findAllBetsByClientId(auctionId, 2);
      assertTrue(dtoList.stream().allMatch(dtoBet -> dtoBet.getAuctionId() == auctionId));
}

@Test
void findTypeByAuctionId() {
      long auctionTypeId = 1;
      AuctionType type = mock(AuctionType.class);
      Auction auction = mock(Auction.class);
      doReturn(auctionTypeId).when(type).getId();
      when(auctionRepository.findById(anyLong())).thenReturn(Optional.of(auction));
      when(auction.getAuctionType()).thenReturn(type);
      assertEquals(auctionTypeId, auctionService.findTypeByAuctionId(anyLong()).getId());
      when(auctionTypeDao.findById(anyLong())).thenReturn(Optional.empty());
      assertThrows(ResourceNotFoundException.class, () -> auctionService.findTypeByAuctionId(anyLong()));
}
}