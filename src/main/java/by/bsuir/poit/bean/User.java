package by.bsuir.poit.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class User {
public static final int CLIENT = 1;
public static final int ADMIN = 2;
public static final int OFFICER = 3;//the grant to all

public static final short STATUS_NOT_ACTIVE = 1;
public static final short STATUS_ACTIVE = 2;

private long id;
private String name;
private String phoneNumber;
private String email;
private short role;
private String passwordHash;
private String securitySalt;
private short status;
}
