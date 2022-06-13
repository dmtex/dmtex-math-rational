package com.github.dmtex.math.rational;

import com.github.dmtex.math.rational.spi.RationalServiceProvider;
import java.util.Arrays;
import java.util.ServiceLoader;

final class RationalUtil {

  private static final RationalServiceProvider SPI = ServiceLoader.load(RationalServiceProvider.class)
      .findFirst()
      .orElseThrow();

  private RationalUtil() {
  }

  /**
   * Creates continued fraction from given value.
   *
   * @param value     value
   * @param maxCount  maxCount
   * @param precision precision (longest denominator)
   * @return chain fraction as list of {@code long}s
   */
  static long[] toFraction(double value, int maxCount, long precision) {
    int sign = (int) Math.signum(value);
    long[] chain = toContinuedFraction(sign * value, maxCount, precision);
    if (chain.length == 1) {
      return new long[] {sign * chain[0], 1};
    }
    int maxIndex = chain.length - 1;
    long n = 1;
    long d = chain[maxIndex];
    for (int i = maxIndex - 1; i > 0; i--) {
      long tmp = d;
      d = d * chain[i] + n;
      n = tmp;
    }
    return new long[] {sign * (chain[0] * d + n), d};
  }

  /**
   * Creates chain fraction from given value.
   *
   * @param value     value
   * @param precision precision (longest denominator)
   * @return chain fraction as list of {@code long}s
   */
  static long[] toFraction(double value, long precision) {
    return toFraction(value, SPI.maxIterations(), precision);
  }

  /**
   * Creates chain fraction from given value.
   *
   * @param value     value
   * @return chain fraction as list of {@code long}s
   */
  static long[] toFraction(double value) {
    return toFraction(value, SPI.rationalPrecision());
  }

  private static long[] toContinuedFraction(double value, int maxCount, long precision) {
    long[] values = new long[maxCount + 1];
    values[0] = (long) value;
    double x = 1.0 / (value - values[0]);
    if (Double.isInfinite(x)) {
      return new long[]{values[0]};
    }
    long max = values[0] == 0 ? 1 : values[0];
    for (int i = 1; i <= maxCount; i++) {
      values[i] = (long) x;
      if (values[i] == 0L || values[i] > precision) {
        return Arrays.copyOf(values, i == 1 ? 2 : i);
      }
      max *= values[i];
      if (max > precision) {
        return Arrays.copyOf(values, i == 1 ? 2 : i);
      }
      x = 1.0 / (x - values[i]);
    }
    return values;
  }

  /**
   * Formats given number.
   *
   * @param value value to be formatted
   * @return formatted value
   */
  static String format(Number value) {
    return SPI.numberFormat().format(value);
  }
}
