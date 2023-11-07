package by.bsuir.poit.servlets;

import lombok.Builder;

import java.security.Principal;
import java.util.UUID;

/**
 * @author Paval Shlyk
 * @since 07/11/2023
 */
@Builder
public record UserDetails(
    long id,
    short role) implements Principal {
@Override
public String getName() {
      return "[User id = " + id + "]";
}
}
