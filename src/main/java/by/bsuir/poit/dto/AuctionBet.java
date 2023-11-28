package by.bsuir.poit.dto;

import jakarta.validation.constraints.NotNull;
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
public class AuctionBet {
private long id;
private double bet;
private Timestamp time;
//foreign keys
@NotNull
private Long lotId;
@NotNull
private Long clientId;
@NotNull
private Long auctionId;
}
