package com.example.eventticketing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MyTicketsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_tickets)
        val userEmail = intent.getStringExtra("email").toString().trim()

        // check database for all tickets belonging to this email
        val refList = viewTickets(userEmail)
        if (refList != null) {
            for (ref in refList) {
                addTicketView(ref)
            }
        }


        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener() {
            val intentHome = Intent(this, HomeActivity::class.java)
            intentHome.putExtra("email", userEmail)
            startActivity(intentHome)
        }
    }

    private fun viewTickets(input_email: String): ArrayList<String>? {
        val dbHandler: DatabaseHandler = DatabaseHandler(this)
        return dbHandler.fetchTicket(input_email)
    }

    private fun addTicketView(refNum: String) {
        val lytMyTicketsContainer = findViewById<LinearLayout>(R.id.lytMyTicketsContainer)

        val inflater = LayoutInflater.from(this)
        val ticket = inflater.inflate(R.layout.my_ticket_object, null, false) as LinearLayout

        // change reference number
        val tvRefNum = ticket.findViewById<TextView>(R.id.tvMyRefNumber)
        tvRefNum.text = refNum.toString().trim()

        lytMyTicketsContainer.addView(ticket)
    }
}