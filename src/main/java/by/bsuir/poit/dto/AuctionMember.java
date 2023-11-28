package by.bsuir.poit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 12/11/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionMember {
private long clientId;
private long auctionId;
private short status;
}
