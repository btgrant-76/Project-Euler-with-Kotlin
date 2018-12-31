package net.skeletonjack.projecteuler

import org.slf4j.LoggerFactory

typealias Solution = Pair<String, () -> Unit>

fun trackTime(solution: Solution) {
  val nanoStartTime = System.nanoTime()
  val milliStartTime = System.currentTimeMillis()
  solution.second()
  val nanoEndTime = System.nanoTime()
  val milliEndTime = System.currentTimeMillis()
  val nanoRuntime = nanoEndTime - nanoStartTime
  val milliRuntime = milliEndTime - milliStartTime

  LoggerFactory.getLogger(solution.first).info("${solution.first} completed in $nanoRuntime nanoseconds/$milliRuntime milliseconds")
}


