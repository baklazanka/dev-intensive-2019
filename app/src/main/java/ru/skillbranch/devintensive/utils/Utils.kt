package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        var _fullName = fullName
        if(_fullName == "" || _fullName == " "){
            _fullName = null
        }

        val parts: List<String>? = _fullName?.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if(firstName.isNullOrBlank() && lastName.isNullOrBlank()){
            return null
        }
        else if (firstName.isNullOrBlank() || lastName.isNullOrBlank()){
            var initial = if (firstName.isNullOrBlank()) lastName?.substring(0, 1) else firstName.substring(0, 1)
            return initial?.toUpperCase()
        }
        else{
            return (firstName.substring(0, 1) + lastName.substring(0, 1)).toUpperCase()
        }
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

        var resultString: String = ""

        for (letter in payload){
            if(!transliterationMap.containsKey(letter)){
                resultString += letter.toString()
                continue
            }
            resultString += transliterationMap[letter]
        }

        return resultString
    }
}