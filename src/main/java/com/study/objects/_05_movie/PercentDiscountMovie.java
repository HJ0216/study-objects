package com.study.objects._05_movie;

import java.time.Duration;
import java.util.List;

public class PercentDiscountMovie extends Movie {

  private final double discountPercent;

  public PercentDiscountMovie(
      String title,
      Duration runningTime,
      Money fee,
      List<DiscountCondition> conditions,
      double discountPercent
  ) {
    super(title, runningTime, fee, conditions); // 부모 생성자 호출
    this.discountPercent = discountPercent;
  }


  @Override
  protected Money calculateDiscountAmount() {
    return getFee().times(discountPercent);
  }
}
