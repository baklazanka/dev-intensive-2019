package ru.skillbranch.devintensive.extensions

import androidx.lifecycle.MutableLiveData

fun <T> mutableLiveData(defaulValue: T? = null): MutableLiveData<T> {
    val data = MutableLiveData<T>()

    if (defaulValue != null){
        data.value = defaulValue
    }

    return data
}