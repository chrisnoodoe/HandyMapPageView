package com.noodoe.map.library.common

import android.content.Context
import android.view.LayoutInflater
import com.google.android.gms.maps.GoogleMap
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator

abstract class BaseRenderer<T : ClusterItem>(
    val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<T>
) : DefaultClusterRenderer<T>(context, map, clusterManager) {

    abstract var resId: Int
    var clusterIconGenerator: IconGenerator = IconGenerator(context)
    var nonClusterIconGenerator: IconGenerator = IconGenerator(context)
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    abstract fun createClusterIconItem(cluster: Cluster<T>)

    abstract fun createNonClusterIconItem(item: T)
}