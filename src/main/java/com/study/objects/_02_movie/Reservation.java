package com.study.objects._02_movie;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Reservation {

  private final Customer customer;
  private final Screening screening;
  private final Money money;
  private final int audienceCount;

}
