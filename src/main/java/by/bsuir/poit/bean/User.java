package by.bsuir.poit.bean;

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

public static final short STATUS_ACTIVE = 1;
public static final short STATUS_NOT_ACTIVE = 2;
private Long id;
private String name;
private String phoneNumber;
private String email;
private short role;
private byte[] passwordHash;
private byte[] securitySalt;
private short status;
}
