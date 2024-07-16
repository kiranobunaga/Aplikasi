package com.example.aplikasi

import android.os.Parcel
import android.os.Parcelable

data class Dosen(
    val id: String? = null,  // Firestore document ID
    val nama: String? = null,
    val nip: String? = null,
    val matkul: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nama)
        parcel.writeString(nip)
        parcel.writeString(matkul)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dosen> {
        override fun createFromParcel(parcel: Parcel): Dosen {
            return Dosen(parcel)
        }

        override fun newArray(size: Int): Array<Dosen?> {
            return arrayOfNulls(size)
        }
    }
}
