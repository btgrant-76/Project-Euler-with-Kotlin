package net.skeletonjack.projecteuler

import org.junit.Assert.*
import org.junit.Test

class NineteenTest {

  companion object Constants {
    val SUNDAY: DayOfWeek = 1
    val MONDAY: DayOfWeek = 2
    val TUESDAY: DayOfWeek = 3
    val WEDNESDAY: DayOfWeek = 4
    val THURSDAY: DayOfWeek = 5
    val FRIDAY: DayOfWeek = 6
    val SATURDAY: DayOfWeek = 7

    /* https://www.miniwebtool.com/leap-years-list/?start_year=1900&end_year=2020 */
    val LEAP_YEARS_BETWEEN_1900_AND_2020 = listOf(1904, 1908, 1912, 1916, 1920, 1924, 1928, 1932,
        1936, 1940, 1944, 1948, 1952, 1956, 1960, 1964, 1968, 1972, 1976, 1980, 1984, 1988, 1992,
        1996, 2000, 2004, 2008, 2012, 2016, 2020)

    val YEARS_TO_TEST = 1900..2020
    val NON_LEAP_YEARS = YEARS_TO_TEST.filterNot { LEAP_YEARS_BETWEEN_1900_AND_2020.contains(it) }
    val LEAP_YEARS = LEAP_YEARS_BETWEEN_1900_AND_2020
  }

  @Test
  fun testBoundsOfDaysOfTheWeek() {
    assertTrue(DAYS_OF_THE_WEEK.contains(1))
    assertTrue(DAYS_OF_THE_WEEK.contains(7))

    assertFalse(DAYS_OF_THE_WEEK.contains(0))
    assertFalse(DAYS_OF_THE_WEEK.contains(8))
  }

  @Test
  fun testBoundsOfMonthsOfTheYear() {
    assertTrue(MONTHS_OF_THE_YEAR.contains(1))
    assertTrue(MONTHS_OF_THE_YEAR.contains(12))

    assertFalse(MONTHS_OF_THE_YEAR.contains(0))
    assertFalse(MONTHS_OF_THE_YEAR.contains(13))
  }

  @Test
  fun whenAMonthOfThirtyOneDaysBeginsOnAMondayItEndsOnAWednesday() {
    val wednesday = thirtyOneDayMonth(2018, 2)
    assertEquals(WEDNESDAY, wednesday)
  }

  @Test
  fun whenAMonthOfThirtyOneDaysBeginsOnAThursdayItEndsOnASaturday() {
    val saturday = thirtyOneDayMonth(2018, 5)
    assertEquals(SATURDAY, saturday)
  }

  @Test
  fun whenAMonthOfThirtyDaysBeginsOnAFridayItEndsOnASaturday() {
    val saturday = thirtyDayMonth(2018, 6)
    assertEquals(SATURDAY, saturday)
  }

  @Test
  fun whenAMonthOfThirtyDaysBeginsOnASaturdayItEndsOnASunday() {
    val sunday = thirtyDayMonth(2018, 7)
    assertEquals(SUNDAY, sunday)
  }

  @Test
  fun whenAMonthOfTwentyEightDaysBeginsOnAThursdayItEndsOnAWednesday() {
    val twentyEightDayMonthCalculator = makeLastDayOfMonthCalculator(28)
    val testMe = twentyEightDayMonthCalculator(2018, THURSDAY)
    assertEquals(WEDNESDAY, testMe)
  }

  @Test
  fun whenAMonthOfTwentyEightDaysBeginsOnAWednesdayItEndsOnATuesday() {
    val twentyEightDayMonthCalculator = makeLastDayOfMonthCalculator(28)
    val testMe = twentyEightDayMonthCalculator(2017, WEDNESDAY)
    assertEquals(TUESDAY, testMe)
  }

  @Test
  fun januaryAndOctoberBeginWithSundaysIn2017() {
    assertEquals(2, countSundaysOnTheFirstOfTheMonth(2017, 1, 2017))
  }

  @Test
  fun aprilAndJulyBeginWithSundaysIn2018() {
    assertEquals(2, countSundaysOnTheFirstOfTheMonth(2018, 2, 2018))
  }

  @Test
  fun fourMonthsBeginWithSundaysIn2017And2018() {
    assertEquals(4, countSundaysOnTheFirstOfTheMonth(2017, 1, 2018))
  }

  @Test
  fun fiveMonthsBeginWithSundaysIn2016And2018() {
    assertEquals(5, countSundaysOnTheFirstOfTheMonth(2016, FRIDAY, 2018))
  }

  @Test
  fun sundayFallsOnTheFirstOfTheMonthOnceIn2016() {
    assertEquals(1, countSundaysOnTheFirstOfTheMonth(2016, FRIDAY, 2016))
  }

  @Test
  fun whenFebruaryBeginsOnAWednesdayBetween1900And2020AndItIsNotALeapYearItEndsOnATuesday() {
    NON_LEAP_YEARS.forEach { year ->
      val testMe = makeLastDayOfMonthCalculatorForFebruary(year)
      assertEquals(TUESDAY, testMe(year, WEDNESDAY))
    }
  }

  @Test
  fun isLeapYearCorrectlyIdentifiesLeapYears() {
    LEAP_YEARS.forEach { year -> assertTrue(isLeapYear(year)) }
  }

  @Test
  fun isLeapYearCorrectlyIdentifiesNonLeapYears() {
    NON_LEAP_YEARS.forEach { year -> assertFalse(isLeapYear(year)) }
  }

  @Test
  fun whenFebruaryBeginsOnAWednesdayBetween1900And2020AndItIsALeapYearItEndsOnAWednesday() {
    LEAP_YEARS.forEach { year ->
      val testMe = makeLastDayOfMonthCalculatorForFebruary(year)
      assertEquals(WEDNESDAY, testMe(year, WEDNESDAY))
    }
  }

  @Test
  fun findTheSolution() {
    val sundaysOnTheFirstOfTheMonth = countSundaysOnTheFirstOfTheMonth(1901, TUESDAY, 2000)
    assertEquals(171, sundaysOnTheFirstOfTheMonth)
  }
}