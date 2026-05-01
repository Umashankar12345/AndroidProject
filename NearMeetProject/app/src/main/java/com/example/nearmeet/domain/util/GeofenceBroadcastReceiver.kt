package com.example.nearmeet.domain.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GeofenceBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationHelper: NotificationHelper

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent) ?: return
        if (geofencingEvent.hasError()) return

        val triggeringGeofences = geofencingEvent.triggeringGeofences ?: return
        triggeringGeofences.forEach { geofence ->
            notificationHelper.showEventReminder(
                title = "You are near an event!",
                message = "An event is happening within 500m of you. Tap to see details.",
                notifId = geofence.requestId.hashCode()
            )
        }
    }
}
