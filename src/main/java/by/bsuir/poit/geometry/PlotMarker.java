package by.bsuir.poit.geometry;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
public class PlotMarker {
public static class PlotMarkerConstructionException extends RuntimeException {
      PlotMarkerConstructionException(String message) {
	    super(message);
      }

      PlotMarkerConstructionException(Throwable t) {
	    super(t);
      }
}

private final Rectangle[] rectangles;

//the ownership of points will belong to PlotMarker
private PlotMarker(Rectangle[] rectangles) {
      this.rectangles = rectangles;
}

public boolean isPointPresent(double x, double y) {
      int index = 0;
      boolean isInside = false;
      while (index < rectangles.length && !isInside) {
	    isInside = rectangles[index].isInside(x, y);
	    index += 1;
      }
      return isInside;
}

public static PlotMarker valueOf(Rectangle[] rectangles) throws PlotMarkerConstructionException {
      PlotMarker marker;
      if (rectangles.length == 2 && haveIntersections(rectangles[0], rectangles[1])) {
	    marker = new PlotMarker(rectangles);
      } else {
	    if (rectangles.length != 2) {
		  throw new PlotMarkerConstructionException("The count of rectangles should equals two");
	    } else {
		  throw new PlotMarkerConstructionException("Inlaid rectangles layouts");
	    }
      }
      return marker;
}

private static boolean haveIntersections(Rectangle lower, Rectangle upper) {
      if (lower.y() > upper.y()) {
	    Rectangle ref = lower;
	    lower = upper;
	    upper = ref;
      }
      return lower.x() <= upper.x() && lower.x() + lower.width() >= upper.x() + upper.width();
}
}
