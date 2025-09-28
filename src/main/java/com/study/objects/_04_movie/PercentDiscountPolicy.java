package com.study.objects._04_movie;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PercentDiscountPolicy implements  DiscountPolicy{

  private final double discountPercent;
  private final List<DiscountCondition> conditions;

  @Override
  public Money calculateDiscountedFee(Screening screening, Money fee, int audienceCount) {
    boolean discountable = conditions.stream().anyMatch(c -> c.isDiscountable(screening));
    if (discountable) {
      return fee.minus(fee.times(discountPercent)).times(audienceCount);
    }

    return fee.times(audienceCount);
  }
}
