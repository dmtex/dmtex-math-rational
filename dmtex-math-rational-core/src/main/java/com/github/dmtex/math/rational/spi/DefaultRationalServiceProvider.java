package com.github.dmtex.math.rational.spi;

import com.github.dmtex.math.rational.format.RationalFormat;
import java.text.NumberFormat;

/**
 * {@code DefaultRationalServiceProvider} class is deafult SPI for Rational numbers.
 *
 * @author Denis Murashev
 *
 * @since Math Rational 1.0
 */
public class DefaultRationalServiceProvider implements RationalServiceProvider {

  private static final long DEFAULT_RATIONAL_PRECISION = 1000;

  private static final int DEFAULT_MAX_ITERATIONS = 32;

  private static final RationalFormat DEFAULT_RATIONAL_FORMAT = new RationalFormat();

  @Override
  public long rationalPrecision() {
    return DEFAULT_RATIONAL_PRECISION;
  }

  @Override
  public int maxIterations() {
    return DEFAULT_MAX_ITERATIONS;
  }

  @Override
  public NumberFormat numberFormat() {
    return DEFAULT_RATIONAL_FORMAT;
  }
}
