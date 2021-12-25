package com.example.androidtaskscheduler.util

interface DialogCallBack {
    fun onDismiss (value:Any?=null){}
    fun onSelect (value:Any){}
}