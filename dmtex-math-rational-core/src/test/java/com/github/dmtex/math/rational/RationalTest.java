package com.github.dmtex.math.rational;

import java.math.BigDecimal;
import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RationalTest {

  @Test
  void testCreateFail() {
    Assertions.assertThrows(ArithmeticException.class, () -> new Rational(1, 0));
  }

  @Test
  void testCreateNegative() {
    Rational actual = new Rational(1, -1);
    assertEquals(-1, actual.getX());
    assertEquals(1, actual.getY());
  }

  @Test
  void testCreateWithIntegral() {
    Rational actual = new Rational(1, 1, 2);
    assertEquals(3, actual.getX());
    assertEquals(2, actual.getY());
  }

  @Test
  void testCreateWithIntegralNegative() {
    Rational actual = new Rational(-1, 1, 2);
    assertEquals(-3, actual.getX());
    assertEquals(2, actual.getY());
  }

  @Test
  void testValueOfBigRational() {
    Number value = BigRational.ONE;
    Rational actual = Rational.valueOf(value);
    assertEquals(Rational.ONE, actual);
  }

  @Test
  void testValueOfPrecision() {
    Rational actual = Rational.valueOf(0.33333, 100000);
    Rational expected = new Rational(33333, 100000);
    assertEquals(expected, actual);
  }

  @Test
  void testValueOfNumberPrecision() {
    Number value = 0.33333;
    Rational actual = Rational.valueOf(value, 100000);
    Rational expected = new Rational(33333, 100000);
    assertEquals(expected, actual);
  }

  @Test
  void testIntegralAndNumerator() {
    Rational actual = Rational.valueOf(1.33333);
    assertEquals(1, actual.getIntegral());
    assertEquals(1, actual.getNumerator());
    assertEquals(3, actual.getDenominator());
  }

  @Test
  void testIntegralAndNumeratorNegative() {
    Rational actual = Rational.valueOf(-1.33333);
    assertEquals(-1, actual.getIntegral());
    assertEquals(1, actual.getNumerator());
    assertEquals(3, actual.getDenominator());
  }

  @Test
  void testAbs() {
    Rational actual = Rational.HALF.abs();
    Rational expected = Rational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testAbsNegate() {
    Rational actual = Rational.HALF.negate().abs();
    Rational expected = Rational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testAbsNegateFailure() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MIN_VALUE).negate());
  }

  @Test
  void testReciprocal() {
    Rational actual = Rational.HALF.reciprocal();
    Rational expected = Rational.TWO;
    assertEquals(expected, actual);
  }

  @Test
  void testPlus() {
    Number x = Rational.ONE_THIRD;
    Rational actual = Rational.HALF.plus(x);
    Rational expected = Rational.FIVE_SIXTH;
    assertEquals(expected, actual);
  }

  @Test
  void testPlusLong() {
    Rational actual = Rational.HALF.plus(1);
    Rational expected = new Rational(3, 2);
    assertEquals(expected, actual);
  }

  @Test
  void testPlusDouble() {
    Rational actual = Rational.HALF.plus(1.0);
    Rational expected = new Rational(3, 2);
    assertEquals(expected, actual);
  }

  @Test
  void testPlusDecimal() {
    Rational actual = Rational.HALF.plus(BigDecimal.valueOf(0.5));
    Rational expected = Rational.ONE;
    assertEquals(expected, actual);
  }

  @Test
  void testPlusFailure1() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MAX_VALUE).plus(Rational.ONE_THIRD));
  }

  @Test
  void testPlusFailure2() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MAX_VALUE).plus(1));
  }

  @Test
  void testPlusFailure3() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MIN_VALUE).plus(-1));
  }

  @Test
  void testMinus() {
    Number x = Rational.ONE_THIRD;
    Rational actual = Rational.HALF.minus(x);
    Rational expected = Rational.ONE_SIXTH;
    assertEquals(expected, actual);
  }

  @Test
  void testMinusLong() {
    Rational actual = Rational.HALF.minus(1);
    Rational expected = new Rational(-1, 2);
    assertEquals(expected, actual);
  }

  @Test
  void testMinusDouble() {
    Rational actual = Rational.HALF.minus(1.0);
    Rational expected = new Rational(-1, 2);
    assertEquals(expected, actual);
  }

  @Test
  void testMinusDecimal() {
    Rational actual = Rational.HALF.minus(BigDecimal.valueOf(0.5));
    Rational expected = Rational.ZERO;
    assertEquals(expected, actual);
  }

  @Test
  void testMinusFailure1() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MIN_VALUE).minus(Rational.ONE_THIRD));
  }

  @Test
  void testMinusFailure2() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MIN_VALUE).minus(1));
  }

  @Test
  void testMinusFailure3() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MAX_VALUE).minus(-1));
  }

  @Test
  void testtimes() {
    Number x = Rational.ONE_THIRD;
    Rational actual = Rational.HALF.times(x);
    Rational expected = Rational.ONE_SIXTH;
    assertEquals(expected, actual);
  }

  @Test
  void testtimesLong() {
    Rational actual = Rational.HALF.times(1);
    Rational expected = Rational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testtimesDouble() {
    Rational actual = Rational.HALF.times(1.0);
    Rational expected = Rational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testtimesDecimal() {
    Rational actual = Rational.HALF.times(BigDecimal.valueOf(0.5));
    Rational expected = Rational.ONE_FOURTH;
    assertEquals(expected, actual);
  }

  @Test
  void testtimesFailure1() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MIN_VALUE).times(Rational.TWO));
  }

  @Test
  void testtimesFailure2() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MIN_VALUE).times(-1));
  }

  @Test
  void testtimesFailure3() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MIN_VALUE).times(-2));
  }

  @Test
  void testDiv() {
    Number x = Rational.ONE_THIRD;
    Rational actual = Rational.HALF.div(x);
    Rational expected = new Rational(3, 2);
    assertEquals(expected, actual);
  }

  @Test
  void testDivLong() {
    Rational actual = Rational.HALF.div(1);
    Rational expected = Rational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testDivDouble() {
    Rational actual = Rational.HALF.div(1.0);
    Rational expected = Rational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testDivDecimal() {
    Rational actual = Rational.HALF.div(BigDecimal.valueOf(0.5));
    Rational expected = Rational.ONE;
    assertEquals(expected, actual);
  }

  @Test
  void testDivFail() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MIN_VALUE).div(Rational.HALF));
  }

  @Test
  void testMod() {
    Number x = Rational.ONE_THIRD;
    Rational actual = Rational.HALF.mod(x);
    Rational expected = Rational.ONE_SIXTH;
    assertEquals(expected, actual);
  }

  @Test
  void testModLong() {
    Rational actual = Rational.HALF.mod(1);
    Rational expected = Rational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testModDouble() {
    Rational actual = Rational.HALF.mod(1.0);
    Rational expected = Rational.HALF;
    assertEquals(expected, actual);
  }

  @Test
  void testModDecimal() {
    Rational actual = Rational.HALF.mod(BigDecimal.valueOf(0.5));
    Rational expected = Rational.ZERO;
    assertEquals(expected, actual);
  }

  @Test
  void testModFail() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MIN_VALUE, Long.MAX_VALUE).mod(Long.MAX_VALUE));
  }

  @Test
  void testPowerPositive() {
    Rational actual = Rational.HALF.power(2);
    Rational expected = Rational.ONE_FOURTH;
    assertEquals(expected, actual);
  }

  @Test
  void testPowerNegative() {
    Rational actual = Rational.HALF.power(-1);
    Rational expected = Rational.TWO;
    assertEquals(expected, actual);
  }

  @Test
  void testPowerOverflow() {
    assertThrows(ArithmeticException.class, () -> new Rational(Long.MAX_VALUE).power(2));
  }

  @Test
  void testRound() {
    Rational actual = Rational.HALF.round();
    Rational expected = Rational.ONE;
    assertEquals(expected, actual);
  }

  @Test
  void testFloor() {
    Rational actual = Rational.HALF.floor();
    Rational expected = Rational.ZERO;
    assertEquals(expected, actual);
  }

  @Test
  void testCeil() {
    Rational actual = Rational.HALF.ceil();
    Rational expected = Rational.ONE;
    assertEquals(expected, actual);
  }

  @Test
  void testIntegralFalse() {
    assertFalse(Rational.HALF.isIntegral());
  }

  @Test
  void testIntegralTrue() {
    assertTrue(Rational.ONE.isIntegral());
  }

  @Test
  void testCompareTo() {
    assertEquals(-1, Rational.HALF.compareTo(Rational.ONE));
  }

  @Test
  void testIntValue() {
    assertEquals(0, Rational.HALF.intValue());
  }

  @Test
  void testFloatValue() {
    assertEquals(0.5, Rational.HALF.floatValue());
  }

  @Test
  void testEqualsOther() {
    assertNotEquals(BigRational.HALF, Rational.HALF);
  }

  @Test
  void testNotEquals1() {
    assertNotEquals(Rational.HALF, Rational.ONE_THIRD);
  }

  @Test
  void testNotEquals2() {
    assertNotEquals(Rational.TWO_THIRD, Rational.ONE_THIRD);
  }

  @Test
  void testNotEquals3() {
    assertNotEquals(new RationalHolder(Rational.ONE), new RationalHolder(null));
  }

  @Test
  void testToString() {
    assertEquals("1/2", Rational.HALF.toString());
  }

  private static class RationalHolder {
    private final Rational value;

    private RationalHolder(Rational value) {
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
