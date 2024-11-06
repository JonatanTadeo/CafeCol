package com.example.appcafecol

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class Perfil(
    val nombre: String = "",
    val edad: String = "",
    val correo: String = "",
    val numero: String = "",
    val ubicacion: String = "",
    val experiencia: String = "",
    val otrasExperiencias: String = "",
    val dateCreated: Timestamp = Timestamp.now()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(Timestamp::class.java.classLoader) ?: Timestamp.now()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(edad)
        parcel.writeString(correo)
        parcel.writeString(numero)
        parcel.writeString(ubicacion)
        parcel.writeString(experiencia)
        parcel.writeString(otrasExperiencias)
        parcel.writeParcelable(dateCreated, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Perfil> {
        override fun createFromParcel(parcel: Parcel): Perfil {
            return Perfil(parcel)
        }

        override fun newArray(size: Int): Array<Perfil?> {
            return arrayOfNulls(size)
        }
    }
}



