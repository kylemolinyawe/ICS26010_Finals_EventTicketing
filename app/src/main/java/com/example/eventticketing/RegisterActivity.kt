package com.example.eventticketing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val btnMoveLogin = findViewById<TextView>(R.id.btnMoveLogin)
        btnMoveLogin.setOnClickListener() {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        }

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener() {
            addUser()
        }
    }

    fun addUser() {
        // inputs
        val etEmail = findViewById<EditText>(R.id.etRegEmail)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)

        val input_email = etEmail.text.toString().trim()
        val input_password = etPassword.text.toString().trim()

        // check blank fields
        if (input_email=="" || input_password=="") {
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_LONG).show()
            return
        }

        // check email format
        val patternEmail = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
        val emailMatcher = patternEmail.matcher(input_email)
        if (!emailMatcher.matches()) {
            Toast.makeText(this, "Please input a VALID EMAIL!", Toast.LENGTH_LONG).show()
            return
        }

        val dbHandler: DatabaseHandler = DatabaseHandler(this)

        // check if email exists
        val userExists = dbHandler.fetchUser(input_email)
        if (userExists != null) {
            Toast.makeText(this, "Account already exists!", Toast.LENGTH_LONG).show()
            return
        }

        // all requirements met
        val status = dbHandler.addUser(UserModelClass(input_email, input_password))
        if (status > -1) {
            Toast.makeText(this, "Account registered successfully!", Toast.LENGTH_LONG).show()
            etEmail.text.clear()
            etPassword.text.clear()

            // bring to login
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)
        } else {
            Toast.makeText(this, "Something went wrong...", Toast.LENGTH_LONG).show()
        }

    }
}