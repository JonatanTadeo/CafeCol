package com.example.appcafecol

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class Finca(
    val finca: String = "",
    val charge: String = "",
    val availability: String = "",
    val location: String = "",
    val salary: String = "",
    val work: String = "",
    val experience: String = "",
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
        parcel.writeString(finca)
        parcel.writeString(charge)
        parcel.writeString(availability)
        parcel.writeString(location)
        parcel.writeString(salary)
        parcel.writeString(work)
        parcel.writeString(experience)
        parcel.writeParcelable(dateCreated, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Finca> {
        override fun createFromParcel(parcel: Parcel): Finca {
            return Finca(parcel)
        }

        override fun newArray(size: Int): Array<Finca?> {
            return arrayOfNulls(size)
        }
    }
}




