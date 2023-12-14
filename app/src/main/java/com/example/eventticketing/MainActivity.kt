package com.example.eventticketing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callLogin()

    }

    fun callLogin(){
        setContentView(R.layout.login)

        val btnRegister = findViewById<TextView>(R.id.btnRegister)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener(){
            callHome()
        }

        btnRegister.setOnClickListener(){
            callRegister()
        }
    }

    fun callRegister(){

    }

    fun callHome(){
        setContentView(R.layout.home)
        val btnTickets1 = findViewById<Button>(R.id.btnTickets1)

        btnTickets1.setOnClickListener(){
            callBuyTickets()
        }

    }

    fun callBuyTickets(){
        setContentView(R.layout.buy_tickets)

        val btnIncrement = findViewById<ImageView>(R.id.ticketIncrement1)
        val btnDecrement = findViewById<ImageView>(R.id.ticketDecrement1)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        btnNext.setOnClickListener(){
            callFinalizeTickets()
        }

        btnBack.setOnClickListener(){
            callHome()
        }

    }
    fun callFinalizeTickets(){
        setContentView(R.layout.finalize_tickets)

        val zones = resources.getStringArray(R.array.Zones)
        val ticket1SeatChoice = findViewById<Spinner>(R.id.ticket1SeatChoice)
        val ticket2SeatChoice = findViewById<Spinner>(R.id.ticket2SeatChoice)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnBack = findViewById<ImageView>(R.id.btnBack)

        setSpinnerItems(ticket1SeatChoice, zones)
        setSpinnerItems(ticket2SeatChoice, zones)

        btnConfirm.setOnClickListener(){
            callPaymentMethods()
        }

        btnBack.setOnClickListener(){
            callBuyTickets()
        }

    }
    
    fun setSpinnerItems(spinner: Spinner, zones: Array<String>){
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, zones)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                Toast.makeText(this@MainActivity,
//                    getString(R.string.selected_item) + " " +
//                            "" + zones[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        
    }

    fun callPaymentMethods(){
        setContentView(R.layout.payment_methods)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener(){
            callMyTickets()
        }
        btnBack.setOnClickListener(){
            callFinalizeTickets()
        }
    }

    fun callMyTickets(){
        setContentView(R.layout.my_tickets)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener(){
            callHome()
        }
    }

}