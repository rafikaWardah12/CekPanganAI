package com.example.cekpanganai.ui.utils

sealed class Message<out R> {
    object Loading : Message<Nothing>()
    data class Success<out T>(val data: T) : Message<T>()
    data class Error(val message: String) : Message<Nothing>()
}