package com.example.tbcworks.presentation.screens.map.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class LocationClusterItem(
    private val lat: Double,
    private val lng: Double,
    private val title: String,
    private val snippet: String
) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(lat, lng)
    override fun getTitle(): String = title
    override fun getSnippet(): String = snippet
}