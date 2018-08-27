package net.skeletonjack.projecteuler

/**
 * You are given the following information, but you may prefer to do some research for yourself.
 *
 * 1 Jan 1900 was a Monday.
 * Thirty days has September,
 * April, June and November.
 * All the rest have thirty-one,
 * Saving February alone,
 * Which has twenty-eight, rain or shine.
 * And on leap years, twenty-nine.
 * A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.
 *
 * How many Sundays fell on the first of the month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?
 */
fun main(args: Array<String>) {
  val yearStart = 1901
  val dayOfTheWeekOnWhichYearStartBegins = TUESDAY
  val yearEnd = 2000
  val sundaysOnTheFirstOfTheMonth =
      countSundaysOnTheFirstOfTheMonth(yearStart, dayOfTheWeekOnWhichYearStartBegins, yearEnd)

  logger.info("$sundaysOnTheFirstOfTheMonth Sundays fall on the first of the month between " +
              "$yearStart and $yearEnd.")
}

val logger = org.slf4j.LoggerFactory.getLogger("nineteen")

typealias LastDayOfMonthCalculator = (year: Int, startingDay: DayOfWeek) -> DayOfWeek
typealias DayOfWeek = Int

const val SUNDAY = 1
const val TUESDAY = 3
const val SATURDAY = 7
val DAYS_OF_THE_WEEK = 1..7
val MONTHS_OF_THE_YEAR = 1..12

fun countSundaysOnTheFirstOfTheMonth(yearStart: Int, firstDayOfJanuary: DayOfWeek, yearEnd: Int): Int {

  fun accumulateSundays(currentYear: Int, firstDayOfTheYear: DayOfWeek, accSundays: Int): Int {

    val calculatorsByMonth = listOf(
        thirtyOneDayMonth, // January
        makeLastDayOfMonthCalculatorForFebruary(currentYear), // February
        thirtyOneDayMonth, // March
        thirtyDayMonth, // April
        thirtyOneDayMonth, // May
        thirtyDayMonth, // June
        thirtyOneDayMonth, // July
        thirtyOneDayMonth, // August
        thirtyDayMonth, // September
        thirtyOneDayMonth, // October
        thirtyDayMonth, // November
        thirtyOneDayMonth// December
    )

    val initialValue = Pair(firstDayOfTheYear, accSundays)

    var monthCount = 1

    val sundaysOnTheFirst = calculatorsByMonth.fold(initialValue, { accumulator, calculator ->
      val firstWeekDayOfTheCurrentMonth = accumulator.first

      val sundaysOnFirstOfTheMonth =
          if (firstWeekDayOfTheCurrentMonth == SUNDAY) {
            logger.debug("${nameMonth(monthCount)} of $currentYear begins on a Sunday")
            accumulator.second + 1
          } else {
            accumulator.second
          }

      val lastWeekDayOfCurrentMonth = calculator(currentYear, firstWeekDayOfTheCurrentMonth)
      logger.debug("${nameMonth(monthCount)}, $currentYear begins on " +
          "${nameDay(firstWeekDayOfTheCurrentMonth)} and ends on ${nameDay(lastWeekDayOfCurrentMonth)}")

      val firstWeekDayOfNextMonth =
          if (lastWeekDayOfCurrentMonth == SATURDAY) {
            SUNDAY
          } else {
            lastWeekDayOfCurrentMonth + 1
          }

      monthCount += 1

      Pair(firstWeekDayOfNextMonth, sundaysOnFirstOfTheMonth)
    })

    return if (currentYear == yearEnd) {
      sundaysOnTheFirst.second
    } else {
      logger.debug("current year $currentYear is not equal to year end:  $yearEnd")
      accumulateSundays(currentYear + 1, sundaysOnTheFirst.first, sundaysOnTheFirst.second)
    }
  }

  return accumulateSundays(yearStart, firstDayOfJanuary, 0)
}

fun nameMonth(monthNumber: Int): String {
  return when (monthNumber) {
    1 -> "January"
    2 -> "February"
    3 -> "March"
    4 -> "April"
    5 -> "May"
    6 -> "June"
    7 -> "July"
    8 -> "August"
    9 -> "September"
    10 -> "October"
    11 -> "November"
    12 -> "December"
    else -> throw IllegalArgumentException("$monthNumber is not valid")
  }
}

fun nameDay(dayNumber: Int): String {
  return when (dayNumber) {
    1 -> "Sunday"
    2 -> "Monday"
    3 -> "Tuesday"
    4 -> "Wednesday"
    5 -> "Thursday"
    6 -> "Friday"
    7 -> "Saturday"
    else -> throw IllegalArgumentException("$dayNumber is not valid")
  }
}


val thirtyOneDayMonth: LastDayOfMonthCalculator = makeLastDayOfMonthCalculator(31)
val thirtyDayMonth: LastDayOfMonthCalculator = makeLastDayOfMonthCalculator(30)

// TODO is there a better name?
fun makeLastDayOfMonthCalculator(daysInMonth: Int): LastDayOfMonthCalculator {
  return { _: Int, startingDay: DayOfWeek ->
    val numberOfDayInFirstTheWeek = (startingDay..DAYS_OF_THE_WEEK.last).count() // TODO too clever? maybe just do the math?

    val numberOfFullWeeks: Int = ((daysInMonth - numberOfDayInFirstTheWeek) / 7)

    val daysBeforeTheLastWeek = (numberOfFullWeeks * 7) + numberOfDayInFirstTheWeek

    val remainingDays = daysInMonth - daysBeforeTheLastWeek

    if (remainingDays == 0) {
      7
    } else {
      remainingDays
    }
  }
}

fun makeLastDayOfMonthCalculatorForFebruary(year: Int): LastDayOfMonthCalculator {
  val daysInMonth = if (isLeapYear(year)) {
    logger.debug("$year is a leap year")
    29
  } else {
    28
  }
  return makeLastDayOfMonthCalculator(daysInMonth)
}

/**
 * A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.
 */
fun isLeapYear(year: Int): Boolean {
  return if (year % 100 == 0) {
    return year % 400 == 0
  } else {
    year % 4 == 0
  }
}

