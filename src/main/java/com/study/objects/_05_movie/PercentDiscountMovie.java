package com.study.objects._05_movie;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class PercentDiscountMovie extends Movie {

  private final BigDecimal discountPercent;

  public PercentDiscountMovie(
      String title,
      Duration runningTime,
      Money fee,
      List<DiscountCondition> conditions,
      BigDecimal discountPercent,
      MovieType type
  ) {
    super(title, runningTime, fee, conditions, type); // 부모 생성자 호출
    this.discountPercent = discountPercent;
  }


  @Override
  protected Money calculateDiscountAmount() {
    return getFee().times(discountPercent);
  }
}
