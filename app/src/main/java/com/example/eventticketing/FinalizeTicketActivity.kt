package com.example.eventticketing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class FinalizeTicketActivity : AppCompatActivity() {

    private val selectedZonesDictionary = mutableMapOf<Int, String>()
    private val refNumbersList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.finalize_tickets)

        val zonesArray: Array<String> = resources.getStringArray(R.array.zones_array)

        // make the dynamically added ticket stuff
        var counter = 1
        val totalTicketNum = intent.getStringExtra("ticketNum").toString().toInt()
        while (counter <= totalTicketNum) {
            addTicket(counter, zonesArray)
            counter++
        }

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener() {
            val userEmail = intent.getStringExtra("email").toString().trim()
            val intentBuyTicket = Intent(this, BuyTicketActivity::class.java)
            intentBuyTicket.putExtra("email", userEmail)
            startActivity(intentBuyTicket)
        }


        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        btnConfirm.setOnClickListener() {
            val userEmail = intent.getStringExtra("email").toString().trim()
            Log.i("email", userEmail)

            val zones = ArrayList<String>(selectedZonesDictionary.values)
            val intentPayment = Intent(this, PaymentActivity::class.java)


            intentPayment.putStringArrayListExtra("zones", zones)
            intentPayment.putStringArrayListExtra("refNums", refNumbersList)
//            intentPayment.putStringArrayListExtra("refNums", refNumbersList)
//            intentPayment.putStringArrayListExtra("zones", zones)
            intentPayment.putExtra("email", userEmail)
            startActivity(intentPayment)
        }
    }

    private fun addTicket(counter: Int, zones: Array<String>) {
        val lytTicketsContainer = findViewById<LinearLayout>(R.id.lytTicketsContainer)

        val inflater = LayoutInflater.from(this)
        val ticket = inflater.inflate(R.layout.ticket_object, null, false) as LinearLayout

        // change ticket number
        val tickNum = ticket.findViewById<TextView>(R.id.tvFinalizeTicketNumber)
        tickNum.text = "Ticket $counter"

        // make random refnum
        val refNum = ticket.findViewById<TextView>(R.id.ticket1RefNum)
        refNum.text = generateRefNum().toString()
        refNumbersList.add(refNum.text.toString())

        lytTicketsContainer.addView(ticket)


        val spinnerZone = ticket.findViewById<Spinner>(R.id.ticketZoneChoice)
        spinnerZone.id = View.generateViewId()
        setSpinnerItems(spinnerZone, zones)
    }

    private fun generateRefNum():Int {
        // generates random 7 digit number
        val random = Random.Default
        return (1000000..9999999).random(random)
    }

    fun setSpinnerItems(spinner: Spinner, zones: Array<String>){
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, zones)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                val selectedItem = spinner.selectedItem.toString()
                val spinnerID = spinner.id
                selectedZonesDictionary[spinnerID] = selectedItem
                Toast.makeText(applicationContext, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
                for ((key, value) in selectedZonesDictionary) {
                    Log.i("Dictionary Entry", "Key: $key, Value: $value")
                }
                for (ref in refNumbersList) {
                    Log.i("Reference Entry", "$ref")
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing here
            }
        }

    }
}