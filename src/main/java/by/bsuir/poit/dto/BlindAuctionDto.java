package by.bsuir.poit.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlindAuctionDto extends AuctionDto {
private int betLimit;
private Timestamp timeout;
}
