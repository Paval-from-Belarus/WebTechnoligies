package by.bsuir.poit.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Lot {
public static final short BLOCKED_STATUS = 1;
public static final short BEFORE_AUCTION_STATUS = 2;
public static final short AUCTION_STATUS = 6;
public static final short SELL_STATUS = 3;
public static final short SENT_STATUS = 4;
public static final short DELIVERIED_STATUS = 5;
//relationship's data
private long id;
private String title;
private short status;
//foreign keys
@NotNull
private Double startPrice;
@NotNull
private Double actualPrice;
@NotNull
private Long auctionTypeId;
@NotNull
private Long sellerId;
private Long customerId;
@NotNull
private Long deliveryPointId;
private Long auctionId;

}
