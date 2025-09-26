package com.study.objects._01_ticket;

import java.util.ArrayDeque;
import java.util.Deque;

public class TicketOffice {

  private Long amount;
  private Deque<Ticket> tickets = new ArrayDeque<>();

  public TicketOffice(Long amount, Ticket... tickets) {
    this.amount = amount;
    for (Ticket ticket : tickets) {
      this.tickets.addLast(ticket);
    }
  }

  public void sellTicketTo(Audience audience) {
    plusAmount(audience.buy(getTicket()));
  }

  public Ticket getTicket() {
    return tickets.pollFirst();
  }

  public void plusAmount(Long amount) {
    this.amount += amount;
  }

  public void minusAmount(Long amount) {
    this.amount -= amount;
  }
}
