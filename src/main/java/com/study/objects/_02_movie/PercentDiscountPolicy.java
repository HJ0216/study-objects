package com.study.objects._02_movie;

import java.math.BigDecimal;

public class PercentDiscountPolicy extends DefaultDiscountPolicy {

  private final BigDecimal percent;

  public PercentDiscountPolicy(BigDecimal percent, DiscountCondition... conditions) {
    super(conditions);
    this.percent = percent;
  }

  @Override
  protected Money getDiscountAmount(Screening screening) {
    return screening.getMovieFee().times(percent);
  }
}
