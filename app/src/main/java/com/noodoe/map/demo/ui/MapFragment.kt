package com.noodoe.map.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.noodoe.map.demo.R
import com.noodoe.map.demo.databinding.FragmentMapBinding
import com.noodoe.map.demo.model.EventObserver
import com.noodoe.map.library.profile.ProfileMapPageRenderer
import com.noodoe.map.library.profile.Profile

class MapFragment : Fragment() {

    private lateinit var viewModel: MapViewModel
    private lateinit var mapView: MapView
    private lateinit var clusterManager: ClusterManager<Profile>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this@MapFragment).get(MapViewModel::class.java)

        val binding = FragmentMapBinding.inflate(inflater, container, false).apply {
            viewModel = this@MapFragment.viewModel
            lifecycleOwner = this@MapFragment
            mapView = map

            mapView.onCreate(savedInstanceState)

            mapView.getMapAsync { map ->
                viewModel?.onMapReady(map)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            mapCenterEvent.observe(this@MapFragment, EventObserver {
                if (it) {
                    googleMap?.let { map ->
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    51.503186,
                                    -0.126446
                                ), 9.5f
                            )
                        )
                        clusterManager = ClusterManager(this@MapFragment.context, map)
                        clusterManager.renderer =
                            ProfileMapPageRenderer(
                                this@MapFragment.context!!,
                                map,
                                clusterManager
                            )

                        map.setOnCameraIdleListener(clusterManager)
                        map.setOnMarkerClickListener(clusterManager)
                        map.setOnInfoWindowClickListener(clusterManager)

                        addItems()
                        clusterManager.cluster()
                    }
                }
            })
        }
    }

    private fun addItems() {
        // http://www.flickr.com/photos/sdasmarchives/5036248203/
        clusterManager.addItem(
            Profile(
                "Walter",
                R.drawable.walter,
                viewModel.position()
            )
        )

        // http://www.flickr.com/photos/usnationalarchives/4726917149/
        clusterManager.addItem(
            Profile(
                "Gran",
                R.drawable.gran,
                viewModel.position()
            )
        )

        // http://www.flickr.com/photos/nypl/3111525394/
        clusterManager.addItem(
            Profile(
                "Ruth",
                R.drawable.ruth,
                viewModel.position()
            )
        )

        // http://www.flickr.com/photos/smithsonian/2887433330/
        clusterManager.addItem(
            Profile(
                "Stefan",
                R.drawable.stefan,
                viewModel.position()
            )
        )

        // http://www.flickr.com/photos/library_of_congress/2179915182/
        clusterManager.addItem(
            Profile(
                "Mechanic",
                R.drawable.mechanic,
                viewModel.position()
            )
        )

        // http://www.flickr.com/photos/nationalmediamuseum/7893552556/
        clusterManager.addItem(
            Profile(
                "Yeats",
                R.drawable.yeats,
                viewModel.position()
            )
        )

        // http://www.flickr.com/photos/sdasmarchives/5036231225/
        clusterManager.addItem(
            Profile(
                "John",
                R.drawable.john,
                viewModel.position()
            )
        )

        // http://www.flickr.com/photos/anmm_thecommons/7694202096/
        clusterManager.addItem(
            Profile(
                "Trevor the Turtle",
                R.drawable.turtle,
                viewModel.position()
            )
        )

        // http://www.flickr.com/photos/usnationalarchives/4726892651/
        clusterManager.addItem(
            Profile(
                "Teach",
                R.drawable.teacher,
                viewModel.position()
            )
        )
    }

    override fun onResume() {
        super.onResume()

        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()

        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()

        mapView.onStop()
    }

    override fun onPause() {
        super.onPause()

        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()

        mapView.onLowMemory()
    }

    companion object {
        @JvmStatic
        fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}
