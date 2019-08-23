package com.noodoe.map.library.profile

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Profile(
    val name: String,
    val profilePhoto: Int,
    private val mPosition: LatLng
) : ClusterItem {

    override fun getSnippet(): String {
        return ""
    }

    override fun getTitle(): String {
        return ""
    }

    override fun getPosition(): LatLng {
        return mPosition
    }
}