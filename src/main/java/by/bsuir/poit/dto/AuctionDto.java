package by.bsuir.poit.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.sql.Date;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDto {
@NotNull
private Long id;
@NotNull(groups = Create.class)
private double priceStep;
@NotNull(groups = Create.class)
private Integer membersLimit;
@Null
private Timestamp duration;
@NotNull
private Date eventDate;
private Date lastRegisterDate;
@NotNull(groups = Create.class)
private Long auctionTypeId;
@NotNull(groups = Create.class)
private Long adminId;
}
