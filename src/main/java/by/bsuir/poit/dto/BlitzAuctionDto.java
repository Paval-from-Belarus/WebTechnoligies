package by.bsuir.poit.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlitzAuctionDto extends AuctionDto {
@NotNull
private Timestamp iterationLimit;
private Integer memberExcludeLimit;
}
