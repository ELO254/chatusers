package com.example.chatusers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class userrAdapter(var context: Context,var data:List<Any>):RecyclerView.Adapter<userrAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {

        var text:TextView = view.findViewById(R.id.txtuser)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.userlayout,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = data[position].toString()

        holder.itemView.setOnClickListener {
            var intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("name", data[position].toString())
            intent.putExtra("uid", FirebaseAuth.getInstance().currentUser?.uid)
            context.startActivity(intent)
        }
    }
}