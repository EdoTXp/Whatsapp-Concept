package com.deiovannagroup.whatsapp_concept.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data model representing a user in the application.
 *
 * @property id The unique identifier of the user.
 * @property name The display name of the user.
 * @property email The email address of the user.
 * @property photo The URL of the user's profile photo.
 */
@Parcelize
data class UserModel(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var photo: String = "",
) : Parcelable
