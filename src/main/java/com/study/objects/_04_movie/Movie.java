package com.study.objects._04_movie;

import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Movie {

  private final String title;
  private final Duration runningTime;
  private final Money fee;
  private final DiscountPolicy discountPolicy;

  public Money calculateDiscountedFee(Screening screening, int audienceCount) {
    return discountPolicy.calculateDiscountedFee(screening, fee, audienceCount);
  }
}
