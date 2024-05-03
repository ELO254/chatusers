package com.example.chatusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val messageList:ArrayList<message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var itemrecieve = 1
    var itemsent = 2
    class SentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val sentmessage = itemView.findViewById<TextView>(R.id.txtsent)
    }

    class RecieveViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val recievemessage = itemView.findViewById<TextView>(R.id.txtrecieve)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1){
            var view = LayoutInflater.from(parent.context).inflate(R.layout.recieve,parent,false)
            return RecieveViewHolder(view)
        }else{
            var view = LayoutInflater.from(parent.context).inflate(R.layout.send,parent,false)
            return SentViewHolder(view)

        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            holder.sentmessage.text = messageList[position].message
        }else{

            val viewHolder = holder as RecieveViewHolder
            holder.recievemessage.text = messageList[position].message

        }
    }

    override fun getItemViewType(position: Int): Int {
        var currentmessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderId)){
            return itemsent
        }else{
            return itemrecieve
        }
    }
}