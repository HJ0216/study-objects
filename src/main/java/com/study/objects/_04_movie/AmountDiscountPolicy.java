package com.study.objects._04_movie;

import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AmountDiscountPolicy implements DiscountPolicy {

  private final Money discountAmount;
  private final List<DiscountCondition> conditions;

  @Override
  public Money calculateDiscountedFee(Screening screening, Money fee, int audienceCount) {
    boolean discountable = conditions.stream().anyMatch(c -> c.isDiscountable(screening));
    if (discountable) {
      return fee.minus(discountAmount).times(audienceCount);
    }

    return fee.times(audienceCount);
  }
}
