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

    fun transliteration(payload: String, divider: String = " "): String {
        return ""
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
            return (firstName?.substring(0, 1) + lastName?.substring(0, 1)).toUpperCase()
        }
    }
}