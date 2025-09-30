package com.study.objects._05_movie;

import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Money {

  public static final Money ZERO = Money.wons(0);

  private final BigDecimal amount;

  public static Money wons(long amount) {
    return new Money(BigDecimal.valueOf(amount));
  }

  public Money minus(Money amount) {
    return new Money(this.amount.subtract(amount.amount));
  }

  public Money times(int multiplier) {
    return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)));
  }

  public Money times(double multiplier) {
    return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)));
  }

  public Money times(BigDecimal multiplier) {
    return new Money(this.amount.multiply(multiplier));
  }

}
