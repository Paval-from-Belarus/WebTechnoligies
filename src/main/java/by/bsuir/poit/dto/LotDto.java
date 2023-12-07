package by.bsuir.poit.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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
public class LotDto {
//relationship's data
@Null(groups = Create.class)
@NotNull(groups = Update.class)
private Long id;
@NotNull(groups = Create.class)
private String title;
@NotNull(groups = Create.class)
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
