package ru.skillbranch.devintensive.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import androidx.core.graphics.toRectF
import java.util.*
import kotlin.math.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        var localFullName = fullName
        if(localFullName == "" || localFullName == " "){
            localFullName = null
        }

        val parts: List<String>? = localFullName?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?): String? =
        if(firstName.isNullOrBlank() && lastName.isNullOrBlank()){
            null
        }
        else if (firstName.isNullOrBlank() || lastName.isNullOrBlank()){
            var initial = if (firstName.isNullOrBlank()) lastName?.substring(0, 1) else firstName.substring(0, 1)
            initial?.toUpperCase(Locale.ROOT)
        }
        else{
            (firstName.substring(0, 1) + lastName.substring(0, 1)).toUpperCase(Locale.ROOT)
        }

    fun transliteration(payload: String, divider: String = " "): String {
        val transliterationMap: Map<Char, String> = mapOf(
            'а' to "a",  'А' to "A",
            'б' to "b",  'Б' to "B",
            'в' to "v",  'В' to "V",
            'г' to "g",  'Г' to "G",
            'д' to "d",  'Д' to "D",
            'е' to "e",  'Е' to "E",
            'ё' to "e",  'Ё' to "E",
            'ж' to "zh", 'Ж' to "Zh",
            'з' to "z",  'З' to "Z",
            'и' to "i",  'И' to "I",
            'й' to "i",  'Й' to "I",
            'к' to "k",  'К' to "K",
            'л' to "l",  'Л' to "L",
            'м' to "m",  'М' to "M",
            'н' to "n",  'Н' to "N",
            'о' to "o",  'О' to "O",
            'п' to "p",  'П' to "P",
            'р' to "r",  'Р' to "R",
            'с' to "s",  'С' to "S",
            'т' to "t",  'Т' to "T",
            'у' to "u",  'У' to "U",
            'ф' to "f",  'Ф' to "F",
            'х' to "h",  'Х' to "H",
            'ц' to "c",  'Ц' to "C",
            'ч' to "ch", 'Ч' to "Ch",
            'ш' to "sh", 'Ш' to "Sh",
            'щ' to "sh'",'Щ' to "Sh'",
            'ъ' to "",   'Ъ' to "",
            'ы' to "i",  'Ы' to "I",
            'ь' to "",   'Ь' to "",
            'э' to "e",  'Э' to "E",
            'ю' to "yu", 'Ю' to "Yu",
            'я' to "ya", 'Я' to "Ya",
            ' ' to divider)

        var resultString = ""

        for (letter in payload){
            if(!transliterationMap.containsKey(letter)){
                resultString += letter.toString()
                continue
            }
            resultString += transliterationMap[letter]
        }

        return resultString
    }

    fun drawInitials(canvas: Canvas, initials: String, initialsPaint: Paint, viewRect: Rect) {
        initialsPaint.color = initialsToColor(initials)
        canvas.drawOval(viewRect.toRectF(), initialsPaint)
        with(initialsPaint){
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = viewRect.height() * 0.5f
        }
        val offsetY = (initialsPaint.descent() + initialsPaint.ascent()) / 2
        canvas.drawText(initials, viewRect.exactCenterX(), viewRect.exactCenterY() - offsetY, initialsPaint)
    }

    private fun initialsToColor(letters: String): Int {
        val bgColors = arrayOf(
            Color.parseColor("#7BC862"),
            Color.parseColor("#E17076"),
            Color.parseColor("#FAA774"),
            Color.parseColor("#6EC9CB"),
            Color.parseColor("#65AADD"),
            Color.parseColor("#A695E7"),
            Color.parseColor("#EE7AAE"),
            Color.parseColor("#2196F3")
        )

        val b = letters[0].toByte()
        val len = bgColors.size
        val d = b / len.toDouble()
        val index = ((d - truncate(d)) * len).toInt()
        return bgColors[index]
    }
}