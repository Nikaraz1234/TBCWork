package com.example.tbcworks.presentation.screens.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.tbcworks.R
import com.example.tbcworks.databinding.FragmentMapBinding
import com.example.tbcworks.presentation.common.BaseFragment
import com.example.tbcworks.presentation.extension.SnackBarHelper.showSnackBar
import com.example.tbcworks.presentation.extension.collectFlow
import com.example.tbcworks.presentation.extension.collectStateFlow
import com.example.tbcworks.presentation.screens.map.model.LocationClusterItem
import com.example.tbcworks.presentation.screens.map.model.LocationModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.firstOrNull

@AndroidEntryPoint
class MapFragment : BaseFragment<FragmentMapBinding>(FragmentMapBinding::inflate),
    OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModels()

    private var _googleMap: GoogleMap? = null
    private val googleMap get() = _googleMap!!

    private var pendingLocations: List<LocationModel> = emptyList()
    
    private val clusterManager: ClusterManager<LocationClusterItem> by lazy {
        ClusterManager<LocationClusterItem>(requireContext(), googleMap).apply {
            googleMap.setOnCameraIdleListener(this)
            googleMap.setOnMarkerClickListener(this)
        }
    }

    override fun bind() {
        setupMap()
        observeState()
        observeSideEffects()
        viewModel.onEvent(MapEvent.LoadLocations)
    }

    override fun listeners() = with(binding){
        btnZoomIn.setOnClickListener { googleMap.animateCamera(CameraUpdateFactory.zoomIn()) }
        btnZoomOut.setOnClickListener { googleMap.animateCamera(CameraUpdateFactory.zoomOut()) }
    }

    private fun setupMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        _googleMap = map
        enableMyLocation()
        clusterManager // initialize ClusterManager

        if (pendingLocations.isNotEmpty()) {
            updateMarkers(pendingLocations)
            pendingLocations = emptyList()
        }
    }

    private fun observeState() {
        collectStateFlow(viewModel.uiState) { state ->
            if (_googleMap != null) {
                updateMarkers(state.locations)
            } else {
                pendingLocations = state.locations
            }
        }
    }

    private fun observeSideEffects() {
        collectFlow(viewModel.sideEffect) { effect ->
            when(effect) {
                is MapSideEffect.ShowError -> binding.root.showSnackBar(effect.message)
            }
        }
    }

    private fun updateMarkers(locations: List<LocationModel>) {
        googleMap.clear()
        clusterManager.clearItems()

        locations.forEach { loc ->
            clusterManager.addItem(
                LocationClusterItem(
                    lat = loc.latitude,
                    lng = loc.longitude,
                    title = loc.title,
                    snippet = loc.description
                )
            )
        }

        clusterManager.cluster()

        locations.firstOrNull()?.let {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 12f))
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
        }
    }
}




