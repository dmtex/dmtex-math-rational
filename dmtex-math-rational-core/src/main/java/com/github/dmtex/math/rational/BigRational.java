package com.github.dmtex.math.rational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;

/**
 * {@code BigRational} class represents rational (fractional) value with 'unlimited' precision.
 *
 * @author Denis Murashev
 *
 * @since Math Rational 1.0
 */
public final class BigRational extends Number implements Comparable<BigRational> {

  /**
   * Zero (0).
   */
  public static final BigRational ZERO = new BigRational(0);

  /**
   * One (1).
   */
  public static final BigRational ONE = new BigRational(1);

  /**
   * Two (2).
   */
  public static final BigRational TWO = new BigRational(2);

  /**
   * One half (1/2).
   */
  public static final BigRational HALF = new BigRational(1, 2);

  /**
   * One third (1/3).
   */
  public static final BigRational ONE_THIRD = new BigRational(1, 3);

  /**
   * Two thirds (2/3).
   */
  public static final BigRational TWO_THIRD = new BigRational(2, 3);

  /**
   * One fourth (1/4).
   */
  public static final BigRational ONE_FOURTH = new BigRational(1, 4);

  /**
   * Three fourths (3/4).
   */
  public static final BigRational THREE_FOURTH = new BigRational(3, 4);

  /**
   * One fifth (1/5).
   */
  public static final BigRational ONE_FIFTH = new BigRational(1L, 5L);

  /**
   * Two fifths (2/5).
   */
  public static final BigRational TWO_FIFTH = new BigRational(2L, 5L);

  /**
   * Three fifths (3/5).
   */
  public static final BigRational THREE_FIFTH = new BigRational(3L, 5L);

  /**
   * Four fifths (4/5).
   */
  public static final BigRational FOUR_FIFTH = new BigRational(4L, 5L);

  /**
   * One sixth (1/6).
   */
  public static final BigRational ONE_SIXTH = new BigRational(1L, 6L);

  /**
   * Five sixths (5/6).
   */
  public static final BigRational FIVE_SIXTH = new BigRational(5L, 6L);

  /**
   * One seventh (1/7).
   */
  public static final BigRational ONE_SEVENTH = new BigRational(1L, 7L);

  /**
   * One eighth (1/8).
   */
  public static final BigRational ONE_EIGHTH = new BigRational(1L, 8L);

  /**
   * Three eighths (3/8).
   */
  public static final BigRational THREE_EIGHTH = new BigRational(3L, 8L);

  /**
   * Five eighths (5/8).
   */
  public static final BigRational FIVE_EIGHTH = new BigRational(5L, 8L);

  /**
   * Seven eighths (7/8).
   */
  public static final BigRational SEVEN_EIGHTH = new BigRational(7L, 8L);

  /**
   * One ninth (1/9).
   */
  public static final BigRational ONE_NINTH = new BigRational(1L, 9L);

  /**
   * One tenth (1/10).
   */
  public static final BigRational ONE_TENTH = new BigRational(1L, 10L);

  private static final long serialVersionUID = 2595477445696927307L;

  private final BigInteger x;
  private final BigInteger y;

  /**
   * Initializes with given values.
   *
   * @param x numerator
   * @param y denominator
   */
  public BigRational(BigInteger x, BigInteger y) {
    if (BigInteger.ZERO.equals(y)) {
      throw new ArithmeticException("Denominator cannot be zero");
    }
    BigInteger sign = BigInteger.valueOf(y.signum());
    BigInteger n = x.abs().gcd(y.abs());
    this.x = x.divide(n).multiply(sign);
    this.y = y.divide(n).multiply(sign);
  }

  /**
   * Initializes with given value.
   *
   * @param x numerator
   */
  public BigRational(BigInteger x) {
    this.x = x;
    this.y = BigInteger.ONE;
  }

  /**
   * Initializes with given values.
   *
   * @param i integral part
   * @param x numerator
   * @param y denominator
   */
  public BigRational(BigInteger i, BigInteger x, BigInteger y) {
    this(i.signum() < 0 ? i.multiply(y).subtract(x) : i.multiply(y).add(x), y);
  }

  /**
   * Initializes with given values.
   *
   * @param x numerator
   * @param y denominator
   */
  public BigRational(long x, long y) {
    this(BigInteger.valueOf(x), BigInteger.valueOf(y));
  }

  /**
   * Initializes with given value.
   *
   * @param x numerator
   */
  public BigRational(long x) {
    this(BigInteger.valueOf(x));
  }

  /**
   * Initializes with given values.
   *
   * @param i integral part
   * @param x numerator
   * @param y denominator
   */
  public BigRational(long i, long x, long y) {
    this(BigInteger.valueOf(i), BigInteger.valueOf(x), BigInteger.valueOf(y));
  }

  /**
   * Creates BigRational from given value.
   *
   * @param value value
   * @return Rational
   */
  public static BigRational valueOf(double value) {
    long[] fraction = RationalUtil.toFraction(value);
    return new BigRational(fraction[0], fraction[1]);
  }

  /**
   * Creates BigRational from given value.
   *
   * @param value     value
   * @param precision precision
   * @return BigRational instance
   */
  public static BigRational valueOf(double value, long precision) {
    long[] fraction = RationalUtil.toFraction(value, precision);
    return new BigRational(fraction[0], fraction[1]);
  }

  /**
   * Creates BigRational from given Rational value.
   *
   * @param value Rational value
   * @return BigRational instance
   */
  public static BigRational valueOf(Rational value) {
    return new BigRational(value.getX(), value.getY());
  }

  /**
   * Creates BigRational from given Rational value.
   *
   * @param value Rational value
   * @return BigRational instance
   * @since Math 0.3
   */
  public static BigRational valueOf(BigDecimal value) {
    int scale = value.scale();
    if (scale < 0) {
      return new BigRational(value.unscaledValue().multiply(BigInteger.TEN.pow(-scale)));
    }
    BigInteger x = value.unscaledValue();
    BigInteger y = BigInteger.TEN.pow(scale);
    return new BigRational(x, y);
  }

  /**
   * Creates BigRational from given value.
   *
   * @param value value
   * @return BigRational instance
   */
  public static BigRational valueOf(Number value) {
    return get(value).orElseGet(() -> valueOf(value.doubleValue()));
  }

  /**
   * Creates BigRational from given value.
   *
   * @param value     value
   * @param precision precision (recommended value for denominator)
   * @return BigRational instance
   */
  public static BigRational valueOf(Number value, long precision) {
    return get(value).orElse(valueOf(value.doubleValue(), precision));
  }

  private static Optional<BigRational> get(Number value) {
    if (value instanceof BigRational) {
      return Optional.of((BigRational) value);
    }
    if (value instanceof Rational) {
      return Optional.of(valueOf((Rational) value));
    }
    if (value instanceof BigInteger) {
      return Optional.of(new BigRational((BigInteger) value));
    }
    if (value instanceof BigDecimal) {
      return Optional.of(valueOf((BigDecimal) value));
    }
    return Optional.empty();
  }

  /**
   * Provides numerator.
   *
   * @return numerator
   */
  public BigInteger getX() {
    return x;
  }

  /**
   * Provides denominator.
   *
   * @return denominator
   */
  public BigInteger getY() {
    return y;
  }

  /**
   * Provides integral part.
   *
   * @return integral part
   */
  public BigInteger getIntegral() {
    return x.divide(y);
  }

  /**
   * Provides numerator.
   *
   * @return numerator
   */
  public BigInteger getNumerator() {
    return x.abs().mod(y);
  }

  /**
   * Provides denominator.
   *
   * @return denominator
   */
  public BigInteger getDenominator() {
    return y;
  }

  /**
   * Provides absolute value.
   *
   * @return absolute value
   */
  public BigRational abs() {
    return x.signum() >= 0 ? this : negate();
  }

  /**
   * Provides negated value.
   *
   * @return negated value
   */
  public BigRational negate() {
    return new BigRational(x.negate(), y);
  }

  /**
   * Provides reciprocal value.
   *
   * @return reciprocal value
   */
  public BigRational reciprocal() {
    return new BigRational(y, x);
  }

  /**
   * Adds.
   *
   * @param value argument
   * @return sum
   */
  public BigRational plus(BigRational value) {
    return new BigRational(x.multiply(value.y).add(value.x.multiply(y)), y.multiply(value.y));
  }

  /**
   * Adds.
   *
   * @param value argument
   * @return sum
   */
  public BigRational plus(double value) {
    return plus(valueOf(value));
  }

  /**
   * Adds.
   *
   * @param value argument
   * @return sum
   */
  public BigRational plus(long value) {
    return plus(BigInteger.valueOf(value));
  }

  /**
   * Adds.
   *
   * @param value value
   * @return sum
   */
  public BigRational plus(BigInteger value) {
    return new BigRational(x.add(value.multiply(y)), y);
  }

  /**
   * Adds.
   *
   * @param value argument
   * @return sum
   */
  public BigRational plus(Number value) {
    return plus(valueOf(value));
  }

  /**
   * Subtracts.
   *
   * @param value argument
   * @return difference
   */
  public BigRational minus(BigRational value) {
    return new BigRational(x.multiply(value.y).subtract(value.x.multiply(y)), y.multiply(value.y));
  }

  /**
   * Subtracts.
   *
   * @param value argument
   * @return difference
   */
  public BigRational minus(double value) {
    return minus(valueOf(value));
  }

  /**
   * Subtracts.
   *
   * @param value argument
   * @return difference
   */
  public BigRational minus(long value) {
    return minus(BigInteger.valueOf(value));
  }

  /**
   * Subtracts.
   *
   * @param value value
   * @return difference
   */
  public BigRational minus(BigInteger value) {
    return new BigRational(x.subtract(value.multiply(y)), y);
  }

  /**
   * Subtracts.
   *
   * @param value argument
   * @return difference
   */
  public BigRational minus(Number value) {
    return minus(valueOf(value));
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public BigRational multiply(BigRational value) {
    return new BigRational(x.multiply(value.x), y.multiply(value.y));
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public BigRational multiply(double value) {
    return multiply(valueOf(value));
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public BigRational multiply(long value) {
    return multiply(BigInteger.valueOf(value));
  }

  /**
   * Multiplies.
   *
   * @param value value
   * @return product
   */
  public BigRational multiply(BigInteger value) {
    return new BigRational(x.multiply(value), y);
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public BigRational multiply(Number value) {
    return multiply(valueOf(value));
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public BigRational times(BigRational value) {
    return multiply(value);
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public BigRational times(double value) {
    return multiply(value);
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public BigRational times(long value) {
    return multiply(value);
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public BigRational times(BigInteger value) {
    return multiply(value);
  }

  /**
   * Multiplies.
   *
   * @param value argument
   * @return product
   */
  public BigRational times(Number value) {
    return multiply(value);
  }

  /**
   * Divides.
   *
   * @param value argument
   * @return ratio
   */
  public BigRational div(BigRational value) {
    return new BigRational(x.multiply(value.y), y.multiply(value.x));
  }

  /**
   * Divides.
   *
   * @param value argument
   * @return ratio
   */
  public BigRational div(double value) {
    return div(valueOf(value));
  }

  /**
   * Divides.
   *
   * @param value argument
   * @return ratio
   */
  public BigRational div(long value) {
    return div(BigInteger.valueOf(value));
  }

  /**
   * Divides.
   *
   * @param value value
   * @return ratio
   */
  public BigRational div(BigInteger value) {
    return new BigRational(x, y.multiply(value));
  }

  /**
   * Divides.
   *
   * @param value argument
   * @return ratio
   */
  public BigRational div(Number value) {
    return div(valueOf(value));
  }

  /**
   * Calculates modulo.
   *
   * @param value argument
   * @return modulo
   */
  public BigRational mod(BigRational value) {
    BigInteger q = div(value).toBigInteger();
    return minus(value.multiply(q));
  }

  /**
   * Calculates modulo.
   *
   * @param value argument
   * @return modulo
   */
  public BigRational mod(double value) {
    return mod(valueOf(value));
  }

  /**
   * Calculates modulo.
   *
   * @param value argument
   * @return modulo
   */
  public BigRational mod(long value) {
    return mod(BigInteger.valueOf(value));
  }

  /**
   * Calculates modulo.
   *
   * @param value value
   * @return modulo
   */
  public BigRational mod(BigInteger value) {
    BigInteger q = div(value).toBigInteger();
    return minus(value.multiply(q));
  }

  /**
   * Calculates modulo.
   *
   * @param value argument
   * @return modulo
   */
  public BigRational mod(Number value) {
    return mod(valueOf(value));
  }

  /**
   * Calculates modulo (reminder).
   *
   * @param value argument
   * @return modulo (reminder)
   */
  public BigRational rem(BigRational value) {
    return mod(value);
  }

  /**
   * Calculates modulo (reminder).
   *
   * @param value argument
   * @return modulo (reminder)
   */
  public BigRational rem(double value) {
    return mod(value);
  }

  /**
   * Calculates modulo (reminder).
   *
   * @param value argument
   * @return modulo (reminder)
   */
  public BigRational rem(long value) {
    return mod(value);
  }

  /**
   * Calculates modulo (reminder).
   *
   * @param value argument
   * @return modulo (reminder)
   */
  public BigRational rem(BigInteger value) {
    return mod(value);
  }

  /**
   * Calculates modulo (reminder).
   *
   * @param value argument
   * @return modulo (reminder)
   */
  public BigRational rem(Number value) {
    return mod(value);
  }

  /**
   * Calculates power.
   *
   * @param value argument
   * @return value to the given power
   */
  public BigRational power(int value) {
    BigInteger n = BigInteger.ONE;
    BigInteger d = BigInteger.ONE;
    int i;
    if (value < 0) {
      for (i = 1; i <= -value; ++i) {
        n = n.multiply(this.y);
        d = d.multiply(this.x);
      }
    } else {
      for (i = 1; i <= value; ++i) {
        n = n.multiply(this.x);
        d = d.multiply(this.y);
      }
    }

    return new BigRational(n, d);
  }

  /**
   * Provides rounded value.
   *
   * @return rounded value
   */
  public BigRational round() {
    return new BigRational(Math.round(doubleValue()));
  }

  /**
   * Provides floor value.
   *
   * @return floor value
   */
  public BigRational floor() {
    return new BigRational((long) Math.floor(doubleValue()));
  }

  /**
   * Provides ceiling value.
   *
   * @return ceiling value
   */
  public BigRational ceil() {
    return new BigRational((long) Math.ceil(doubleValue()));
  }

  /**
   * Checks if value is integral.
   *
   * @return {@code true} if value is integral
   */
  public boolean isIntegral() {
    return BigInteger.ONE.equals(y);
  }

  @Override
  public int compareTo(BigRational o) {
    return minus(o).x.compareTo(BigInteger.ZERO);
  }

  /**
   * Converts type.
   *
   * @return converted to {@link BigInteger}
   */
  public BigInteger toBigInteger() {
    return x.divide(y);
  }

  /**
   * Converts type.
   *
   * @return converted to {@link Rational}
   */
  public Rational toRational() {
    final int longBits = 64;
    return x.bitLength() < longBits && y.bitLength() < longBits
        ? new Rational(x.longValueExact(), y.longValueExact())
        : Rational.valueOf(doubleValue());
  }

  @Override
  public int intValue() {
    return x.divide(y).intValue();
  }

  @Override
  public long longValue() {
    return x.divide(y).longValue();
  }

  @Override
  public float floatValue() {
    return (float) (x.doubleValue() / y.doubleValue());
  }

  @Override
  public double doubleValue() {
    return x.doubleValue() / y.doubleValue();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = prime + x.hashCode();
    return prime * result + y.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    BigRational other = (BigRational) obj;
    return Objects.equals(x, other.x) && Objects.equals(y, other.y);
  }

  @Override
  public String toString() {
    return RationalUtil.format(this);
  }
}
