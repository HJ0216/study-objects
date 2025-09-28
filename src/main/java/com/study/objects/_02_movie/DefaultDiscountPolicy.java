package com.study.objects._02_movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DefaultDiscountPolicy implements DiscountPolicy {

  private final List<DiscountCondition> conditions;

  public DefaultDiscountPolicy(DiscountCondition... conditions) {
    this.conditions = new ArrayList<>(Arrays.asList(conditions));
  }

  public Money calculateDiscountAmount(Screening screening) {
    for (DiscountCondition each : conditions) {
      if (each.isSatisfiedBy(screening)) {
        return getDiscountAmount(screening);
      }
    }

    return Money.ZERO;
  }

  abstract protected Money getDiscountAmount(Screening screening);
}
