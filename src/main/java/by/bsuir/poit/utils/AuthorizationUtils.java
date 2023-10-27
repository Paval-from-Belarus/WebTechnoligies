package by.bsuir.poit.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthorizationUtils {
public static final String PASSWORD = "password";
public static final String NAME = "user_name";

@SneakyThrows
public static String encodeToken(String token, String salt) {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt.getBytes());
      byte[] digest = md.digest(token.getBytes());
      md.reset();//to use in future
      return Base64.getEncoder().encodeToString(digest);
}
}
