package com.study.objects._04_movie;

public interface DiscountPolicy {

  Money calculateDiscountedFee(Screening screening, Money fee, int audienceCount);

}
