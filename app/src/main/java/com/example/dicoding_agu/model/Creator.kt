package com.example.dicoding_agu.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Creator(
    var username: String? = null,
    var avatar: String? = null
) : Parcelable

