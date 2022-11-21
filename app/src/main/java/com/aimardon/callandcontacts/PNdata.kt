package com.aimardon.callandcontacts

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PNdata(
    val name:String,
    val phone:String
) : Parcelable