package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.shortFormat(): String {
    val pattern = if (this.isSameDay(Date())) "HH:mm" else "dd.MM.yy"
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.isSameDay(date: Date): Boolean {
    val day1 = this.time / DAY
    val day2 = date.time / DAY
    return day1 == day2
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when(units){
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val diff = date.time - this.time
    val diffSeconds = diff / SECOND
    val diffMinutes = diff / MINUTE
    val diffHours = diff / HOUR
    val diffDays = diff / DAY

    val absDiffSeconds = abs(diffSeconds)
    val absDiffMinutes = abs(diffMinutes)
    val absDiffHours = abs(diffHours)
    val absDiffDays = abs(diffDays)

    val result: String = when{
        (absDiffSeconds in 2..45) -> "несколько секунд"
        (absDiffSeconds in 46..75) -> "минуту"
        (absDiffSeconds > 75 && absDiffMinutes <= 45) -> TimeUnits.MINUTE.plural(absDiffMinutes.toInt())
        (absDiffMinutes in 46..75) -> "час"
        (absDiffMinutes > 75 && absDiffHours <= 22) -> TimeUnits.HOUR.plural(absDiffHours.toInt())
        (absDiffHours in 23..26) -> "день"
        (absDiffHours > 26 && absDiffDays <= 360) -> TimeUnits.DAY.plural(absDiffDays.toInt())
        else -> ""
    }

    return when{
        (absDiffSeconds in 0..1) -> "только что"
        (diffDays > 360) -> "более года назад"
        (diffDays < -360) -> "более чем через год"
        else -> when{
            (diffSeconds >= 0) -> "$result назад"
            (diffSeconds < 0) -> "через $result"
            else -> ""
        }
    }
}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String = when(this){
        SECOND -> when(value){
            11, 12, 13, 14 -> "$value секунд"
            else -> when(value % 10){
                1 -> "$value секунду"
                2, 3, 4 -> "$value секунды"
                else -> "$value секунд"
            }
        }
        MINUTE -> when(value){
            11, 12, 13, 14 -> "$value минут"
            else -> when(value % 10){
                1 -> "$value минуту"
                2, 3, 4 -> "$value минуты"
                else -> "$value минут"
            }
        }
        HOUR -> when(value){
            11, 12, 13, 14 -> "$value часов"
            else -> when(value % 10){
                1 -> "$value час"
                2, 3, 4 -> "$value часа"
                else -> "$value часов"
            }
        }
        DAY -> when(value){
            11, 12, 13, 14 -> "$value дней"
            else -> when(value % 10){
                1 -> "$value день"
                2, 3, 4 -> "$value дня"
                else -> "$value дней"
            }
        }
    }
}