package by.bsuir.poit.dto;

import jakarta.validation.constraints.NotNull;
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
public class AuctionMemberDto {
@NotNull
private long clientId;
@NotNull
private long auctionId;
@NotNull
private short status;
}
