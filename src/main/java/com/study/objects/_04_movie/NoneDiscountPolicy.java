package com.study.objects._04_movie;


public class NoneDiscountPolicy implements DiscountPolicy {

  @Override
  public Money calculateDiscountedFee(Screening screening, Money fee, int audienceCount) {
    return fee.times(audienceCount);
  }
}
