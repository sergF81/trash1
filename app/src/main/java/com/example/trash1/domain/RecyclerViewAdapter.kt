package com.example.trash1.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.trash1.R
import com.example.trash1.data.Item
import com.example.trash1.databinding.ListRowUserBinding

class RecyclerViewUserAdapter(
    private val userArray: MutableList<String>
) : RecyclerView.Adapter<RecyclerViewUserAdapter.UserHolder>() {
    //описание класса UserHolder, котрый описывает наполнения одного элемента RecycleView c заполенной разметкой itemRecycle
    class UserHolder(itemViewUsers: View) : RecyclerView.ViewHolder(itemViewUsers) {
        private val binding = ListRowUserBinding.bind(itemViewUsers)
        fun bind(s: String) = with(binding) {
            textViewUserRow.text = s
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_row_user, parent, false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(userArray[position])

    }

    override fun getItemCount(): Int {
        return userArray.size
    }

}