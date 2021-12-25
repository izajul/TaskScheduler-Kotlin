package com.example.androidtaskscheduler.util

interface AdapterCallBack<T> {
    fun onSet(item: T){}
    fun onDelete(item: T){}
    fun onUpdate(item: T){}
}