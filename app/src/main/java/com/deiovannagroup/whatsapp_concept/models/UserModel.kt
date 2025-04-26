package com.deiovannagroup.whatsapp_concept.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var photo: String = "",
) : Parcelable
