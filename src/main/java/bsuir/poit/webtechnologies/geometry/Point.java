package bsuir.poit.webtechnologies.geometry;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */


public record Point(double x, double y) {
public static Point of(double x, double y) {
      return new Point(x, y);
}
}
