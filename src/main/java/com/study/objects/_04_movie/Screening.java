package com.study.objects._04_movie;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Screening {

  private final Movie movie;
  private final int sequence;
  private final LocalDateTime whenScreened;

  public Money calculateFee(int audienceCount) {
    return movie.calculateDiscountedFee(this, audienceCount);
  }
}
