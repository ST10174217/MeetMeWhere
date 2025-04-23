package com.example.meetmewhere2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetmewhere2.databinding.ActivityEventsBinding
import com.example.meetmewhere2.databinding.ActivityNewEventBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsActivity : AppCompatActivity() {

    lateinit var binding: ActivityEventsBinding
    private lateinit var db : AppDatabase
    private lateinit var dataList: ArrayList<EventsDataClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_events)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Receive data from login class
        val namelbl = intent.getStringExtra("USERNAME")

        //Initialize binding view
        binding = ActivityEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Instantiate database
        db = AppDatabase.getDatabase(this) as AppDatabase

        //Initialize RecyclerView attributes
        binding.recyclerPage.layoutManager = LinearLayoutManager(this)
        binding.recyclerPage.setHasFixedSize(true)

        //Initialize dataList
        dataList = arrayListOf<EventsDataClass>()

        //Send user to event creation screen
        binding.btnNewEvent.setOnClickListener(){
            val intent = Intent(this, NewEventActivity::class.java)
            startActivity(intent)
        }

        /*
        Thread for safe database access
        Retrieving event objects from db and converting them to data compatible with recyclerView adapter
        Add the new objects into the arraylist
        Finally initialize the events adapter and recycler view
         */
        CoroutineScope(Dispatchers.IO).launch {
            for (event in db.eventDAO().getAllEvents())
            {
                val eventData = EventsDataClass(event.title, event.description) //Retrieve event details into eventDataClass
                dataList.add(eventData) //Add object to list for Recycler View adapter
            }
            runOnUiThread()
            {
                try {//Initialize RecyclerView adapter
                    binding.recyclerPage.adapter = EventsAdapter(dataList)
                } catch (e: Exception) {
                    Toast.makeText(this@EventsActivity, e.message  , Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}