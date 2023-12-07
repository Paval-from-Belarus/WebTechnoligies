package by.bsuir.poit.dto;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {


//the first two fields are mapped from user
private long id;
private String name;

private double account;
private double ranking;
}
