package com.study.objects._05_movie;

import java.time.Duration;
import java.util.List;

public class NoneDiscountMovie extends Movie {

  public NoneDiscountMovie(
      String title,
      Duration runningTime, Money fee,
      List<DiscountCondition> conditions,
      Money discountAmount,
      MovieType type
  ) {
    super(title, runningTime, fee, conditions, type); // 부모 생성자 호출
  }

  @Override
  protected Money calculateDiscountAmount() {
    return Money.ZERO;
  }

}
