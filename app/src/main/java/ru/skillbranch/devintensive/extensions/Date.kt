package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
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

    var result: String = ""
    when{
        (diffSeconds >= 0 && diffSeconds <= 1) -> result = "только что"
        (diffSeconds > 1 && diffSeconds <= 45) -> result = "несколько секунд назад"
        (diffSeconds > 45 && diffSeconds <= 75) -> result = "минуту назад"
        (diffSeconds > 75 && diffMinutes <= 45) -> when(diffMinutes.toInt()){
            11 -> result = "$diffMinutes минут назад"
            12 -> result = "$diffMinutes минут назад"
            13 -> result = "$diffMinutes минут назад"
            14 -> result = "$diffMinutes минут назад"
            else -> when((diffMinutes).toInt() % 10){
                1 -> result = "$diffMinutes минуту назад"
                2 -> result = "$diffMinutes минуты назад"
                3 -> result = "$diffMinutes минуты назад"
                4 -> result = "$diffMinutes минуты назад"
                else -> result = "$diffMinutes минут назад"
            }
        }
        (diffMinutes > 45 && diffMinutes <= 75) -> result = "час назад"
        (diffMinutes > 75 && diffHours <= 22) -> when(diffHours.toInt()){
            11 -> result = "$diffHours часов назад"
            12 -> result = "$diffHours часов назад"
            13 -> result = "$diffHours часов назад"
            14 -> result = "$diffHours часов назад"
            else -> when((diffHours).toInt() % 10){
                1 -> result = "$diffHours час назад"
                2 -> result = "$diffHours часа назад"
                3 -> result = "$diffHours часа назад"
                4 -> result = "$diffHours часа назад"
                else -> result = "$diffHours часов назад"
            }
        }
        (diffHours > 22 && diffHours <= 26) -> result = "день назад"
        (diffHours > 26 && diffDays <= 360) -> when(diffDays.toInt()){
            11 -> result = "$diffDays дней назад"
            12 -> result = "$diffDays дней назад"
            13 -> result = "$diffDays дней назад"
            14 -> result = "$diffDays дней назад"
            else -> when((diffDays).toInt() % 10){
                1 -> result = "$diffDays день назад"
                2 -> result = "$diffDays дня назад"
                3 -> result = "$diffDays дня назад"
                4 -> result = "$diffDays дня назад"
                else -> result = "$diffDays дней назад"
            }
        }
        (diffDays > 360) -> result = "более года назад"

        (diffSeconds <= 0 && diffSeconds >= -1) -> result = "только что"
        (diffSeconds < -1 && diffSeconds >= -45) -> result = "через несколько секунд"
        (diffSeconds < -45 && diffSeconds >= -75) -> result = "через минуту"
        (diffSeconds < -75 && diffMinutes >= -45) -> when(diffMinutes.toInt()){
            -11 -> result = "через ${-diffMinutes} минут"
            -12 -> result = "через ${-diffMinutes} минут"
            -13 -> result = "через ${-diffMinutes} минут"
            -14 -> result = "через ${-diffMinutes} минут"
            else -> when(diffMinutes.toInt() % 10){
                -1 -> result = "через ${-diffMinutes} минуту"
                -2 -> result = "через ${-diffMinutes} минуты"
                -3 -> result = "через ${-diffMinutes} минуты"
                -4 -> result = "через ${-diffMinutes} минуты"
                else -> result = "через ${-diffMinutes} минут"
            }
        }
        (diffMinutes < -45 && diffMinutes >= -75) -> result = "час назад"
        (diffMinutes < -75 && diffHours >= -22) -> when(diffHours.toInt()){
            -11 -> result = "через ${-diffHours} часов"
            -12 -> result = "через ${-diffHours} часов"
            -13 -> result = "через ${-diffHours} часов"
            -14 -> result = "через ${-diffHours} часов"
            else -> when(diffHours.toInt() % 10){
                -1 -> result = "через ${-diffHours} час"
                -2 -> result = "через ${-diffHours} часа"
                -3 -> result = "через ${-diffHours} часа"
                -4 -> result = "через ${-diffHours} часа"
                else -> result = "через ${-diffHours} часов"
            }
        }
        (diffHours < -22 && diffHours >= -26) -> result = "день назад"
        (diffHours < -26 && diffDays >= -360) -> when(diffDays.toInt()){
            -11 -> result = "через ${-diffDays} дней"
            -12 -> result = "через ${-diffDays} дней"
            -13 -> result = "через ${-diffDays} дней"
            -14 -> result = "через ${-diffDays} дней"
            else -> when(diffDays.toInt() % 10){
                -1 -> result = "через ${-diffDays} день"
                -2 -> result = "через ${-diffDays} дня"
                -3 -> result = "через ${-diffDays} дня"
                -4 -> result = "через ${-diffDays} дня"
                else -> result = "через ${-diffDays} дней"
            }
        }
        (diffDays < -360) -> result = "более чем через год"
    }

    return result
}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}