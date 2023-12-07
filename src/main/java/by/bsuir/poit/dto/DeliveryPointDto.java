package by.bsuir.poit.dto;

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
public class DeliveryPointDto {
private long id;
private String cityCode;
private String streetName;
private String houseNumber;
private String name;
}
