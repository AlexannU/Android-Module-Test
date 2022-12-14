package ru.alexannu.modulestest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.provider.Telephony.Sms
import android.telephony.SmsMessage

class MyReceiver : BroadcastReceiver() {
    val SMS = "android.provider.Telephony.SMS_RECEIVED"
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SMS) {
            val msgs = Sms.Intents.getMessagesFromIntent(intent)
            for (msg in msgs) {
                val senderNo = msg.displayOriginatingAddress
                val message = msg.displayMessageBody
                println("SenderNum $senderNo, messageBody $message")
            }
        }
    }

}