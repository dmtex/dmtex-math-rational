package com.github.dmtex.math.rational;

import java.util.Optional;

/**
 * {@code Rational} class represents rational (fractional) value.
 *
 * @author Denis Murashev
 *
 * @since Math Rational 1.0
 */
public final class Rational extends Number implements Comparable<Rational> {

  /**
   * Zero (0).
   */
  public static final Rational ZERO = new Rational(0);

  /**
   * One (1).
   */
  public static final Rational ONE = new Rational(1);

  /**
   * Two (2).
   */
  public static final Rational TWO = new Rational(2);

  /**
   * One half (1/2).
   */
  public static final Rational HALF = new Rational(1, 2);

  /**
   * One third (1/3).
   */
  public static final Rational ONE_THIRD = new Rational(1, 3);

  /**
   * Two thirds (2/3).
   */
  public static final Rational TWO_THIRD = new Rational(2, 3);

  /**
   * One fourth (1/4).
   */
  public static final Rational ONE_FOURTH = new Rational(1, 4);

  /**
   * Three fourths (3/4).
   */
  public static final Rational THREE_FOURTH = new Rational(3, 4);

  /**
   * One fifth (1/5).
   */
  public static final Rational ONE_FIFTH = new Rational(1L, 5L);

  /**
   * Two fifths (2/5).
   */
  public static final Rational TWO_FIFTH = new Rational(2L, 5L);

  /**
   * Three fifths (3/5).
   */
  public static final Rational THREE_FIFTH = new Rational(3L, 5L);

  /**
   * Four fifths (4/5).
   */
  public static final Rational FOUR_FIFTH = new Rational(4L, 5L);

  /**
   * One sixth (1/6).
   */
  public static final Rational ONE_SIXTH = new Rational(1L, 6L);

  /**
   * Five sixths (5/6).
   */
  public static final Rational FIVE_SIXTH = new Rational(5L, 6L);

  /**
   * One seventh (1/7).
   */
  public static final Rational ONE_SEVENTH = new Rational(1L, 7L);

  /**
   * One eighth (1/8).
   */
  public static final Rational ONE_EIGHTH = new Rational(1L, 8L);

  /**
   * Three eighths (3/8).
   */
  public static final Rational THREE_EIGHTH = new Rational(3L, 8L);

  /**
   * Five eighths (5/8).
   */
  public static final Rational FIVE_EIGHTH = new Rational(5L, 8L);

  /**
   * Seven eighths (7/8).
   */
  public static final Rational SEVEN_EIGHTH = new Rational(7L, 8L);

  /**
   * One ninth (1/9).
   */
  public static final Rational ONE_NINTH = new Rational(1L, 9L);

  /**
   * One tenth (1/10).
   */
  public static final Rational ONE_TENTH = new Rational(1L, 10L);

  private static final long serialVersionUID = -8417657122414418223L;

  private static final String ERROR_OVERFLOW = "Long overflow";

  private final long x;
  private final long y;

  /**
   * Initializes with given values.
   *
   * @param x numerator
   * @param y denominator
   */
  public Rational(long x, long y) {
    if (y == 0) {
      throw new ArithmeticException("Denominator cannot be zero");
    }
    int sign = y < 0 ? -1 : 1;
    long n = gcd(Math.abs(x), (long) sign * y);
    this.x = (long) sign * x / n;
    this.y = (long) sign * y / n;
  }

  /**
   * Initializes with given value.
   *
   * @param x numerator
   */
  public Rational(long x) {
    this.x = x;
    this.y = 1L;
  }

  /**
   * Initializes with given values.
   *
   * @param i integral part
   * @param x numerator
   * @param y denominator
   */
  public Rational(long i, long x, long y) {
    this(i < 0 ? i * y - x : i * y + x, y);
  }

  /**
   * Creates Rational from given value.
   *
   * @param value value
   * @return Rational
   */
  public static Rational valueOf(double value) {
    long[] fraction = RationalUtil.toFraction(value);
    return new Rational(fraction[0], fraction[1]);
  }

  /**
   * Creates Rational from given value.
   *
   * @param value     value
   * @param precision precision (recommended value for denominator)
   * @return Rational
   */
  public static Rational valueOf(double value, long precision) {
    long[] fraction = RationalUtil.toFraction(value, precision);
    return new Rational(fraction[0], fraction[1]);
  }

  /**
   * Creates Rational from given value.
   *
   * @param value value
   * @return Rational
   */
  public static Rational valueOf(Number value) {
    return get(value).orElseGet(() -> valueOf(value.doubleValue()));
  }

  /**
   * Creates Rational from given value.
   *
   * @param value     value
   * @param precision precision
   * @return Rational
   */
  public static Rational valueOf(Number value, long precision) {
    return get(value).orElse(valueOf(value.doubleValue(), precision));
  }

  private static Optional<Rational> get(Number value) {
    if (value instanceof Rational) {
      return Optional.of((Rational) value);
    }
    if (value instanceof BigRational) {
      return Optional.of(((BigRational) value).toRational());
    }
    return Optional.empty();
  }

  /**
   * Provides numerator.
   *
   * @return numerator
   */
  public long getX() {
    return x;
  }

  /**
   * Provides denominator.
   *
   * @return denominator
   */
  public long getY() {
    return y;
  }

  /**
   * Provides integral part.
   *
   * @return integral part
   */
  public long getIntegral() {
    return x / y;
  }

  /**
   * Provides numerator.
   *
   * @return numerator
   */
  public long getNumerator() {
    return Math.abs(x) % y;
  }

  /**
   * Provides denominator.
   *
   * @return denominator
   */
  public long getDenominator() {
    return y;
  }

  /**
   * Provides absolute value.
   *
   * @return absolute value
   */
  public Rational abs() {
    return x >= 0L ? this : negate();
  }

  /**
   * Provides negated value.
   *
   * @return negated value
   */
  public Rational negate() {
    return new Rational(safeNegate(x), y);
  }

  /**
   * Provides reciprocal value.
   *
   * @return reciprocal value
   */
  public Rational reciprocal() {
    return new Rational(y, x);
  }

  /**
   * Adds.
   *
   * @param value argument
   * @return sum
   */
  public Rational plus(Rational value) {
    long d = gcd(y, value.y);
    long valueD = value.y / d;
    return new Rational(safeAdd(safeMultiply(x, valueD), safeMultiply(y / d, value.x)), safeMultiply(y, valueD));
  }

  /**
   * Adds.
   *
   * @param value argument
   * @return sum
   */
  public Rational plus(double value) {
    return plus(valueOf(value));
  }

  /**
   * Adds.
   *
   * @param value argument
   * @return sum
   */
  public Rational plus(long value) {
    return new Rational(safeAdd(x, safeMultiply(y, value)), y);
  }

  /**
   * Adds.
   *
   * @param value argument
   * @return sum
   */
  public Rational plus(Number value) {
    return plus(valueOf(value));
  }

  /**
   * Subtracts.
   *
   * @param value argument
   * @return difference
   */
  public Rational minus(Rational value) {
    long d = gcd(y, value.y);
    long valueD = value.y / d;
    return new Rational(safeSubtract(safeMultiply(x, valueD), safeMultiply(y / d, value.x)),
        safeMultiply(y, valueD));
  }

  /**
   * Subtracts.
   *
   * @param value argument
   * @return difference
   */
  public Rational minus(double value) {
    return minus(valueOf(value));
  }

  /**
   * Subtracts.
   *
   * @param value argument
   * @return difference
   */
  public Rational minus(long value) {
    return new Rational(safeSubtract(x, safeMultiply(y, value)), y);
  }

  /**
   * Subtracts.
   *
   * @param value argument
   * @return difference
   */
  public Rational minus(Number value) {
    return minus(valueOf(value));
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public Rational multiply(Rational value) {
    long n = gcd(x, value.y);
    long d = gcd(y, value.x);
    return new Rational(safeMultiply(x / n, value.x / d), safeMultiply(y / d, value.y / n));
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public Rational multiply(double value) {
    return multiply(valueOf(value));
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public Rational multiply(long value) {
    return new Rational(safeMultiply(x, value), y);
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public Rational multiply(Number value) {
    return multiply(valueOf(value));
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public Rational times(Rational value) {
    return multiply(value);
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public Rational times(double value) {
    return multiply(value);
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public Rational times(long value) {
    return multiply(value);
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public Rational times(Number value) {
    return multiply(value);
  }

  /**
   * Divides.
   *
   * @param value argument
   * @return ratio
   */
  public Rational div(Rational value) {
    long n = gcd(x, value.x);
    long d = gcd(y, value.y);
    return new Rational(safeMultiply(x / n, value.y / d), safeMultiply(y / d, value.x / n));
  }

  /**
   * Divides.
   *
   * @param value argument
   * @return ratio
   */
  public Rational div(double value) {
    return div(valueOf(value));
  }

  /**
   * Divides.
   *
   * @param value argument
   * @return ratio
   */
  public Rational div(long value) {
    return new Rational(x, safeMultiply(y, value));
  }

  /**
   * Divides.
   *
   * @param value argument
   * @return ratio
   */
  public Rational div(Number value) {
    return div(valueOf(value));
  }

  /**
   * Calculates modulo.
   *
   * @param value argument
   * @return modulo
   */
  public Rational mod(Rational value) {
    long q = div(value).longValue();
    return minus(value.multiply(q));
  }

  /**
   * Calculates modulo.
   *
   * @param value argument
   * @return modulo
   */
  public Rational mod(double value) {
    return mod(valueOf(value));
  }

  /**
   * Calculates modulo.
   *
   * @param value argument
   * @return modulo
   */
  public Rational mod(long value) {
    long n = safeMultiply(x, value);
    long q = n / y;
    return new Rational(n, y).minus(new Rational(safeMultiply(q, value)));
  }

  /**
   * Calculates modulo.
   *
   * @param value argument
   * @return modulo
   */
  public Rational mod(Number value) {
    return mod(valueOf(value));
  }

  /**
   * Calculates modulo (reminder).
   *
   * @param value argument
   * @return modulo (reminder)
   */
  public Rational rem(Rational value) {
    return mod(value);
  }

  /**
   * Calculates modulo (reminder).
   *
   * @param value argument
   * @return modulo (reminder)
   */
  public Rational rem(double value) {
    return mod(value);
  }

  /**
   * Calculates modulo (reminder).
   *
   * @param value argument
   * @return modulo (reminder)
   */
  public Rational rem(long value) {
    return mod(value);
  }

  /**
   * Calculates modulo (reminder).
   *
   * @param value argument
   * @return modulo (reminder)
   */
  public Rational rem(Number value) {
    return mod(value);
  }

  /**
   * Calculates power.
   *
   * @param value argument
   * @return value to the given power
   */
  public Rational power(int value) {
    long n = 1L;
    long d = 1L;
    int i;
    if (value < 0) {
      for (i = 1; i <= -value; i++) {
        n = safeMultiply(n, y);
        d = safeMultiply(d, x);
      }
    } else {
      for (i = 1; i <= value; i++) {
        n = safeMultiply(n, x);
        d = safeMultiply(d, y);
      }
    }
    return new Rational(n, d);
  }

  /**
   * Provides rounded value.
   *
   * @return rounded value
   */
  public Rational round() {
    return new Rational(Math.round(doubleValue()));
  }

  /**
   * Provides floor value.
   *
   * @return floor value
   */
  public Rational floor() {
    return new Rational((long) Math.floor(doubleValue()));
  }

  /**
   * Provides ceiling value.
   *
   * @return ceiling value
   */
  public Rational ceil() {
    return new Rational((long) Math.ceil(doubleValue()));
  }

  /**
   * Checks if value is integral.
   *
   * @return {@code true} if value is integral
   */
  public boolean isIntegral() {
    return y == 1;
  }

  @Override
  public int compareTo(Rational o) {
    return Long.compare(minus(o).x, 0);
  }

  @Override
  public int intValue() {
    return (int) (x / y);
  }

  @Override
  public long longValue() {
    return x / y;
  }

  @Override
  public float floatValue() {
    return (float) x / (float) y;
  }

  @Override
  public double doubleValue() {
    return (double) x / (double) y;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = prime + Long.hashCode(x);
    return prime * result + Long.hashCode(y);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Rational other = (Rational) obj;
    return x == other.x && y == other.y;
  }

  @Override
  public String toString() {
    return RationalUtil.format(this);
  }

  private static long gcd(long a, long b) {
    long absA = Math.abs(a);
    long absB = Math.abs(b);
    long x = Math.max(absA, absB);
    long y = Math.min(absA, absB);
    while (y > 0L) {
      x -= x / y * y;
      if (x < y) {
        x ^= y;
        y ^= x;
        x ^= y;
      }
    }
    return x;
  }

  private static long safeAdd(long left, long right) {
    if (right > 0 && left > Long.MAX_VALUE - right) {
      throw new ArithmeticException(ERROR_OVERFLOW);
    }
    if (right < 0 && left < Long.MIN_VALUE - right) {
      throw new ArithmeticException(ERROR_OVERFLOW);
    }
    return left + right;
  }

  private static long safeSubtract(long left, long right) {
    if (right > 0 && left < Long.MIN_VALUE + right) {
      throw new ArithmeticException(ERROR_OVERFLOW);
    }
    if (right < 0 && left > Long.MAX_VALUE + right) {
      throw new ArithmeticException(ERROR_OVERFLOW);
    }
    return left - right;
  }

  private static long safeMultiply(long left, long right) {
    if (right <= 0 || left <= Long.MAX_VALUE / right && left >= Long.MIN_VALUE / right) {
      if (right < -1 && (left > Long.MIN_VALUE / right || left < Long.MAX_VALUE / right)) {
        throw new ArithmeticException(ERROR_OVERFLOW);
      }
      if (right == -1 && left == Long.MIN_VALUE) {
        throw new ArithmeticException(ERROR_OVERFLOW);
      }
      return left * right;
    }
    throw new ArithmeticException(ERROR_OVERFLOW);
  }

  private static long safeNegate(long a) {
    if (a == Long.MIN_VALUE) {
      throw new ArithmeticException(ERROR_OVERFLOW);
    }
    return -a;
  }
}
