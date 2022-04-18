package com.tukorea.worldtime

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.tukorea.worldtime.databinding.MainItemBinding

class MainListAdapter (private val context: Context, private val MainList: MutableList<MainListItem>) : BaseAdapter() {
    override fun getCount(): Int = MainList.size


    override fun getItem(position: Int): MainListItem = MainList[position]


    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = MainItemBinding.inflate(LayoutInflater.from(context))

        val show = MainList[position]
        binding.diffTime.text = show.timeDiff
        binding.zoneName.text = show.zoneName
        binding.itemTime.text = show.itemTime

        return binding.root

    }


}