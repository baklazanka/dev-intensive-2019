package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16): String = "${this.substring(0, count).trim()}${if (this.trim().length > count) "..." else ""}"

fun String.stripHtml(): String = this.replace("""<.*?>""".toRegex(), "").replace("""\s+""".toRegex(), " ")