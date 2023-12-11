package by.bsuir.poit.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionBetDto {
@NotNull(groups = Update.class)
private Long id;
@NotNull
private Double bet;
@Null //user never assign such value
private Timestamp time;
//foreign keys
@NotNull
private Long lotId;
@NotNull
private Long auctionId;
}
