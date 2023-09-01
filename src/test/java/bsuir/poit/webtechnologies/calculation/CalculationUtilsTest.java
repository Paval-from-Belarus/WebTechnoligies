package bsuir.poit.webtechnologies.calculation;

import bsuir.poit.webtechnologies.geometry.Color;
import bsuir.poit.webtechnologies.geometry.Point;
import org.junit.jupiter.api.Test;

import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
class CalculationUtilsTest {
@Test
public void sinusTest() {
      Map<Point, Double> testCases = Map.of(
	  Point.of(0.5, 2.0), 1.179084453,
	  Point.of(-1.25, 0.25), -0.68582882
      );
      for (Map.Entry<Point, Double> entry : testCases.entrySet()) {
	    double x = entry.getKey().x();
	    double y = entry.getKey().y();
	    assertEquals(CalculationUtils.solveFirst(x, y), entry.getValue(), 0.00001);
      }
}

@Test
public void insertionTest() {
      var first = new int[]{1, 3, 5, 5, 10};
      var second = new int[]{2, 4, 5, 11};
      int[] positions = CalculationUtils.findInsertPositions(first, second);
      assertEquals(positions.length, second.length);
      assertEquals(1, positions[0]);
      assertEquals(2, positions[1]);
      assertEquals(4, positions[2]);
      assertEquals(5, positions[3]);
}
@Test
public void sortedTest() {
      int[] array = new int[]{123, 43, 12, 432, 5, 120, 41};
      CalculationUtils.customSort(array);
      int min = array[0];
      for (int i = 1; i < array.length; i++) {
	    assertTrue(array[i] >= min);
	    min = array[i];
      }
}
@Test
public void arrayToMatrixTest() {
      int[] plainArray = new int[]{1, 2, 3, 4, 5};
      int[][] matrix = CalculationUtils.arrayToMatrix(plainArray);
      assertEquals(matrix.length, plainArray.length);
      for (int[] ints : matrix) {
	    assertEquals(ints.length, plainArray.length);
      }
      int arrayPivot = 0;
      for (int[] row : matrix) {
	    for (int ceil : row) {
		  assertEquals(ceil, plainArray[arrayPivot]);
		  arrayPivot = (arrayPivot + 1) % plainArray.length;
	    }
	    arrayPivot = (arrayPivot + 1) % plainArray.length;
      }
}
@Test
public void longestIncreasingSubsequenceTest() {
      int[] sequence = new int[]{5, 3, 2, 10, 34, 5, 7};
      int extraCnt = CalculationUtils.findExtraElementsCountForLongestIncreasingSubsequence(sequence);
      assertEquals(sequence.length - 3, extraCnt);
}
@Test
public void simplicityTest() {
      int[] numbers = new int[]{1, 2, 4, 10, 13};
      var indexes = CalculationUtils.findPrimaryNumbersIndexes(numbers);
      assertEquals(indexes.size(), 3);
      indexes.sort(Comparator.naturalOrder());
      assertEquals(indexes.get(0), 0);
      assertEquals(indexes.get(1), 1);
      assertEquals(indexes.get(2), numbers.length - 1);
}
@Test
public void lineFunctionTest() {
      double start = 0.0;
      double end = 10.0;
      double step = 2.0;
      double currentValue = start;
      var values = new ArrayList<Double>();
      while (currentValue <  end) {
	    values.add(Math.tan(currentValue));
	    currentValue += step;
      }
      var testValues = CalculationUtils.findValuesOnLine(start, end, step, Math::tan);
      assertEquals(testValues.size(), values.size());
      for (int i = 0; i < values.size(); i++) {
	    assertEquals(values.get(i), testValues.get(i));
      }

}
}