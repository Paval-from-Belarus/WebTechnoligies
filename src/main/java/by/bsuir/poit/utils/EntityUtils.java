package by.bsuir.poit.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Paval Shlyk
 * @since 20/11/2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EntityUtils {
public static java.sql.Date toEntityDate(Date simpleDate) {
      return new java.sql.Date(simpleDate.getTime());
}
public static Date fromEntityDate(java.sql.Date entityDate) {
      return new Date(entityDate.getTime());
}
}
