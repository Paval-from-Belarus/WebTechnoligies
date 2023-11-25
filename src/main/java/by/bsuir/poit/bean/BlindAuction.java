package by.bsuir.poit.bean;

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
public class BlindAuction extends Auction {
private int betLimit;
private Timestamp timeout;
}
