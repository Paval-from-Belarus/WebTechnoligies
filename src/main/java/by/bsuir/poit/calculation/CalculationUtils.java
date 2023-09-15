package by.bsuir.poit.calculation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
public class CalculationUtils {
public static double solveFirst(double x, double y) {
      double upperPart = 1.0 + Math.pow(Math.sin(x + y), 2.0);
      double lowerPart = 2.0 + Math.abs(x - 2 * x / (1 + Math.pow(x * y, 2.0)));
      return (upperPart / lowerPart + x);
}

public static List<Double> findValuesOnLine(double start, double end, double step, Function<Double, Double> handler) {
      double value = start;
      List<Double> results = new ArrayList<>();
      while (value < end) {
	    results.add(handler.apply(value));
	    value += step;
      }
      return results;
}

public static List<Integer> findPrimaryNumbersIndexes(int[] array) {
      List<Integer> primaryNumbers = new ArrayList<>();
      for (int i = 0; i < array.length; i++) {
	    if (cif.getLastMax() < array[i]) {
		  cif.updateDivisors(array[i]);
	    }
	    if (isPrimeNumber(array[i], cif.getDivisors())) {
		  primaryNumbers.add(i);
	    }
      }
      return primaryNumbers;
}

public static int findExtraElementsCountForLongestIncreasingSubsequence(int[] origin) {
      int[] lengths = new int[origin.length];
      for (int i = 0; i < origin.length; i++) {
	    lengths[i] = 1; //at least element self
	    for (int j = 0; j < i; j++) {
		  if (origin[j] < origin[i] && lengths[j] + 1 > lengths[i]) {
			lengths[i] = lengths[j] + 1;
		  }
	    }
      }
      return origin.length - maxOfArray(lengths);
}

public static int[][] arrayToMatrix(int[] array) {
      int[][] matrix = new int[array.length][array.length];
      int index = 0;
      for (int i = 0; i < array.length; i++) {
	    for (int j = 0; j < array.length; j++) {
		  matrix[i][j] = array[index];
		  index = (index + 1) % array.length; //consider to use plain if-statement
	    }
	    index = (index + 1) % array.length;
      }
      return matrix;
}

public static void customSort(int[] array) {
      for (int i = 0; i < array.length - 1; ) {
	    if (i >= 0 && array[i] > array[i + 1]) {
		  swap(array, i, i + 1);
		  i -= 1;
	    } else {
		  i += 1;
	    }
      }
}

public static int[] findInsertPositions(int[] acceptable, int[] insertable) {
      int[] indexes = new int[insertable.length];
      for (int i = 0; i < insertable.length; i++) {
	    int insertValue = insertable[i];
	    int insertIndex = Arrays.binarySearch(acceptable, insertValue);
	    if (insertIndex < 0) {
		  insertIndex = -(insertIndex + 1);
	    }
	    while (insertIndex < acceptable.length - 1 && acceptable[insertIndex] == insertValue) {
		  insertIndex += 1;
	    }
	    indexes[i] = insertIndex;
      }
      return indexes;
}

private static boolean isPrimeNumber(int value, int[] divisors) {
      assert divisors.length > 0;
      boolean isPrime = true;
      int index = 0;
      while (index < divisors.length && isPrime) {
	    int divisor = divisors[index];
	    if (divisor > value / 2) {
		  break;
	    }
	    isPrime = value % divisor != 0;
	    index += 1;
      }
      return isPrime;
}

public static int maxOfArray(int[] array) {
      int max = Integer.MIN_VALUE;
      for (int value : array) {
	    if (value > max) {
		  max = value;
	    }
      }
      return max;
}

public static void swap(int[] array, int i, int j) {
      assert i < array.length && j < array.length;
      int temp = array[i];
      array[i] = array[j];
      array[j] = temp;
}

private final static NumbersCif cif = new NumbersCif();
}
