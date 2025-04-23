package com.example.meetmewhere2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.meetmewhere2.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Instantiating binding view
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Instantiate database instance
        db = AppDatabase.getDatabase(this) as AppDatabase

        //Send user to login screen
        binding.btnToLogin.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        //When user clicks register button
        binding.btnRegister.setOnClickListener()
        {
            //Collecting input
            val name = binding.edtName.text
            val password = binding.edtPassword.text
            val password2 = binding.edtPasswordConfirm.text

            //Validation
            if (name.isEmpty())
            {
                binding.edtName.error = "Username is required!"
                return@setOnClickListener
            }
            //Validation user availability
            if (password.isEmpty())
            {
                binding.edtPassword.error = "Password is required!"
                return@setOnClickListener
            }
            if (password2.isEmpty())
            {
                binding.edtPasswordConfirm.error = "Password is required!"
                return@setOnClickListener
            }
            if(password.toString() != password2.toString())
            {
                binding.edtPasswordConfirm.error = "Passwords do not match!"
                return@setOnClickListener
            }
            // TODO:  Check if username already exists

            //Method to insert user
            addUser(name.toString(), password.toString())
            //Send user to Login screen
            startActivity(Intent(Intent(this, MainActivity::class.java)))
            finish()
        }//End of btnClick
    }
    private fun addUser(username: String, key : String)
    {
        //Save user into database
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserEntity(userName = username, password = key)
            db.userDAO().insert(user)
        }
        //Notify user
        Toast.makeText(this@RegisterActivity, "Account Saved!", Toast.LENGTH_SHORT).show()
    }
}