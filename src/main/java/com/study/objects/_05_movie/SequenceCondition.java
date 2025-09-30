package com.study.objects._05_movie;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SequenceCondition implements DiscountCondition {

  private final int sequence;

  @Override
  public boolean isSatisfiedBy(Screening screening) {
    return screening.getSequence() == sequence;
  }
}
