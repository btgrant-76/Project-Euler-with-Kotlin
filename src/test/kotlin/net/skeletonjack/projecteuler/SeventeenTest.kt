package net.skeletonjack.projecteuler

import org.amshove.kluent.shouldEqual
import org.junit.jupiter.api.Test

internal class SeventeenTest {

  @Test
  fun `single-digit numbers yield the correct count`() {
    verifyMapOfNumbersToLetterCount(
        mapOf(
            1 to 3, // one
            2 to 3, // two
            6 to 3, // six
            4 to 4, // four
            5 to 4, // five
            9 to 4, // nine
            3 to 5, // three
            7 to 5, // seven
            8 to 5  // eight
        )
    )
  }

  @Test
  fun `the numbers 1 to 5 written in words have 19 letters`() {
    countWordLetters(1, 5) shouldEqual 19
  }

  @Test
  fun `the number 342 has 23 letters`() {
    countLettersInWord(342) shouldEqual 23
  }

  @Test
  fun `the number 115 contains 20 letters`() {
    countLettersInWord(115) shouldEqual 20
  }

  @Test
  fun `double-digit numbers yield the correct count`() {
    verifyMapOfNumbersToLetterCount(
        mapOf(
            40 to 5, // forty
            50 to 5, // fifty
            60 to 5, // sixty
            20 to 6, // twenty
            30 to 6, // thirty
            80 to 6, // eighty
            90 to 6, // ninety
            70 to 7  // seventy
        )
    )
  }

  @Test
  fun `10-19 have distinct names`() {
    verifyMapOfNumbersToLetterCount(
        mapOf(
            10 to 3, // ten
            11 to 6, // eleven
            12 to 6, // twelve
            15 to 7, // fifteen
            16 to 7, // sixteen
            13 to 8, // thirteen
            14 to 8, // fourteen
            18 to 8, // eighteen
            19 to 8, // nineteen
            17 to 9  // seventeen
        )
    )
  }

  @Test
  fun `23 has 11 letters`() {
    countLettersInWord(23) shouldEqual 11
  }

  @Test
  fun `three- or four-digit numbers ending with 16, 14, 19 or 17 yield the correct count`() {
    verifyMapOfNumbersToLetterCount(
        mapOf(
            114 to 21, // one hundred and fourteen
            519 to 22  // five hundred and nineteen
        )
    )
  }

  @Test
  fun `three-digit numbers yield the correct count`() {
    verifyMapOfNumbersToLetterCount(
        mapOf(
            100 to 10, // one hundred
            200 to 10, // two hundred
            600 to 10, // six hundred
            400 to 11, // four hundred
            500 to 11, // five hundred
            900 to 11, // nine hundred
            300 to 12, // three hundred
            700 to 12, // seven hundred
            800 to 12  // eight hundred
        )
    )
  }

  @Test
  fun `four-digit numbers yield the correct count`() {
    verifyMapOfNumbersToLetterCount(mapOf(
        1000 to 11, // one thousand
        2000 to 11, // two thousand
        6000 to 11, // six thousand
        4000 to 12, // four thousand
        5000 to 12, // five thousand
        9000 to 12, // nine thousand
        3000 to 13, // three thousand
        7000 to 13, // seven thousand
        8000 to 13  // eight thousand
    ))
  }

  @Test
  fun `one thousand has 11 letters`() {
    countLettersInWord(1000) shouldEqual 11
  }

  private fun verifyMapOfNumbersToLetterCount(map: Map<Int, Int>) {
    map.forEach {
      countLettersInWord(it.key) shouldEqual it.value
    }
  }

  @Test
  fun `all of the numbers from 1 to 1000 in words yields 21124 letters`() {
    countWordLetters(1, 1000) shouldEqual 21124
  }

}
