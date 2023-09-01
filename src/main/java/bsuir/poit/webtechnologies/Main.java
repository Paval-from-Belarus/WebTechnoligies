package bsuir.poit.webtechnologies;

import bsuir.poit.webtechnologies.calculation.CalculationUtils;
import bsuir.poit.webtechnologies.geometry.PlotMarker;
import bsuir.poit.webtechnologies.geometry.Rectangle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
public class Main {
private final Scanner input;
public enum TaskType {
      First, Second
}

public Main(InputStream inputStream) {
      this.input = new Scanner(inputStream);
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
public Object solve(TaskType type) {
      return switch (type) {
	    case First -> solveFirstTask();
	    case Second -> solveSecondTask();
      };
}
public static void main(String[] args) {
      if (args.length != 0) {
	    try (InputStream ignored = Files.newInputStream(Path.of(args[0]))) {
		  System.out.println("Not now implemented");
	    } catch (IOException e) {
		  System.out.println("We have some troubles with you input file. Probably, it doesn't exist");
	    }
	    return;
      } else {
	    var main = new Main(System.in);
      }
      var main = new Main(System.in);
      Scanner input = main.input;
      System.out.println("Enter task number (0 or 1)");
      try {
	    int taskNumber;
	    while ((taskNumber = input.nextInt()) < 0 || taskNumber >= TaskType.values().length) {
		  System.out.println("Sorry. But you input invalid task number.");
		  System.out.println("Enter task number (0 or 1)");
	    }
	    System.out.println(main.solve(TaskType.values()[taskNumber]));
      } catch (Exception e) {
	    System.out.println("Something goes wrong. Sorry. We cannot response(");
      }
}
}
