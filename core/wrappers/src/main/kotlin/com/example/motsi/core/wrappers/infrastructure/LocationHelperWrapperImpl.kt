package com.example.motsi.core.wrappers.infrastructure

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationHelperWrapperImpl @Inject constructor(
    private val context: Context
): LocationHelperWrapper {

    // Проверка разрешений
    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    // Получение текущего местоположения (suspend)
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override suspend fun getCurrentLocationOrNull(): Location? =
        suspendCancellableCoroutine { cont ->
            val client = LocationServices.getFusedLocationProviderClient(context)

            if (!hasLocationPermission()) {
                cont.resume(null)
                return@suspendCancellableCoroutine
            }

            val tokenSource = CancellationTokenSource()
            cont.invokeOnCancellation { tokenSource.cancel() }

            client.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                tokenSource.token
            ).addOnSuccessListener { location ->
                if (location != null) {
                    cont.resume(location)
                } else {
                    // fallback
                    client.lastLocation
                        .addOnSuccessListener { last ->
                            cont.resume(last)
                        }
                        .addOnFailureListener {
                            cont.resume(null)
                        }
                }
            }.addOnFailureListener {
                cont.resume(null)
            }
        }

    // Проверка включена ли геолокация
    override fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                locationManager.isLocationEnabled
            } else {
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            }
        } catch (e: Exception) {
            false
        }
    }
}