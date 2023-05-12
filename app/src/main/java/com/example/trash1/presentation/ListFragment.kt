package com.example.trash1.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.trash1.databinding.ActivityMainBinding
import com.example.trash1.databinding.FragmentListBinding
import com.example.trash1.domain.RecyclerViewUserAdapter
import com.example.trash1.domain.usecase.SearchUseCase
import kotlinx.coroutines.*
import android.app.Activity


class ListFragment : Fragment(), SearchUseCase.UserDataLoader {
    private lateinit var binding: FragmentListBinding

    var searchWords: String = ""
    val case = SearchUseCase(this)
    var userArray: MutableList<String> = mutableListOf<String>()
    val adapter = RecyclerViewUserAdapter(userArray)
    var i: Int = 0
    var y: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonExit.setOnClickListener() {

        }

        binding.editSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchWords = binding.editSearch.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

                CoroutineScope(Dispatchers.Main).launch {
                    val d: Job = launch {
                        delay(600L)
                        case.userRetrofit(searchWords, ListFragment())
                        y = 0
                        i = 0
                    }
                    d.join()
                    d.cancel()
                }
            }
        })
    }

    companion object {

        @JvmStatic
        fun newInstance() = ListFragment()
    }

    override fun dataLoader(userArray: MutableList<String>) {
        y++
        if (y < 2) {
            this.userArray.clear()
            for (i in 0 until userArray.size) {
                this.userArray.add(i, userArray[i])
                println("userArray" + i + "  - " + userArray[i])
            }
        }
        binding.listUserRecycler.layoutManager = GridLayoutManager(context, 1)
        binding.listUserRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun errorData() {
        Toast.makeText(
            this.context, "Waiting data from server. Please wait few seconds.",
            Toast.LENGTH_SHORT
        ).show()
    }
}