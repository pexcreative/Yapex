package com.example.notificationreader

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class NotificationService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val packageName = sbn.packageName

        // Apps bancarias
        val supportedApps = listOf(
            "com.bcp.innovacxion.yapeapp",
            "com.mercadopago.wallet",
            "pe.com.interbank.mobilebanking",
            "com.bcp.bank.bcp"
        )

        if (packageName in supportedApps) {
            val extras = sbn.notification.extras
            val title = extras.getCharSequence("android.title")?.toString() ?: ""
            val text = extras.getCharSequence("android.text")?.toString()
                ?: extras.getCharSequence("android.bigText")?.toString() ?: ""

            if (text.isNotEmpty()) {
                Log.d("BankNotification", "$packageName: $title - $text")
                sendToAPI(packageName, title, text)
            }
        }
    }

    private fun sendToAPI(app: String, title: String, message: String) {
        Thread {
            try {
                val appEncoded = URLEncoder.encode(app, "UTF-8")
                val titleEncoded = URLEncoder.encode(title, "UTF-8")
                val messageEncoded = URLEncoder.encode(message, "UTF-8")

                val urlString = "http://demo.pexcreative.com/yapex/?app=$appEncoded&title=$titleEncoded&message=$messageEncoded"
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.connectTimeout = 5000

                val responseCode = connection.responseCode
                Log.d("API", "OK $responseCode")

                connection.disconnect()
            } catch (e: Exception) {
                Log.e("API", "Error: ${e.message}")
            }
        }.start()
    }
}