package com.haidev.coronavirusapp.ui.screen.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.haidev.coronavirusapp.R
import com.haidev.coronavirusapp.data.model.CoronaStatisticModel

class ItemCountryAdapter(
    val context: Context,
    var dataSource: List<CoronaStatisticModel.Response.Country>
) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_country, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.tvNameCountry.text = dataSource[position].Country

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