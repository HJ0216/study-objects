package com.study.objects._02_movie;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Money {

  public static final Money ZERO = Money.wons(0);
  @Getter
  private final BigDecimal amount;

  public static Money wons(long amount) {
    return new Money(BigDecimal.valueOf(amount));
  }

  public static Money wons(double amount) {
    return new Money(BigDecimal.valueOf(amount));
  }

  public Money plus(Money amount) {
    return new Money(this.amount.add(amount.amount));
  }

  public Money minus(Money amount) {
    return new Money(this.amount.subtract(amount.amount));
  }

  public Money times(double percent) {
    return new Money(this.amount.multiply(BigDecimal.valueOf(percent)));
  }

  public boolean isLessThan(Money other) {
    return amount.compareTo(other.amount) < 0;
  }

  public boolean isGreaterThanOrEqual(Money other) {
    return amount.compareTo(other.amount) >= 0;
  }
}
