package com.serex.appstorehb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        this.findViewById<Button>(R.id.btn_login).setOnClickListener {

            val username = this.findViewById<TextInputLayout>(R.id.user)
            val password = this.findViewById<TextInputLayout>(R.id.password)

            val usernameHardCode = "hbullon"
            val passwordHardCode = "123456"

            val validatedUsername = username.editText?.text.toString() == usernameHardCode

            if(!validatedUsername){
                username.error = "Wrong, invalidated username or password"
            }else{
                username.error = null
            }

            val validatedPassword = password.editText?.text.toString() == passwordHardCode

            if(!validatedPassword){
                password.error = "Wrong, invalidated username or password"
            }else{
                password.error = null
            }

            if(validatedUsername && validatedPassword){
                val sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE)
                sharedPreferences.edit().putBoolean( getString(R.string.logged_user_key), true).apply()
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(this, "Please, check your credentials", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {

        if(keyCode.equals(4)){
            return false
        }
        return super.onKeyUp(keyCode, event)
    }
}