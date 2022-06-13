package com.github.dmtex.math.rational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BigRationalTest {

  @Test
  void testCreateFail() {
    Assertions.assertThrows(ArithmeticException.class, () -> new BigRational(1, 0));
  }

  @Test
  void testCreateNegative() {
    BigRational actual = new BigRational(1, -1);
    assertEquals(BigInteger.ONE.negate(), actual.getX());
    assertEquals(BigInteger.ONE, actual.getY());
  }

  @Test
  void testCreateWithIntegral() {
    BigRational actual = new BigRational(1, 1, 2);
    assertEquals(BigInteger.valueOf(3), actual.getX());
    assertEquals(BigInteger.valueOf(2), actual.getY());
  }

  @Test
  void testCreateWithIntegralNegative() {
    BigRational actual = new BigRational(-1, 1, 2);
    assertEquals(BigInteger.valueOf(-3), actual.getX());
    assertEquals(BigInteger.valueOf(2), actual.getY());
  }

  @Test
  void testCreateFromLong() {
    BigRational actual = new BigRational(1);
    assertEquals(BigInteger.ONE, actual.getX());
    assertEquals(BigInteger.ONE, actual.getY());
  }

  @Test
  void testValueOfBigInteger() {
    Number value = BigInteger.ONE;
    BigRational actual = BigRational.valueOf(value);
    assertEquals(BigRational.ONE, actual);
  }

  @Test
  void testValueOfBigDecimal() {
    BigRational value = BigRational.valueOf(new BigDecimal("1.0").setScale(-1, RoundingMode.HALF_UP));
    assertEquals(BigRational.ZERO, value);
  }

  @Test
  void testValueOfPrecision() {
    BigRational actual = BigRational.valueOf(0.33333, 100000);
    BigRational expected = new BigRational(33333, 100000);
    assertEquals(expected, actual);
  }

  @Test
  void testValueOfNumberPrecision() {
    Number value = 0.33333;
    BigRational actual = BigRational.valueOf(value, 100000);
    BigRational expected = new BigRational(33333, 100000);
    assertEquals(expected, actual);
  }

  @Test
  void testIntegralAndNumerator() {
    BigRational actual = BigRational.valueOf(1.33333);
    assertEquals(BigInteger.ONE, actual.getIntegral());
    assertEquals(BigInteger.ONE, actual.getNumerator());
    assertEquals(BigInteger.valueOf(3), actual.getDenominator());
  }

  @Test
  void testIntegralAndNumeratorNegative() {
    BigRational actual = BigRational.valueOf(-1.33333);
    assertEquals(BigInteger.ONE.negate(), actual.getIntegral());
    assertEquals(BigInteger.ONE, actual.getNumerator());
    assertEquals(BigInteger.valueOf(3), actual.getDenominator());
  }

  @Test
  void testAbs() {
    BigRational actual = BigRational.HALF.abs();
    BigRational expected = BigRational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testAbsNegate() {
    BigRational actual = BigRational.HALF.negate().abs();
    BigRational expected = BigRational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testReciprocal() {
    BigRational actual = BigRational.HALF.reciprocal();
    BigRational expected = BigRational.TWO;
    assertEquals(expected, actual);
  }

  @Test
  void testPlus() {
    Number x = BigRational.ONE_THIRD;
    BigRational actual = BigRational.HALF.plus(x);
    BigRational expected = BigRational.FIVE_SIXTH;
    assertEquals(expected, actual);
  }

  @Test
  void testPlusLong() {
    BigRational actual = BigRational.HALF.plus(1);
    BigRational expected = new BigRational(3, 2);
    assertEquals(expected, actual);
  }

  @Test
  void testPlusDouble() {
    BigRational actual = BigRational.HALF.plus(1.0);
    BigRational expected = new BigRational(3, 2);
    assertEquals(expected, actual);
  }

  @Test
  void testPlusDecimal() {
    BigRational actual = BigRational.HALF.plus(BigDecimal.valueOf(0.5));
    BigRational expected = BigRational.ONE;
    assertEquals(expected, actual);
  }

  @Test
  void testMinus() {
    Number x = BigRational.ONE_THIRD;
    BigRational actual = BigRational.HALF.minus(x);
    BigRational expected = BigRational.ONE_SIXTH;
    assertEquals(expected, actual);
  }

  @Test
  void testMinusLong() {
    BigRational actual = BigRational.HALF.minus(1);
    BigRational expected = new BigRational(-1, 2);
    assertEquals(expected, actual);
  }

  @Test
  void testMinusDouble() {
    BigRational actual = BigRational.HALF.minus(1.0);
    BigRational expected = new BigRational(-1, 2);
    assertEquals(expected, actual);
  }

  @Test
  void testMinusDecimal() {
    BigRational actual = BigRational.HALF.minus(BigDecimal.valueOf(0.5));
    BigRational expected = BigRational.ZERO;
    assertEquals(expected, actual);
  }

  @Test
  void testtimes() {
    Number x = BigRational.ONE_THIRD;
    BigRational actual = BigRational.HALF.times(x);
    BigRational expected = BigRational.ONE_SIXTH;
    assertEquals(expected, actual);
  }

  @Test
  void testtimesLong() {
    BigRational actual = BigRational.HALF.times(1);
    BigRational expected = BigRational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testtimesDouble() {
    BigRational actual = BigRational.HALF.times(1.0);
    BigRational expected = BigRational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testtimesDecimal() {
    BigRational actual = BigRational.HALF.times(BigDecimal.valueOf(0.5));
    BigRational expected = BigRational.ONE_FOURTH;
    assertEquals(expected, actual);
  }

  @Test
  void testDiv() {
    Number x = BigRational.ONE_THIRD;
    BigRational actual = BigRational.HALF.div(x);
    BigRational expected = new BigRational(3, 2);
    assertEquals(expected, actual);
  }

  @Test
  void testDivLong() {
    BigRational actual = BigRational.HALF.div(1);
    BigRational expected = BigRational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testDivDouble() {
    BigRational actual = BigRational.HALF.div(1.0);
    BigRational expected = BigRational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testDivDecimal() {
    BigRational actual = BigRational.HALF.div(BigDecimal.valueOf(0.5));
    BigRational expected = BigRational.ONE;
    assertEquals(expected, actual);
  }

  @Test
  void testMod() {
    Number x = BigRational.ONE_THIRD;
    BigRational actual = BigRational.HALF.mod(x);
    BigRational expected = BigRational.ONE_SIXTH;
    assertEquals(expected, actual);
  }

  @Test
  void testModLong() {
    BigRational actual = BigRational.HALF.mod(1);
    BigRational expected = BigRational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testModDouble() {
    BigRational actual = BigRational.HALF.mod(1.0);
    BigRational expected = BigRational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testModDecimal() {
    BigRational actual = BigRational.HALF.mod(BigDecimal.valueOf(0.5));
    BigRational expected = BigRational.ZERO;
    assertEquals(expected, actual);
  }

  @Test
  void testPowerPositive() {
    BigRational actual = BigRational.HALF.power(2);
    BigRational expected = BigRational.ONE_FOURTH;
    assertEquals(expected, actual);
  }

  @Test
  void testPowerNegative() {
    BigRational actual = BigRational.HALF.power(-1);
    BigRational expected = BigRational.TWO;
    assertEquals(expected, actual);
  }

  @Test
  void testRound() {
    BigRational actual = BigRational.HALF.round();
    BigRational expected = BigRational.ONE;
    assertEquals(expected, actual);
  }

  @Test
  void testFloor() {
    BigRational actual = BigRational.HALF.floor();
    BigRational expected = BigRational.ZERO;
    assertEquals(expected, actual);
  }

  @Test
  void testCeil() {
    BigRational actual = BigRational.HALF.ceil();
    BigRational expected = BigRational.ONE;
    assertEquals(expected, actual);
  }

  @Test
  void testIntegralFalse() {
    assertFalse(BigRational.HALF.isIntegral());
  }

  @Test
  void testIntegralTrue() {
    assertTrue(BigRational.ONE.isIntegral());
  }

  @Test
  void testCompareTo() {
    assertEquals(-1, BigRational.HALF.compareTo(BigRational.ONE));
  }

  @Test
  void testToRational() {
    assertEquals(Rational.HALF, BigRational.HALF.toRational());
  }

  @Test
  void testToRationalCaseBig() {
    BigInteger numerator = new BigInteger("1000000000000000000000");
    BigInteger denominator = new BigInteger("2000000000000000000001");
    assertEquals(Rational.HALF, new BigRational(numerator, denominator).toRational());
  }

  @Test
  void testIntValue() {
    assertEquals(0, BigRational.HALF.intValue());
  }

  @Test
  void testLongValue() {
    assertEquals(0, BigRational.HALF.longValue());
  }

  @Test
  void testFloatValue() {
    assertEquals(0.5, BigRational.HALF.floatValue());
  }

  @Test
  void testEqualsOther() {
    assertNotEquals(Rational.HALF, BigRational.HALF);
  }

  @Test
  void testNotEquals1() {
    assertNotEquals(BigRational.HALF, BigRational.ONE_THIRD);
  }

  @Test
  void testNotEquals2() {
    assertNotEquals(BigRational.TWO_THIRD, BigRational.ONE_THIRD);
  }

  @Test
  void testNotEquals3() {
    assertNotEquals(new RationalHolder(BigRational.ONE), new RationalHolder(null));
  }

  @Test
  void testToString() {
    assertEquals("1/2", BigRational.HALF.toString());
  }

  @Test
  void testHashCode() {
    Map<BigRational, Integer> map = new HashMap<>();
    map.put(BigRational.ONE, 1);
    assertEquals(1, map.get(BigRational.ONE));
  }

  private static class RationalHolder {
    private final BigRational value;

    private RationalHolder(BigRational value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof RationalHolder)) {
        return false;
      }
      RationalHolder that = (RationalHolder) o;
      return value.equals(that.value);
    }

    @Override
    public int hashCode() {
      return Objects.hash(value);
    }
  }
}
