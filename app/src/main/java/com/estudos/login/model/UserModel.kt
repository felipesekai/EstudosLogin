package com.estudos.login.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class UserModel(val name: String, val email: String, val password: String) : Parcelable {
    constructor(name: String, email: String) : this(
        name=name, email=email,
        password="password")
}