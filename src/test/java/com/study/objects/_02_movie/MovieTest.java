package com.study.objects._02_movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieTest {

  private Movie movie;
  private DiscountPolicy mockPolicy;

  @Nested
  @DisplayName("calculateMovieFee()는")
  class Describe_calculateMovieFee {

    @Nested
    @DisplayName("할인 정책이 없으면")
    class Context_without_discount_policy {

      @BeforeEach
      void setUp() {
        mockPolicy = mock(NonDiscountPolicy.class);
        movie = new Movie(
            "StarWars",
            Duration.ofMinutes(210),
            Money.wons(10_000),
            mockPolicy
        );
      }

      @Test
      @DisplayName("기본 요금을 반환한다")
      void it_returns_base_fee() {
        // given
        Screening screening = new Screening(movie, 23, LocalDateTime.now());

        when(mockPolicy.calculateDiscountAmount(screening)).thenReturn(Money.ZERO);

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(10_000, fee);
      }
    }

    @Nested
    @DisplayName("금액 할인 정책이 있으면")
    class Context_with_amount_discount_policy {

      @BeforeEach
      void setUp() {
        mockPolicy = mock(AmountDiscountPolicy.class);
        movie = new Movie(
            "Avatar",
            Duration.ofMinutes(210),
            Money.wons(10_000),
            mockPolicy
        );
      }

      @ParameterizedTest
      @CsvSource({
          "1, 800",
          "10, 800"
      })
      @DisplayName("할인 회차 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_discount_sequence(int sequence, int discountAmount) {
        // given
        Screening screening = new Screening(movie, sequence,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(10, 0)));
        when(mockPolicy.calculateDiscountAmount(screening)).thenReturn(Money.wons(discountAmount));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(10_000 - discountAmount, fee);
      }


      @ParameterizedTest
      @CsvSource({
          "THURSDAY, 10, 0, 800",
          "THURSDAY, 20, 59, 800",
          "MONDAY, 10, 30, 800"
      })
      @DisplayName("할인 시간대 상영에 800원 할인된 요금을 반환한다")
      void it_returns_discounted_fee_during_discount_periods(DayOfWeek dayOfWeek, int hour, int minute, int discountAmount) {
        // given
        LocalDateTime dateTime = LocalDateTime.of(
            LocalDate.now().with(dayOfWeek),
            LocalTime.of(hour, minute)
        );
        Screening screening = new Screening(movie, 3, dateTime);
        when(mockPolicy.calculateDiscountAmount(screening)).thenReturn(Money.wons(discountAmount));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(10_000 - discountAmount, fee);
      }

      @Test
      @DisplayName("할인 조건에 맞지 않으면 기본 요금을 반환한다")
      void it_returns_base_fee_when_no_condition_met() {
        // given
        Screening screening = new Screening(movie, 5,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(15, 0)));
        when(mockPolicy.calculateDiscountAmount(screening)).thenReturn(Money.ZERO);

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(10_000, fee);
      }

      @Test
      @DisplayName("중복 할인 조건이 있어도 한 번만 할인된 요금을 반환한다")
      void it_returns_single_discounted_fee_when_multiple_conditions_met() {
        // given
        Screening screening = new Screening(movie, 10,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.THURSDAY), LocalTime.of(12, 0)));
        when(mockPolicy.calculateDiscountAmount(screening)).thenReturn(Money.wons(800));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_200, fee);
      }
    }

    @Nested
    @DisplayName("비율 할인 정책이 있으면")
    class Context_with_percent_discount_policy {

      @BeforeEach
      void setUp() {
        mockPolicy = mock(PercentDiscountPolicy.class);
        movie = new Movie(
            "Titanic",
            Duration.ofMinutes(180),
            Money.wons(11_000),
            mockPolicy
        );
      }

      @ParameterizedTest
      @CsvSource({
          "TUESDAY, 14, 00, 1100",
          "TUESDAY, 16, 59, 1100",
          "THURSDAY, 10, 30, 1100",
          "THURSDAY, 13, 59, 1100"
      })
      @DisplayName("할인 시간대 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_during_discount_periods(DayOfWeek dayOfWeek, int hour, int minute, int discountAmount) {
        // given
        LocalDateTime dateTime = LocalDateTime.of(
            LocalDate.now().with(dayOfWeek),
            LocalTime.of(hour, minute)
        );
        Screening screening = new Screening(movie, 3, dateTime);
        when(mockPolicy.calculateDiscountAmount(screening)).thenReturn(Money.wons(discountAmount));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(11_000 - discountAmount, fee);
      }


      @Test
      @DisplayName("2회차 상영에 10% 할인된 요금을 반환한다")
      void it_returns_discounted_fee_for_second_sequence() {
        // given
        Screening screening = new Screening(movie, 2,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(10, 0)));
        when(mockPolicy.calculateDiscountAmount(screening)).thenReturn(Money.wons(1_100));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_900, fee);
      }

      @Test
      @DisplayName("할인 조건에 맞지 않으면 기본 요금을 반환한다")
      void it_returns_base_fee_when_no_condition_met() {
        // given
        Screening screening = new Screening(movie, 5,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.SUNDAY), LocalTime.of(15, 0)));
        when(mockPolicy.calculateDiscountAmount(screening)).thenReturn(Money.ZERO);

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(11_000, fee);
      }

      @Test
      @DisplayName("중복 할인 조건이 있어도 한 번만 할인된 요금을 반환한다")
      void it_returns_single_discounted_fee_when_multiple_conditions_met() {
        // given
        Screening screening = new Screening(movie, 2,
            LocalDateTime.of(LocalDate.now().with(DayOfWeek.TUESDAY), LocalTime.of(14, 0)));
        when(mockPolicy.calculateDiscountAmount(screening)).thenReturn(Money.wons(1_100));

        // when
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(9_900, fee);
      }
    }

  }

  @Nested
  @DisplayName("changeDiscountPolicy()는")
  class Describe_changeDiscountPolicy {

    @Nested
    @DisplayName("유효한 할인 정책이라면")
    class Context_with_valid_discount_policy {

      private Movie movie;

      @BeforeEach
      void setUp() {
        movie = new Movie(
            "StarWars",
            Duration.ofMinutes(210),
            Money.wons(15_000),
            new AmountDiscountPolicy(Money.wons(1_000), new SequenceCondition(1))
        );
      }

      @Test
      @DisplayName("할인 정책을 변경한다")
      void it_changes_discount_policy() {
        // given
        Screening screening = new Screening(movie, 1,
            LocalDateTime.of(2025, 9, 28, 10, 0));
        DiscountPolicy newPolicy = mock(DiscountPolicy.class);
        when(newPolicy.calculateDiscountAmount(screening)).thenReturn(Money.wons(1_500));

        // when
        movie.changeDiscountPolicy(newPolicy);
        Money fee = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(13_500, fee);
        verify(newPolicy).calculateDiscountAmount(screening);
      }

      @Test
      @DisplayName("할인 정책을 제거한다")
      void it_removes_discount_policy() {
        // given
        Screening screening = new Screening(movie, 1, LocalDateTime.now());
        Money feeWithDiscount = movie.calculateMovieFee(screening);
        assertAmountEquals(14_000, feeWithDiscount);

        // when
        movie.changeDiscountPolicy(null);
        Money feeWithoutDiscount = movie.calculateMovieFee(screening);

        // then
        assertAmountEquals(15_000, feeWithoutDiscount);
      }
    }
  }

  private void assertAmountEquals(long expected, Money actual) {
    assertEquals(0, actual.getAmount().compareTo(BigDecimal.valueOf(expected)));
  }
}