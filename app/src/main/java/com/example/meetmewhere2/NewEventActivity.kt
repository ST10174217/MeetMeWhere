package com.example.meetmewhere2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.meetmewhere2.databinding.ActivityNewEventBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewEventActivity : AppCompatActivity() {
    //Global variable declaration
    lateinit var binding: ActivityNewEventBinding
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_event)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Initialize binding view
        binding = ActivityNewEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Instantiate database instance
        db = AppDatabase.getDatabase(this) as AppDatabase

        //Save event details
        binding.btnSave.setOnClickListener(){
            //collect user input
            val title = binding.edtTitle.text
            val desc = binding.edtDesc.text
            val date = binding.edtDate.text
            val time = binding.edtTime.text
            val location = binding.edtLocation.text

            //Validation
            if (title.isEmpty()){
                binding.edtTitle.error = "Title is required"
                return@setOnClickListener
            }
            if (desc.isEmpty()){
                binding.edtDesc.error = "Description is required"
                return@setOnClickListener
            }
            if (date.isEmpty()){
                binding.edtDate.error = "Date is required"
                return@setOnClickListener
            }
            if (time.isEmpty()){
                binding.edtTime.error = "Time is required"
                return@setOnClickListener
            }
            if (location.isEmpty()){
                binding.edtLocation.error = "Location is required"
                return@setOnClickListener
            }
            //Instantiate new event object to hold details
            val event = EventEntity(title = title.toString(),
                description = desc.toString(),
                date = date.toString(),
                time = time.toString(),
                location = location.toString()
                )
            //Insert object into database
            CoroutineScope(Dispatchers.IO).launch {
                db.eventDAO().insertEvent(event)
            }
            //Notify user
            Toast.makeText(this, "Event Added!", Toast.LENGTH_SHORT).show()
            //Take user back to the event screen
            startActivity(Intent(Intent(this, EventsActivity::class.java)))
            finish()
        }
    }
}