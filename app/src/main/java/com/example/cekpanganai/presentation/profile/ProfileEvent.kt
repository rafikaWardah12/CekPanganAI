package com.example.cekpanganai.presentation.profile

import com.example.cekpanganai.data.database.entity.UserEntity

sealed interface ProfileEvent {
    data class SaveProfile(val user: UserEntity) : ProfileEvent
}