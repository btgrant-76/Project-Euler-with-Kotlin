package net.skeletonjack.projecteuler

import org.slf4j.LoggerFactory

/**
 * If the numbers 1 to 5 are written out in words: one, two, three, four, five, then there are
 * 3 + 3 + 5 + 4 + 4 = 19 letters used in total.
 *
 * If all the numbers from 1 to 1000 (one thousand) inclusive were written out in words,
 * how many letters would be used?
 *
 * NOTE: Do not count spaces or hyphens. For example, 342 (three hundred and forty-two) contains 23
 * letters and 115 (one hundred and fifteen) contains 20 letters. The use of "and" when writing out
 * numbers is in compliance with British usage.
 */
fun main(args: Array<String>) {
  trackTime(Pair(PROBLEM) {
    LOGGER.info("1 through 1000 written out in words produces ${countWordLetters(1, 1000)} letters")
  })
}

private const val PROBLEM = "seventeen"
private val LOGGER = LoggerFactory.getLogger(PROBLEM)

fun countWordLetters(first: Int, last: Int): Int {
  return (first..last).fold(0) { acc, i -> acc + countLettersInWord(i) }
}

fun countLettersInWord(number: Int): Int {

  val numberAsDigitStrings = "000$number" // ensure that there are always 4 digits
      .takeLast(4)
      .toList()

  val thousands = wordsForThousandsPlace(numberAsDigitStrings[0])
  val hundreds = wordsForHundredsPlace(numberAsDigitStrings[1])
  val tensAndOnes = wordsForTensAndOnesPlaces(numberAsDigitStrings.takeLast(2))

  // if the number is > 100 AND if tens or ones digits are non-zero
  val and = if (number > 100 && tensAndOnes.isNotEmpty()) {
    "And"
  } else {
    ""
  }

  val numberAsWords = "$thousands$hundreds$and$tensAndOnes"

  LOGGER.debug("$number in words is '$numberAsWords'")

  return numberAsWords.count()
}

private fun wordsForTensAndOnesPlaces(tensAndOnes: List<Char>): String {
  if (tensAndOnes[0] == '1') {
    return wordsForTenToNineteen(tensAndOnes.joinToString(""))
  } else if (tensAndOnes[0] == '0') {
    return writeOutSingleDigit(tensAndOnes[1])
  }

  return "${wordsForTensPlace(tensAndOnes[0])}${writeOutSingleDigit(tensAndOnes[1])}"
}

private fun wordsForTenToNineteen(tensAndOnes: String): String {
  return when (tensAndOnes) {
    "10" -> "Ten"
    "11" -> "Eleven"
    "12" -> "Twelve"
    "13" -> "Thirteen"
    "14" -> "Fourteen"
    "15" -> "Fifteen"
    "16" -> "Sixteen"
    "17" -> "Seventeen"
    "18" -> "Eighteen"
    "19" -> "Nineteen"
    else -> ""
  }
}

private fun wordsForTensPlace(tensPlace: Char): String {
  return when (tensPlace) {
    '2' -> "Twenty"
    '3' -> "Thirty"
    '4' -> "Forty"
    '5' -> "Fifty"
    '6' -> "Sixty"
    '7' -> "Seventy"
    '8' -> "Eighty"
    '9' -> "Ninety"
    else -> ""
  }
}

private fun wordsForHundredsPlace(hundredsDigit: Char): String {
  return emptyStringForZeroOrDefault(hundredsDigit, "${writeOutSingleDigit(hundredsDigit)}Hundred")
}

private fun wordsForThousandsPlace(thousandsDigit: Char): String {
  return emptyStringForZeroOrDefault(thousandsDigit,
      "${writeOutSingleDigit(thousandsDigit)}Thousand")
}

private fun writeOutSingleDigit(digit: Char) = when (digit) {
  '1' -> "One"
  '2' -> "Two"
  '3' -> "Three"
  '4' -> "Four"
  '5' -> "Five"
  '6' -> "Six"
  '7' -> "Seven"
  '8' -> "Eight"
  '9' -> "Nine"
  else -> ""
}

private fun emptyStringForZeroOrDefault(digit: Char, default: String) =
    if (digit == '0') {
      ""
    } else {
      default
    }
