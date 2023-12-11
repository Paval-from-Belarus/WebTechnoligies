package by.bsuir.poit.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
//the first two fields are mapped from user
@NotNull
private long id;
@NotNull
private String name;
@NotNull
private double account;
@NotNull
private double ranking;
}
