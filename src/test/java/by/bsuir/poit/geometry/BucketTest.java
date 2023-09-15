package by.bsuir.poit.geometry;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
class BucketTest {
@Test
public void test() {
      final int ballCnt = 100;
      var list = new ArrayList<Ball>();
      for (int i = 0; i < ballCnt; i++) {
	    list.add(Ball.randomBall());
      }
      final int SMALL_BUCKET_CAPACITY = 50;
      var bucket = Bucket.of(50, list);
      assertTrue(bucket.getLoad() <= SMALL_BUCKET_CAPACITY);
      int bucketCapacity = list.stream()
			       .map(Ball::weight)
			       .reduce(0, Integer::sum);
      bucket = Bucket.of(bucketCapacity, list);
      assertEquals(bucket.getLoad(), bucketCapacity);
      assertEquals(ballCnt, bucket.balls().size());
      bucket = Bucket.of(bucketCapacity / 10, list);
      var hugeBall = new Ball(bucketCapacity, Color.Red);
      assertNull(bucket.addBall(hugeBall));
      var smallBall = new Ball(bucket.getRestCapacity(), Color.Red);
      assertNotNull(bucket.addBall(smallBall));
      throw new RuntimeException("Hello");
}

}