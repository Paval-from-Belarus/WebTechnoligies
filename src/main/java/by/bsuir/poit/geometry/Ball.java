package by.bsuir.poit.geometry;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
public record Ball (int weight, Color color) implements Comparable<Ball>{
private static final Random random = ThreadLocalRandom.current();
public static final int MAX_BALL_WEIGHT = 200;
public static Ball randomBall() {
      int weight = random.nextInt(MAX_BALL_WEIGHT);
      final Color[] colors = Color.values();
      return new Ball(weight, colors[random.nextInt(colors.length)]);
}

@Override
public int compareTo(Ball o) {
      return this.weight - o.weight;
}
}
