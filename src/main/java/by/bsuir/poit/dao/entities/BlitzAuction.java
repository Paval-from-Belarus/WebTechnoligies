package by.bsuir.poit.dao.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlitzAuction extends Auction{
private Timestamp iterationLimit;
@Nullable
private Integer memberExcludeLimit;
}
