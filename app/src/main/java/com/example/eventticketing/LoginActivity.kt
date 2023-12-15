package com.example.eventticketing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val btnMoveRegister = findViewById<TextView>(R.id.btnMoveRegister)
        btnMoveRegister.setOnClickListener() {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }


        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPass = findViewById<EditText>(R.id.etPassword)

        btnLogin.setOnClickListener() {
            // inputs
            val email = etEmail.text.toString().trim()
            val pass = etPass.text.toString().trim()

            verifyLogin(email, pass)
        }
    }

    fun verifyLogin(input_email:String, input_password:String) {
        val dbHandler: DatabaseHandler = DatabaseHandler(this)
        val user = dbHandler.fetchUser(input_email)

        // account not found
        if (user == null) {
            Toast.makeText(this, "Account not found!", Toast.LENGTH_LONG).show()
            return
        }

        // password check
        if (user.userPassword != input_password) {
            Toast.makeText(this, "Incorrect Password!", Toast.LENGTH_LONG).show()
            return
        }

        // user found and password matches
        val intentHome = Intent(this, HomeActivity::class.java)
        intentHome.putExtra("email", input_email)
        // REMINDER: intentHome.getStringExtra("email").toString().trim()
        startActivity(intentHome)
    }
}