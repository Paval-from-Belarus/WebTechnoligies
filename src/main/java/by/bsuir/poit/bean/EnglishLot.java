package by.bsuir.poit.bean;

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
