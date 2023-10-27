package by.bsuir.poit.bean;

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
public static final int BLOCKED_STATUS = 1;
public static final int BEFORE_AUCTION_STATUS = 2;
public static final int SELL_STATUS = 3;
public static final int SENT_STATUS = 4;
public static final int DELIVERIED_STATUS = 5;
//relationship's data
private long id;
private String title;
private int status;
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
