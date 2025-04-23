package com.example.meetmewhere2

import android.provider.CalendarContract.EventsEntity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventsAdapter(private val dataList: ArrayList<EventsDataClass>) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {
    class EventViewHolder(eventView: View): RecyclerView.ViewHolder(eventView){
        //Assign id to Layout widget
        val rvEventTitle: TextView = eventView.findViewById(R.id.txtTitle)
        val rvEventDesc: TextView = eventView.findViewById(R.id.txtDesc)
        val rvBtnEdit: Button = eventView.findViewById(R.id.imgBtnEdit)
        val rvBtnDelete: Button = eventView.findViewById(R.id.imgBtnDelete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        //Basically create or instantiates each view item using layout
        val eventView = LayoutInflater.from(parent.context).inflate(R.layout.event_item_layout,parent, false)
        return EventViewHolder(eventView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        //Set data and event handling
        val currentEvent = dataList[position]
        holder.rvEventTitle.text = currentEvent.dataTitle
        holder.rvEventDesc.text = currentEvent.dataDesc

        //Edit event handling
        holder.rvBtnEdit.setOnClickListener()
        {
            // TODO: Handle the edit event
        }

        //Delete event handling
        holder.rvBtnDelete.setOnClickListener()
        {
            // TODO: Handle the delete event
        }


//        //Sets data to a view holder item and handles events
//        val currentItem = dataList[position]
//        holder.rvImage.setImageResource(currentItem.dataImage)
//        holder.rvTitle.text = currentItem.dataTitle
//        holder.rvEditBtn.setOnClickListener()
//        {
//            //Handle the edit event
//
//        }
//        holder.rvDeleteBtn.setOnClickListener()
//        {
//            dataList.removeAt(position)
//            notifyItemRemoved(position)
//            notifyItemRangeChanged(position,dataList.size)
//        }

    }
}