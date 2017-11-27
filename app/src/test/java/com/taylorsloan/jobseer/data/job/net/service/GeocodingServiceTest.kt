package com.taylorsloan.jobseer.data.job.net.service

import com.taylorsloan.jobseer.data.TestDataModuleImpl
import com.taylorsloan.jobseer.data.coordinate.net.service.GeocodingService
import org.junit.Assert
import org.junit.Test
import javax.inject.Inject

/**
 * Created by taylorsloan on 10/28/17.
 */
class GeocodingServiceTest {

    @Inject
    lateinit var geocodingService : GeocodingService

    init {
        TestDataModuleImpl.netComponent().inject(this)
    }

    @Test
    fun testGetCoordinatesFromAddress() {
        val test = geocodingService.getCoordinatesFromAddress(address = "LosAngeles",
                apiKey = "AIzaSyBp_sX6OLut-EP5ji995TBeaaDFl54JrPE").test()
        test.assertNoErrors()
        val lat = test.values()[0].results[0].geometry.location.lat
        val lng = test.values()[0].results[0].geometry.location.lng
        System.out.println("Los Angeles Coordinates: $lat, $lng")
        Assert.assertNotEquals(0.0, lat)
        Assert.assertNotEquals(0.0, lng)
    }
}