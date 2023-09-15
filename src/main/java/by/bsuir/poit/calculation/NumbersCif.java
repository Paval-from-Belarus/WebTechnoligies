package by.bsuir.poit.calculation;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Paval Shlyk
 * @since 01/09/2023
 */
public class NumbersCif {
private static final int DEFAULT_SIMPLES_CNT = 200;
private final AtomicReference<int[]> simples = new AtomicReference<>();

private final AtomicInteger lastMax = new AtomicInteger(-1);

public NumbersCif() {
      updateDivisors(DEFAULT_SIMPLES_CNT);
}

public NumbersCif(int primaryCnt) {
      updateDivisors(primaryCnt);
}
public int getLastMax() {
	return lastMax.get();
}
public int[] getDivisors() {
      return simples.get();
}
public void updateDivisors(Integer maxValue) {
      if (lastMax.get() >= maxValue) {
	    return;
      }
      boolean[] markers = new boolean[maxValue + 1];
      int simplesCnt = 0;
      for (int i = 2; i <= maxValue; i++) {
	    if (!markers[i]) {
		  simplesCnt += 1;
		  for (int j = 2; j * i <= maxValue; j++)
			markers[j * i] = true;
	    }
      }

      int[] simples = new int[simplesCnt];
      int index = 0;
      for (int i = 2; i <= maxValue; i++) {
	    if (!markers[i]) {
		  simples[index] = i;
		  index += 1;
	    }
      }
      // TODO: 01/09/2023 should be synchronized properly
      this.simples.set(simples);
      this.lastMax.set(maxValue);
}

}
