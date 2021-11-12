package com.tiyas.mybroadcastreciever

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tiyas.mybroadcastreciever.databinding.ActivitySmsReceiverBinding

class SmsReceiverActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "exra_sms_message"
    }

    private lateinit var  smsReceiverBinding: ActivitySmsReceiverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        smsReceiverBinding = ActivitySmsReceiverBinding.inflate(layoutInflater)
        setContentView(smsReceiverBinding.root)

        title = getString(R.string.incoming_message)

        smsReceiverBinding.btnClose.setOnClickListener {
            finish()
        }

        val senderNo = intent.getStringExtra(EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(EXTRA_SMS_MESSAGE)

        smsReceiverBinding.tvFrom.text = getString(R.string.from, senderNo)
        smsReceiverBinding.tvMessage.text = senderMessage
    }


}