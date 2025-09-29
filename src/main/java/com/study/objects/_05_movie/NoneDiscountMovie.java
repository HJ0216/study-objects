package com.study.objects._05_movie;

import java.time.Duration;
import java.util.List;

public class NoneDiscountMovie extends Movie {

  public NoneDiscountMovie(
      String title,
      Duration runningTime, Money fee,
      List<DiscountCondition> conditions,
      Money discountAmount
  ) {
    super(title, runningTime, fee, conditions); // 부모 생성자 호출
  }

  @Override
  protected Money calculateDiscountAmount() {
    return Money.ZERO;
  }

}
