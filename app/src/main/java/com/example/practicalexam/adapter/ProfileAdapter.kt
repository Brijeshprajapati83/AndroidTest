package com.example.practicalexam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practicalexam.ProfileActivity
import com.example.practicalexam.R
import com.example.practicalexam.modelclass.UserData

class ProfileAdapter(
    var context: Context,
    private var profileList: ArrayList<UserData>,
    val onItemClickListener: ProfileActivity
) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemprofile, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profileClass = profileList[position]

        Glide
            .with(context)
            .load(profileClass.large_photo_url)
            .placeholder(R.mipmap.ic_launcher_round)
            .into(holder.imageView)

        holder.txUser.text = profileClass.first_name
        holder.txUserpwd.text = profileClass.supplier_id.toString()
        holder.txSurname.text = profileClass.last_name

        holder.itemView.setOnClickListener{
            onItemClickListener.onItemClick(profileClass,position)
        }


    }

    override fun getItemCount(): Int {
        return profileList.size
    }

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView = itemView.findViewById(R.id.ivProfile)
        val txUser: TextView = itemView.findViewById(R.id.tvUsername)
        val txUserpwd: TextView = itemView.findViewById(R.id.tvPassword)
        val txSurname: TextView = itemView.findViewById(R.id.tvTextview)


    }
    interface OnItemClickListener{

        fun onItemClick(userData: UserData , position: Int)
    }

}