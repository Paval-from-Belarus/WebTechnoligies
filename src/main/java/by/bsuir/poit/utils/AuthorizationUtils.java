package by.bsuir.poit.utils;

import by.bsuir.poit.dto.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.CharBuffer;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * @author Paval Shlyk
 * @since 23/10/2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthorizationUtils {
public static final String COOKIE_USER_ID = "user_id";
public static final String COOKIE_USER_ROLE = "user_role";
public static final String PASSWORD = "password";
public static final String NAME = "user_name";
public static final String ROLE = "role";
public static final String EMAIL = "email";
public static final String USER_ATTRIBUTE = "user_attribute";
public static final int SALT_LENGTH = 32;
public static final int PASSWORD_HASH_LENGTH = 32;
public static final int PRINCIPAL_COOKIE_COUNT = 2;

@SneakyThrows
public static String encodePassword(String password, String salt) {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt.getBytes());
      byte[] digest = md.digest(password.getBytes());
      md.reset();//to use in future
      return bytesToString(digest);
}

public static String newSecuritySalt() {
      return bytesToString(random.generateSeed(SALT_LENGTH));
}

private static final SecureRandom random = new SecureRandom();

public static UserDto parseUser(HttpServletRequest request) throws IOException {
      request.getParameter(ROLE);
      return UserDto.builder()
		 .name(request.getParameter(NAME))
		 .email(request.getParameter(EMAIL))
		 .role(Short.parseShort(request.getParameter(ROLE)))
		 .build();
}

public static String bytesToString(byte[] bytes) {
      CharBuffer letters = CharBuffer.allocate(bytes.length);
      for (byte letter : bytes) {
	    letters.put((char) letter);
      }
      return String.valueOf(letters.array());
}

public static void removePrincipalCookies(Cookie[] cookies, HttpServletResponse response) {
      for (Cookie cookie : cookies) {
	    if (AuthorizationUtils.isPrincipalCookie(cookie)) {
		  cookie.setMaxAge(-1);
		  response.addCookie(cookie);
	    }
      }
}

public static boolean isPrincipalCookie(@NotNull Cookie cookie) {
      return cookie.getName().equals(COOKIE_USER_ID) || cookie.getName().equals(COOKIE_USER_ROLE);
}
}
