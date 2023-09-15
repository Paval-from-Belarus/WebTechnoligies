package by.bsuir.poit.geometry;

import lombok.*;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
@Accessors(chain = true)
@RequiredArgsConstructor
public class Bucket {
@Getter
private final int capacity;
private final List<Ball> balls;
@Getter
private int restCapacity;

/**
 *
 * @param ball that will be attempted to add
 * @return null in case of absence of available space. Otherwise, return add ball (probably, this ball can be changed)
 */
public @Nullable Ball addBall(Ball ball) {
      Ball result = null;
      if (restCapacity >= ball.weight()) {
	    balls.add(ball);
	    restCapacity -= ball.weight();
	    result = ball;
      }
      return result;
}
public long getLoad() {
      return capacity - restCapacity;
}
public List<Ball> balls() {
      return Collections.unmodifiableList(balls);
}
/**
 * Create bucket with specific capacity and try to fill balls in bucket.
 * The less ball will be in bucket
 */
public static Bucket of(int capacity, List<Ball> balls) {
      List<Ball> retainBalls = new ArrayList<>();
      int restOfCapacity = capacity;
      for (Ball ball : balls) {
	    if (ball.weight() <= restOfCapacity) {
		  restOfCapacity -= ball.weight();
		  retainBalls.add(ball);
	    } else {
		  break;
	    }
      }
      var bucket = new Bucket(capacity, retainBalls);
      bucket.restCapacity = restOfCapacity;
      return bucket;
}
}
