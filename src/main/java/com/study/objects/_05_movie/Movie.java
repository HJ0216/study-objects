package com.study.objects._05_movie;

import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Movie {

  private final String title;
  private final Duration runningTime;
  private final Money fee;
  private final List<DiscountCondition> conditions;

  public Money calculateMovieFee(Screening screening) {
    if (isDiscountable(screening)) {
      return fee.minus(calculateDiscountAmount());
    }

    return fee;
  }

  private boolean isDiscountable(Screening screening) {
    return conditions.stream().anyMatch(condition -> condition.isSatisfiedBy(screening));
  }

  protected Money getFee() {
    return fee;
  }

  abstract protected Money calculateDiscountAmount();

}
