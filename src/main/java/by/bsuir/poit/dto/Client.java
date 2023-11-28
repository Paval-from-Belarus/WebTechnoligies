package by.bsuir.poit.dto;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
public static final int ACTIVE = 1; //for statuses
public static final int BLOCKED = 2;

//the first two fields are mapped from user
private long id;
private String name;

private double account;
private double ranking;
}
