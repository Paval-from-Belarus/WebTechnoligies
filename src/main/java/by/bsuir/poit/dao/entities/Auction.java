package by.bsuir.poit.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Auction {
private long id;
private double priceStep;
@Nullable
private Integer membersLimit;
@NotNull
private Timestamp duration;
@NotNull
private Date eventDate;
@Nullable
private Date lastRegisterDate;
@NotNull
private Long auctionTypeId;
}
