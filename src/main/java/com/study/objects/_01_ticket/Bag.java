package com.study.objects._01_ticket;

import lombok.Getter;

public class Bag {

  @Getter
  private Long amount;
  private Invitation invitation;
  private Ticket ticket;

  public Long hold(Ticket ticket) {
    setTicket(ticket);

    if (hasInvitation()) {
      return 0L;
    } else {
      minusAmount(ticket.getFee());
      return ticket.getFee();
    }
  }

  private void setTicket(Ticket ticket) {
    this.ticket = ticket;
  }

  private boolean hasInvitation() {
    return invitation != null;
  }

  public boolean hasTicket() {
    return ticket != null;
  }

  public void plusAmount(Long amount) {
    this.amount += amount;
  }

  private void minusAmount(Long amount) {
    this.amount -= amount;
  }

  public Bag(Long amount) {
    this.amount = amount;
  }

  public Bag(Long amount, Invitation invitation) {
    this.amount = amount;
    this.invitation = invitation;
  }
}
