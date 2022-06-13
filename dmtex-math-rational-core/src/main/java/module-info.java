module dmtex.math.rational {

  exports com.github.dmtex.math.rational;
  exports com.github.dmtex.math.rational.format;
  exports com.github.dmtex.math.rational.spi;

  provides com.github.dmtex.math.rational.spi.RationalServiceProvider
      with com.github.dmtex.math.rational.spi.DefaultRationalServiceProvider;

  uses com.github.dmtex.math.rational.spi.RationalServiceProvider;
}
