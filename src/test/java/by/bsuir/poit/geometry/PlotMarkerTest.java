package by.bsuir.poit.geometry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
class PlotMarkerTest {
public static final String VALID_BOUND_INTERSECTION = "VALID_BOUND_INTERSECTION";
public static final String VALID_DEEP_INTERSECTION = "VALID_DEEP_INTERSECTION";
public static final String INVALID_COUNT_OF_RECTANGLES = "INVALID_COUNT_OF_RECTANGLES";
public static final String INVALID_RECTANGLES_LAYOUT = "INVALID_LAYOUT";
private static final Map<String, Rectangle[]> testCases = new HashMap<>();

@BeforeAll
private void init() {

      testCases.put(
	  VALID_BOUND_INTERSECTION,
	  arrayOf(new Rectangle(0, 0, 100, 50), new Rectangle(50 - 20, 50, 40, 50)));
      testCases.put(
	  VALID_DEEP_INTERSECTION,
	  arrayOf(new Rectangle(0, 0, 100, 50), new Rectangle(5, 5, 30, 20))
      );
      testCases.put(
	  INVALID_COUNT_OF_RECTANGLES,
	  arrayOf(new Rectangle(0, 0, 0, 0))
      );
      testCases.put(
	  INVALID_RECTANGLES_LAYOUT,
	  arrayOf(new Rectangle(0, 0, 100, 20), new Rectangle(100, 100, 100, 100))
      );
}

@Test
public void InvalidConstruction() {
      Assertions.assertThrows(PlotMarker.PlotMarkerConstructionException.class, () -> PlotMarker.valueOf(testCases.get(INVALID_COUNT_OF_RECTANGLES)));
      Assertions.assertThrows(PlotMarker.PlotMarkerConstructionException.class, () -> PlotMarker.valueOf(testCases.get(INVALID_RECTANGLES_LAYOUT)));
}

@Test
public void testValidCases() {
      var rectangles = testCases.get(VALID_BOUND_INTERSECTION);//all rectangles are sorted by y. That is the last rectangle has highest y
      var lastRectangle = rectangles[rectangles.length - 1];
      Point validPoint = Point.of(lastRectangle.x() + lastRectangle.width() / 2, lastRectangle.y() + lastRectangle.height() / 2);
      Point invalidPoint = Point.of(lastRectangle.x(), lastRectangle.y() + lastRectangle.height() + 1.0); //upper then possible
      var marker = PlotMarker.valueOf(rectangles);
      Assertions.assertTrue(marker.isPointPresent(validPoint.x(), validPoint.y()), "Valid point check position test failed");
      Assertions.assertFalse(marker.isPointPresent(invalidPoint.x(), invalidPoint.y()), "Invalid point check position test failed");
}
private Rectangle[] arrayOf(Rectangle... items) {
      Arrays.sort(items);
      return items;
}
}