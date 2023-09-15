package by.bsuir.poit;

import by.bsuir.poit.calculation.CalculationUtils;
import by.bsuir.poit.geometry.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
public class Main {
private final Scanner input;
public static final int TASK_CNT = 16;

private final Map<Integer, Supplier<? extends Object>> taskMap = new HashMap<>(TASK_CNT);

public Main(InputStream inputStream) {
      this.input = new Scanner(inputStream);
      taskMap.putAll(
	  Map.of(
	      0, this::solveFirstTask,
	      1, this::solveSecondTask,
	      2, this::solveThirdTask,
	      3, this::solveFourthTask,
	      4, this::solveFifthTask,
	      5, this::solveSixTask,
	      6, this::solveSevenTask,
	      7, this::solveEightTask,
	      8, this::solveNinthTask
	  )
      );
      for (int i = 9; i < TASK_CNT; i++) {
	    taskMap.put(i, this::dummyTask);
      }
      assert taskMap.size() == TASK_CNT;

}

private List<Double> solveThirdTask() {
      double start = input.nextDouble();
      double end = input.nextDouble();
      double step = input.nextDouble();
      return CalculationUtils.findValuesOnLine(start, end, step, Math::tan);
}

private List<Integer> solveFourthTask() {
      int[] numbers = scanArray(this.input);
      return CalculationUtils.findPrimaryNumbersIndexes(numbers);
}

private Integer solveFifthTask() {
      int[] numbers = scanArray(this.input);
      return CalculationUtils.findExtraElementsCountForLongestIncreasingSubsequence(numbers);
}

private String solveSixTask() {
      int[] numbers = scanArray(this.input);
      return Arrays.deepToString(CalculationUtils.arrayToMatrix(numbers));
}

private String solveSevenTask() {
      int[] numbers = scanArray(this.input);
      CalculationUtils.customSort(numbers);
      return Arrays.toString(numbers);
}

private String solveEightTask() {
      int[] origin = scanArray(this.input);
      int[] insertable = scanArray(this.input);
      return Arrays.toString(CalculationUtils.findInsertPositions(origin, insertable));
}

private String solveNinthTask() {
      int capacity = input.nextInt();
      int ballsCnt = input.nextInt();
      System.out.println("Balls will be automatically generated");
      var balls = new Ball[ballsCnt];
      for (int i = 0; i < balls.length; i++) {
	    balls[i] = Ball.randomBall();
      }
      System.out.println("Generated balls");
      System.out.println(Arrays.toString(balls));
      var bucket = Bucket.of(capacity, Arrays.asList(balls));
      long weight = bucket.getLoad();
      long blueBallCnt = bucket.balls().stream()
			     .filter(ball -> ball.color().equals(Color.Blue))
			     .count();
      return String.format("Bucket load is %d. The quantity of blue balls is %d",
	  weight, blueBallCnt);
}

private String dummyTask() {
      return "This task cannot be depicted via text. Probably, see source code to check correctness of it accomplishment";
}

public static int[] scanArray(Scanner input) {
      int arraySize = input.nextInt();
      int[] numbers = new int[arraySize];
      for (int i = 0; i < arraySize; i++) {
	    numbers[i] = input.nextInt();
      }
      return numbers;
}

private Double solveFirstTask() {
      double x = input.nextDouble();
      double y = input.nextDouble();
      return CalculationUtils.solveFirst(x, y);
}

private Boolean solveSecondTask() {
      int rectanglesCnt = input.nextInt();
      Rectangle[] rectangles = new Rectangle[rectanglesCnt];
      for (int i = 0; i < rectanglesCnt; i++) {
	    double x = input.nextDouble();
	    double y = input.nextDouble();
	    double width = input.nextDouble();
	    double height = input.nextDouble();
	    rectangles[i] = new Rectangle(x, y, width, height);
      }
      var marker = PlotMarker.valueOf(rectangles);
      return marker.isPointPresent(input.nextDouble(), input.nextDouble());
}

public Object solve(int taskNumber) {
      var supplier = taskMap.get(taskNumber);
      if (supplier != null) {
	    return supplier.get();
      } else {
	    throw new IllegalStateException("Invalid task number");
      }
}

public static void main(String[] args) {
      if (args.length != 0) {
	    try (InputStream ignored = Files.newInputStream(Path.of(args[0]))) {
		  System.out.println("Not now implemented");
	    } catch (IOException e) {
		  System.out.println("We have some troubles with you input file. Probably, it doesn't exist");
	    }
	    return;
      }
      var main = new Main(System.in);
      Scanner input = main.input;
      System.out.println("Enter task number)");
      try {
	    int taskNumber;
	    while ((taskNumber = input.nextInt()) < 0 || taskNumber >= TASK_CNT) {
		  System.out.println("Sorry. But you input invalid task number.");
		  System.out.printf("Enter task number (0..%d", TASK_CNT - 1);
	    }
	    System.out.println(main.solve(taskNumber));
      } catch (Exception e) {
	    System.out.println("Something goes wrong. Sorry. We cannot response(");
      }
}
}
