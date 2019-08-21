package com.noodoe.map.library.profile

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.noodoe.map.library.R
import com.noodoe.map.library.common.BaseRenderer
import com.noodoe.map.library.model.MultiDrawable
import java.util.*
import kotlin.math.min

class ProfileMapPageRenderer(
    context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Profile>,
    override var resId: Int = R.layout.multi_profile
) : BaseRenderer<Profile>(context, map, clusterManager) {

    override fun createClusterIconItem(cluster: Cluster<Profile>) {
        val dimension = context.resources.getDimension(R.dimen.image_size).toInt()

        val profileView = layoutInflater.inflate(resId, null)
        clusterIconGenerator.setContentView(profileView)
        val clusterImageView: ImageView = profileView.findViewById(R.id.image)

        // Draw multiple people.
        // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
        val profilePhotos = ArrayList<Drawable>(min(4, cluster.size))

        for ((_, profilePhoto) in cluster.items) {
            // Draw 4 at most.
            if (profilePhotos.size == 4) break
            val drawable = context.resources.getDrawable(profilePhoto, null)
            drawable.setBounds(0, 0, dimension, dimension)
            profilePhotos.add(drawable)
        }

        val multiDrawable = MultiDrawable(profilePhotos)
        multiDrawable.setBounds(0, 0, dimension, dimension)

        clusterImageView.setImageDrawable(multiDrawable)
    }

    override fun createNonClusterIconItem(profile: Profile) {
        val dimension = context.resources.getDimension(R.dimen.image_size).toInt()
        val padding = context.resources.getDimension(R.dimen.padding_size).toInt()

        val imageView = ImageView(context)
        imageView.layoutParams = ViewGroup.LayoutParams(dimension, dimension)
        imageView.setPadding(padding, padding, padding, padding)
        nonClusterIconGenerator.setContentView(imageView)

        imageView.setImageResource(profile.profilePhoto)
    }

    override fun onBeforeClusterItemRendered(profile: Profile, markerOptions: MarkerOptions) {
        createNonClusterIconItem(profile)

        val icon = nonClusterIconGenerator.makeIcon()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(profile.name)
    }

    override fun onBeforeClusterRendered(cluster: Cluster<Profile>, markerOptions: MarkerOptions) {
        createClusterIconItem(cluster)

        val icon = clusterIconGenerator.makeIcon(cluster.size.toString())
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon))
    }

    override fun shouldRenderAsCluster(cluster: Cluster<Profile>?): Boolean {
        // Always render clusters.
        cluster?.let {
            return it.size > 1
        } ?: return false
    }
}