package by.bsuir.poit.dao.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
public static final int ACTIVE = 1; //for statuses
public static final int BLOCKED = 2;

private long id;
private double account;
private double ranking;
private int status;
}
