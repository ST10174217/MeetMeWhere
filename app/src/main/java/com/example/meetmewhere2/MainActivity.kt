package com.example.meetmewhere2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.meetmewhere2.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Initializing database
        db = AppDatabase.getDatabase(this) as AppDatabase

        //Instantiation of binding view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener()
        {
            //User values
            val enteredName = binding.edtUserName.text
            val enteredKey = binding.edtKey.text

            //Validation
            if (enteredName.isEmpty())
            {
                binding.edtUserName.error = "Username is required"
                return@setOnClickListener
            }
            if (enteredKey.isEmpty())
            {
                binding.edtKey.error = "Password is required"
                return@setOnClickListener
            }
            //Matching credentials -> validate credentials from database and move the user to the events activity
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val newUser = db.userDAO().getAllUsers().find { it.userName == enteredName.toString() }//Serves as username verification
                    runOnUiThread()
                    {
                        if (newUser == null)
                        {
                            Toast.makeText(this@MainActivity, "User does not exist!", Toast.LENGTH_SHORT).show()
                        }
                        else if (!newUser.password.toString().equals(enteredKey.toString())){
                            Toast.makeText(this@MainActivity, "Incorrect Credentials!", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            Toast.makeText(this@MainActivity, "Welcome ${newUser.userName.toString()}", Toast.LENGTH_SHORT).show()
                            //Move user to event screen
                            val intent = Intent(Intent(this@MainActivity, EventsActivity::class.java))
                            intent.putExtra("USERNAME", newUser.userName.toString())
                            startActivity(intent)
                        }
                    }
                }
                catch (e : Exception)
                {
                    runOnUiThread()
                    {
                        Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        //Send user to register screen
        binding.btnToRegister.setOnClickListener()
        {
            //Clear fields
            binding.edtUserName.text = null
            binding.edtKey.text = null

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}