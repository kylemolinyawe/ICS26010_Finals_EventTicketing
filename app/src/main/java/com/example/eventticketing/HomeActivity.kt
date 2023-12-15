package com.example.eventticketing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class HomeActivity :  AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val intent = intent

        val btnMyTickets = findViewById<Button>(R.id.btnMyTickets)
        btnMyTickets.setOnClickListener() {
            val userEmail = intent.getStringExtra("email").toString().trim()
            val intentMyTickets = Intent(this, MyTicketsActivity::class.java)
            intentMyTickets.putExtra("email", userEmail)
            startActivity(intentMyTickets)
        }

        val btnTickets = findViewById<Button>(R.id.btnTickets)
        btnTickets.setOnClickListener() {
            val userEmail = intent.getStringExtra("email").toString().trim()
            val intentBuyTickets = Intent(this, BuyTicketActivity::class.java)
            intentBuyTickets.putExtra("email", userEmail)
            startActivity(intentBuyTickets)
        }


    }
}