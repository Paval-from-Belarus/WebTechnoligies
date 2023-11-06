package by.bsuir.poit.utils;

import by.bsuir.poit.bean.User;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.stream.Collectors;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthorizationUtils {
public static final String PASSWORD = "password";
public static final String NAME = "user_name";
public static final String USER_ATTRIBUTE = "user_attribute";
public static final int SALT_LENGTH = 32;
public static final int PASSWORD_HASH_LENGTH = 32;
@SneakyThrows
public static byte[] encodePassword(byte[] password, byte[] salt) {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt);
      byte[] digest = md.digest(password);
      md.reset();//to use in future
      return digest;
}

public static byte[] newSecuritySalt() {
      return random.generateSeed(SALT_LENGTH);
}

private static final SecureRandom random = new SecureRandom();
private static final Gson PARSER = new Gson();

public static User parseUser(HttpServletRequest request) throws IOException {
      String jsonUser = request.getReader().lines()
			    .limit(8)
			    .collect(Collectors.joining());
      return PARSER.fromJson(jsonUser, User.class);
}
}
