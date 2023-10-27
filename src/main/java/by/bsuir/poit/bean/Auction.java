package by.bsuir.poit.bean;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
private Integer membersLimit;
@NotNull
private Timestamp duration;
@NotNull
private Date eventDate;
private Date lastRegisterDate;
@NotNull
private Long auctionTypeId;
}
