package by.bsuir.poit.dao.entities;

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
public class Client {
public static final int ACTIVE = 1; //for statuses
public static final int BLOCKED = 2;

private long userId; //primary key and foreign key in some place
private double account;
private double ranking;
private int status;
}
