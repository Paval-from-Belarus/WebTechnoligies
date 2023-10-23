package by.bsuir.poit.dao.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EnglishLot extends Lot {
private double redemptionPrice;
}
