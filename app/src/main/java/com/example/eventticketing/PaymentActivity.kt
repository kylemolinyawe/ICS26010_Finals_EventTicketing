package com.example.eventticketing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast

class PaymentActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_methods)

//        val zones = intent.getSerializableExtra("zones")
//        val refNums = intent.getSerializableExtra("refNums")
        val zones = intent.getStringArrayListExtra("zones")
        val refNums = intent.getStringArrayListExtra("refNums")
        val userEmail = intent.getStringExtra("email").toString().trim()

        val radioGCash = findViewById<RadioButton>(R.id.radioGCash)
        val radioMaya = findViewById<RadioButton>(R.id.radioMaya)
        val radioMasterCard = findViewById<RadioButton>(R.id.radioMastercard)
        val radioGrabPay = findViewById<RadioButton>(R.id.radioGrabPay)
        var paymentMethod:String = ""

        radioGCash.setOnClickListener() {
            paymentMethod = onRadioClick(radioGCash)
        }
        radioMaya.setOnClickListener() {
            paymentMethod = onRadioClick(radioMaya)
        }
        radioMasterCard.setOnClickListener() {
            paymentMethod = onRadioClick(radioMasterCard)
        }
        radioGrabPay.setOnClickListener() {
            paymentMethod = onRadioClick(radioGrabPay)
        }

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener() {
            val intentFinalizeTicket = Intent(this, FinalizeTicketActivity::class.java)
            intentFinalizeTicket.putExtra("email", userEmail)
            startActivity(intentFinalizeTicket)
        }

        val btnPurchase = findViewById<Button>(R.id.btnBuyTicket)
        btnPurchase.setOnClickListener() {
            // make an array of ticket objects from TicketModelClass
            val ticketList = ArrayList<TicketModelClass>()
            var ctr = 0
            if (refNums != null) {
                Log.i("nullcheck", "refnums not null")
                if (zones != null) {
                    Log.i("nullcheck", "zones not null")
                    for (ref in refNums) {
                        ticketList.add(TicketModelClass(ticketRefNum = ref.toString(),
                                                        ticketZone = zones[ctr].toString(),
                                                        userEmail = userEmail,
                                                        paymentMethod = paymentMethod))
                        ctr++
                    }
                }
            } // end of this part

            for (ticket in ticketList) {
                val dbHandler: DatabaseHandler = DatabaseHandler(this)
                val status = dbHandler.addTicket(ticket)
                if (status > -1) {
                    Log.i("Ticket Saved", "Ticket ${ticket.ticketRefNum} for ${ticket.userEmail} saved to table")
                } else {
                    Toast.makeText(this, "Something went wrong...", Toast.LENGTH_LONG).show()
                }

                val userEmail = intent.getStringExtra("email").toString().trim()
                val intentHome = Intent(this, HomeActivity::class.java)
                intentHome.putExtra("email", userEmail)
                startActivity(intentHome)
            }
        }

    }

    private fun onRadioClick(selectedRadioButton: RadioButton): String {
        val radioGCash = findViewById<RadioButton>(R.id.radioGCash)
        val radioMaya = findViewById<RadioButton>(R.id.radioMaya)
        val radioMasterCard = findViewById<RadioButton>(R.id.radioMastercard)
        val radioGrabPay = findViewById<RadioButton>(R.id.radioGrabPay)

        when (selectedRadioButton) {
            radioGCash -> {
                radioMaya.isChecked = false
                radioMasterCard.isChecked = false
                radioGrabPay.isChecked = false
                return "GCash"
            }
            radioMaya -> {
                radioGCash.isChecked = false
                radioMasterCard.isChecked = false
                radioGrabPay.isChecked = false
                return "PayMaya"
            }
            radioMasterCard -> {
                radioMaya.isChecked = false
                radioGCash.isChecked = false
                radioGrabPay.isChecked = false
                return "MasterCard"
            }
            radioGrabPay -> {
                radioMaya.isChecked = false
                radioMasterCard.isChecked = false
                radioGCash.isChecked = false
                return "GrabPay"
            }
        }
        return "" // unreachable empty return
    }
}