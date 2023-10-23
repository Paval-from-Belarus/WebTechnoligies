package by.bsuir.poit.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Data
@Builder
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
@Nullable
private Long customerId;
@NotNull
private Long deliveryPointId;
@Nullable
private Long auctionId;
}
