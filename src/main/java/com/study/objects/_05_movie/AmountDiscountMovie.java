package com.study.objects._05_movie;

import java.time.Duration;
import java.util.List;

public class AmountDiscountMovie extends Movie {

  private final Money discountAmount;

  public AmountDiscountMovie(
      String title,
      Duration runningTime,
      Money fee,
      List<DiscountCondition> conditions,
      Money discountAmount,
      MovieType type
  ) {
    super(title, runningTime, fee, conditions, type); // 부모 생성자 호출
    this.discountAmount = discountAmount;
  }

  @Override
  protected Money calculateDiscountAmount() {
    return discountAmount;
  }
}
