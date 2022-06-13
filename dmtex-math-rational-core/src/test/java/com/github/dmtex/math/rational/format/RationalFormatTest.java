package com.github.dmtex.math.rational.format;

import com.github.dmtex.math.rational.BigRational;
import com.github.dmtex.math.rational.Rational;
import java.math.BigInteger;
import java.text.DecimalFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RationalFormatTest {

  @Test
  void testFormatDoublePrimitive() {
    assertThrows(UnsupportedOperationException.class, () -> new RationalFormat().format(1.5));
  }

  @Test
  void testFormatLongPrimitive() {
    assertThrows(UnsupportedOperationException.class, () -> new RationalFormat().format(2));
  }

  @Test
  void testFormatDouble() {
    String actual = new RationalFormat().format(Double.valueOf(1.5));
    assertEquals(DecimalFormat.getInstance().format(1.5), actual);
  }

  @Test
  void testFormatLong() {
    String actual = new RationalFormat().format(Long.valueOf(2));
    assertEquals("2", actual);
  }

  @Test
  void testFormatRational() {
    String actual = new RationalFormat().format(Rational.valueOf(1.5));
    assertEquals("1 1/2", actual);
  }

  @Test
  void testFormatRational2() {
    String actual = new RationalFormat().format(Rational.TWO);
    assertEquals("2", actual);
  }

  @Test
  void testFormatRationalZero() {
    String actual = new RationalFormat().format(Rational.ZERO);
    assertEquals("0", actual);
  }

  @Test
  void testFormatBigRational() {
    String actual = new RationalFormat().format(BigRational.valueOf(1.5));
    assertEquals("1 1/2", actual);
  }

  @Test
  void testFormatBigRational2() {
    String actual = new RationalFormat().format(BigRational.TWO);
    assertEquals("2", actual);
  }

  @Test
  void testFormatBigRationalZero() {
    String actual = new RationalFormat().format(BigRational.ZERO);
    assertEquals("0", actual);
  }

  @Test
  void testParseFailure() {
    assertThrows(IllegalArgumentException.class, () -> new RationalFormat().parse("a"));
  }

  @Test
  void testParse1() {
    Number actual = new RationalFormat().parse("1", null);
    assertTrue(actual instanceof Rational);
    assertEquals(Rational.ONE, actual);
  }

  @Test
  void testParse2() {
    Number actual = new RationalFormat().parse("1/2");
    assertTrue(actual instanceof Rational);
    assertEquals(Rational.HALF, actual);
  }

  @Test
  void testParse3() {
    Number actual = new RationalFormat().parse("1 1/2");
    assertTrue(actual instanceof Rational);
    assertEquals(new Rational(3, 2), actual);
  }

  @Test
  void testParseBigRational1() {
    String source = "1000000000000000000";
    Number actual = new RationalFormat().parse(source);
    assertEquals(new BigRational(new BigInteger(source)), actual);
  }

  @Test
  void testParseBigRational2() {
    String source = "1000000000000000000";
    Number actual = new RationalFormat().parse(source + " 1/10");
    assertEquals(new BigRational(new BigInteger(source), BigInteger.ONE, BigInteger.TEN), actual);
  }
}
