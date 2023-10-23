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
public class User {
public static final int CLIENT = 1;
public static final int ADMIN = 2;
public static final int OFFICER = 3;//the grant to all

private long id;
private String name;
private String phoneNumber;
private String email;
private int role;
private String passwordHash;
private String securitySalt;
}
