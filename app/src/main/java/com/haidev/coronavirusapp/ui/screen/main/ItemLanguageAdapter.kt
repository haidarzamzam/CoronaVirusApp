package com.haidev.coronavirusapp.ui.screen.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.haidev.coronavirusapp.R

class ItemLanguageAdapter(val context: Context, var dataSource: List<ItemLanguageModel>) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_language, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.tvNameCountry.text = dataSource[position].name

        val id = context.resources.getIdentifier(
            dataSource[position].img,
            "drawable",
            context.packageName
        )
        vh.ivFlag.setBackgroundResource(id)

        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position]
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ItemHolder(row: View?) {
        val tvNameCountry: TextView = row?.findViewById(R.id.tv_name_country) as TextView
        val ivFlag: ImageView = row?.findViewById(R.id.iv_flag) as ImageView
    }

}