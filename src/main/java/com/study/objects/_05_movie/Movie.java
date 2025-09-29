package com.study.objects._05_movie;

import java.time.Duration;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Movie {

  private final String title;
  private final Duration runningTime;
  private final Money fee;
  @Getter
  private final List<DiscountCondition> conditions;
  @Getter
  private final MovieType type;

  public Money calculateMovieFee(Screening screening) {
    if (isDiscountable(screening)) {
      return fee.minus(calculateDiscountAmount());
    }

    return fee;
  }

  private boolean isDiscountable(Screening screening) {
    return conditions.stream().anyMatch(condition -> condition.isSatisfiedBy(screening));
  }

  public Money getFee() {
    return fee;
  }

  abstract protected Money calculateDiscountAmount();

}
