package io.github.megasoheilsh.xray.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.megasoheilsh.xray.R
import io.github.megasoheilsh.xray.service.TProxyService
import io.github.megasoheilsh.xray.service.VpnTileService

class VpnActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        val allowed = listOf(
            TProxyService.START_VPN_SERVICE_ACTION_NAME,
            TProxyService.STOP_VPN_SERVICE_ACTION_NAME,
            TProxyService.NEW_CONFIG_ACTION_NAME,
        )
        val action = intent.action ?: ""
        val label = intent.getStringExtra("profile") ?: context.getString(R.string.appName)
        if (!allowed.contains(action)) return
        Intent(context, VpnTileService::class.java).also {
            it.putExtra("action", action)
            it.putExtra("label", label)
            context.startService(it)
        }
    }

}
