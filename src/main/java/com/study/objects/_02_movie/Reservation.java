package com.study.objects._02_movie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Reservation {

  private final Customer customer;
  @Getter
  private final Screening screening;
  @Getter
  private final Money money;
  private final int audienceCount;

}
