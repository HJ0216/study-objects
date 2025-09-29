package com.study.objects._05_movie;

public class ReservationAgency {

  public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
    boolean discountable = checkDiscountable(screening);
    Money fee = calculateFee(screening, discountable, audienceCount);

    return createReservation(customer, screening, fee, audienceCount);
  }

  private Reservation createReservation(
      Customer customer,
      Screening screening,
      Money fee,
      int audienceCount
  ) {
    return new Reservation(customer, screening, fee, audienceCount);
  }

  private Money calculateFee(Screening screening, boolean discountable, int audienceCount) {
    if (discountable) {
      return screening.getMovie().getFee()
                      .minus(calculateDiscountedFee(screening.getMovie()))
                      .times(audienceCount);
    }

    return screening.getMovie().getFee().times(audienceCount);
  }

  private Money calculateDiscountedFee(Movie movie) {
    return movie.calculateDiscountAmount();
  }

  private boolean checkDiscountable(Screening screening) {
    return screening.getMovie().getConditions().stream()
                    .anyMatch(condition -> condition.isSatisfiedBy(screening));
  }
}
