package com.justcorrections.grit.data.mapdata.resources

/**
 * Created by ianwillis on 11/19/17.
 */

data class Resource(var id: String = "",
                    val category: String = "",
                    val city: String = "",
                    val lat: Double = 0.0,
                    val lng: Double = 0.0,
                    val name: String = "",
                    val phone: String = "",
                    val state: String = "",
                    val address: String = "",
                    val url: String = "",
                    val zip: Long = 0)
