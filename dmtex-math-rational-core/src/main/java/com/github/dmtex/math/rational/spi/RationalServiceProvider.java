package com.github.dmtex.math.rational.spi;

import java.text.NumberFormat;

/**
 * {@code RationalServiceProvider} interface is SPI for Rational numbers.
 *
 * @author Denis Murashev
 *
 * @since Math Rational 1.0
 */
public interface RationalServiceProvider {

  /**
   * Provides default rational precision.
   *
   * @return precision
   */
  long rationalPrecision();

  /**
   * Provides max iterations amount to find appropriate ratio.
   *
   * @return max iterations
   */
  int maxIterations();

  /**
   * Provides formatter for rational numbers.
   *
   * @return {@link NumberFormat} instance
   */
  NumberFormat numberFormat();
}
