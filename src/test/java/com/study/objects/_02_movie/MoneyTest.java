package com.study.objects._02_movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MoneyTest {


  @Nested
  @DisplayName("wons()는")
  class Describe_wons {

    @Nested
    @DisplayName("100L을 입력하면")
    class Context_with_100L {

      @Test
      @DisplayName("Money 객체를 반환한다")
      void it_returns_money_of_100() {
        // given
        long amount = 100L;

        // when
        Money money = Money.wons(amount);

        // then
        assertAmountEquals(amount, money);
      }
    }

    @Nested
    @DisplayName("100.5를 입력하면")
    class Context_with_100_5 {

      @Test
      @DisplayName("Money 객체를 반환한다")
      void it_returns_money_of_100_5() {
        // given
        double amount = 100.5;

        // when
        Money money = Money.wons(amount);

        // then
        assertAmountEquals(amount, money);
      }
    }

    private void assertAmountEquals(Number expected, Money actual) {
      assertEquals(0, actual.getAmount().compareTo(BigDecimal.valueOf(expected.doubleValue())));
    }
  }

  @Nested
  @DisplayName("plus()는")
  class Describe_plus {

    @Nested
    @DisplayName("100L을 입력하면")
    class Context_with_100L {

      @Test
      @DisplayName("100을 더한 Money 객체를 반환한다")
      void it_returns_money_plus_100() {
        // given
        Money amount = Money.wons(100L);

        // when
        Money originalAmount = Money.wons(350L);
        Money addedAmount = originalAmount.plus(amount);

        // then
        assertAmountEquals(Money.wons(350L), originalAmount);
        assertAmountEquals(Money.wons(450L), addedAmount);
      }
    }

    @Nested
    @DisplayName("Money.ZERO를 입력하면")
    class Context_with_money_zero {

      @Test
      @DisplayName("기존 값과 동일한 금액의 Money 객체를 반환한다")
      void it_returns_money_of_same_amount() {
        // given
        Money amount = Money.ZERO;

        // when
        Money originalAmount = Money.wons(350L);
        Money addedAmount = originalAmount.plus(amount);

        // then
        assertAmountEquals(Money.wons(350L), originalAmount);
        assertAmountEquals(originalAmount, addedAmount);
      }
    }
  }

  @Nested
  @DisplayName("minus()는")
  class Describe_minus {

    @Nested
    @DisplayName("100L을 입력하면")
    class Context_with_100L {

      @Test
      @DisplayName("100을 뺀 Money 객체를 반환한다")
      void it_returns_money_minus_100() {
        // given
        Money amount = Money.wons(100L);

        // when
        Money originalAmount = Money.wons(350L);
        Money subtractedAmount = originalAmount.minus(amount);

        // then
        assertAmountEquals(Money.wons(350L), originalAmount);
        assertAmountEquals(Money.wons(250L), subtractedAmount);
      }

      @Nested
      @DisplayName("Money.ZERO를 입력하면")
      class Context_with_money_zero {

        @Test
        @DisplayName("기존 값과 동일한 금액의 Money 객체를 반환한다")
        void it_returns_money_of_same_amount() {
          // given
          Money amount = Money.ZERO;

          // when
          Money originalAmount = Money.wons(350L);
          Money addedAmount = originalAmount.minus(amount);

          // then
          assertAmountEquals(Money.wons(350L), originalAmount);
          assertAmountEquals(originalAmount, addedAmount);
        }
      }

    }
  }

  @Nested
  @DisplayName("times()는")
  class Describe_times {

    @Nested
    @DisplayName("3을 입력하면")
    class Context_with_3 {

      @Test
      @DisplayName("3을 곱한 Money 객체를 반환한다")
      void it_returns_money_times_3() {
        // given
        double number = 3;

        Money originalAmount = Money.wons(350L);
        Money multipliedAmount = Money.wons(350L).times(number);

        // then
        assertAmountEquals(Money.wons(350L), originalAmount);
        assertAmountEquals(Money.wons(1050L), multipliedAmount);
      }
    }

    @Nested
    @DisplayName("1을 입력하면")
    class Context_with_1 {

      @Test
      @DisplayName("기존 값과 동일한 금액의 Money 객체를 반환한다")
      void it_returns_money_of_same_amount() {
        // given
        double number = 1;

        // when
        Money originalAmount = Money.wons(350L);
        Money multipliedAmount = Money.wons(350L).times(number);

        // then
        assertAmountEquals(Money.wons(350L), originalAmount);
        assertAmountEquals(originalAmount, multipliedAmount);
      }
    }

    @Nested
    @DisplayName("Money.ZERO를 입력하면")
    class Context_with_money_zero {

      @Test
      @DisplayName("0을 곱한 Money 객체를 반환한다")
      void it_returns_money_times_0() {
        // given
        double number = 0;

        Money originalAmount = Money.wons(350L);
        Money multipliedAmount = Money.wons(350L).times(number);

        // then
        assertAmountEquals(Money.wons(350L), originalAmount);
        assertAmountEquals(Money.wons(0L), multipliedAmount);
      }
    }

    @Nested
    @DisplayName("0.1을 입력하면")
    class Context_with_0_1 {

      @Test
      @DisplayName("0.1을 곱한 Money 객체를 반환한다")
      void it_returns_money_times_0_1() {
        // given
        double number = 0.1;

        Money originalAmount = Money.wons(350L);
        Money multipliedAmount = Money.wons(350L).times(number);

        // then
        assertAmountEquals(Money.wons(350L), originalAmount);
        assertAmountEquals(Money.wons(35L), multipliedAmount);
      }
    }

  }

  @Nested
  @DisplayName("isLessThan()은")
  class Describe_isLessThan {

    @Nested
    @DisplayName("7을 입력하면")
    class Context_with_7 {

      private static final Money SEVEN = Money.wons(7);

      @Test
      @DisplayName("기존 값이 7보다 작을 경우, true를 반환한다")
      void it_returns_true_less_than_7() {
        // given

        // when, then
        assertTrue(Money.wons(3).isLessThan(SEVEN));
      }

      @Test
      @DisplayName("기존 값이 7과 같을 경우, false를 반환한다")
      void it_returns_false_equals_7() {
        // given

        // when, then
        assertFalse(Money.wons(7).isLessThan(SEVEN));
      }

      @Test
      @DisplayName("기존 값이 7보다 클 경우, false를 반환한다")
      void it_returns_false_less_than_7() {
        // given

        // when, then
        assertFalse(Money.wons(10).isLessThan(SEVEN));
      }
    }
  }

  @Nested
  @DisplayName("isGreaterThanOrEqual()은")
  class Describe_isGreaterThanOrEqual {

    @Nested
    @DisplayName("50을 입력하면")
    class Context_with_50 {

      private static final Money FIFTY = Money.wons(50);

      @Test
      @DisplayName("기존 값이 50과 같을 경우, true를 반환한다")
      void it_returns_true_equals_50() {
        // given

        // when, then
        assertTrue(Money.wons(50).isGreaterThanOrEqual(FIFTY));
      }

      @Test
      @DisplayName("기존 값이 50보다 클 경우, true를 반환한다")
      void it_returns_true_greater_than_50() {
        // given

        // when, then
        assertTrue(Money.wons(51).isGreaterThanOrEqual(FIFTY));
      }

      @Test
      @DisplayName("기존 값이 50보다 작을 경우, false를 반환한다")
      void it_returns_false_less_than_50() {
        // given

        // when, then
        assertFalse(Money.wons(49).isGreaterThanOrEqual(FIFTY));
      }
    }
  }

  private void assertAmountEquals(Money expected, Money actual) {
    assertEquals(0, actual.getAmount().compareTo(expected.getAmount()));
  }

}