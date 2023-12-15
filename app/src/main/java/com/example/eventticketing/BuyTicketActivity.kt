package com.example.eventticketing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class BuyTicketActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buy_tickets)

        val tvTicketNumber = findViewById<TextView>(R.id.tvTickNumber)
        val tvTicketSubtotal = findViewById<TextView>(R.id.tvTicketSubtotal)
        val tvTicketNum = findViewById<TextView>(R.id.ticketNum)
        val pricePerTicket = 1660 // get this from a textview

        // increment - decrement buttons
        val decrement = findViewById<ImageView>(R.id.ticketDecrement1)
        val increment = findViewById<ImageView>(R.id.ticketIncrement1)

        decrement.setOnClickListener() {
            var current = tvTicketNumber.text.toString().toInt()
            if (current > 1) {
                tvTicketNumber.text = (current - 1).toString()
                tvTicketNum.text = (current - 1).toString() + " Tickets"
                tvTicketSubtotal.text = "PHP " + ((current - 1)*pricePerTicket).toString()
            }
        }

        increment.setOnClickListener() {
            var current = tvTicketNumber.text.toString().toInt()
            tvTicketNumber.text = (current + 1).toString()
            tvTicketNum.text = (current + 1).toString() + " Tickets"
            tvTicketSubtotal.text = "PHP " + ((current + 1)*pricePerTicket).toString()
        }

        // back and next buttons
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener() {
            val userEmail = intent.getStringExtra("email").toString().trim()
            val intentHome = Intent(this, HomeActivity::class.java)
            intentHome.putExtra("email", userEmail)
            startActivity(intentHome)
        }

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener() {
            val userEmail = intent.getStringExtra("email").toString().trim()
            Log.i("email", userEmail)
            val intentFinalizeTicket = Intent(this, FinalizeTicketActivity::class.java)
            intentFinalizeTicket.putExtra("email", userEmail)
            intentFinalizeTicket.putExtra("ticketNum", tvTicketNumber.text.toString().trim())
            startActivity(intentFinalizeTicket)
        }
    }
}