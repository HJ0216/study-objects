package com.study.objects._04_movie;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SequenceCondition implements DiscountCondition {

  private final int sequence;

  public boolean isDiscountable(Screening screening) {
    return screening.getSequence() == sequence;
  }
}
