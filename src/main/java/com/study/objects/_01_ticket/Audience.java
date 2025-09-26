package com.study.objects._01_ticket;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Audience {

  private final Bag bag;

  public Long buy(Ticket ticket) {
    return bag.hold(ticket);
  }
}
