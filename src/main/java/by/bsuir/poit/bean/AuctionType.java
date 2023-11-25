package by.bsuir.poit.bean;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuctionType {
public static final long ENGLISH = 1;
public static final long BLITZ = 2;
public static final long BLIND = 3;
private long id;
@NotNull
private String name;
@NotNull
private String description;
}
