package com.example.planmanagement

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PlanAdapter(activity: Activity, val resourceId: Int, data: List<Plan>) : ArrayAdapter<Plan>(activity, resourceId, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, parent, false)
            val fruitName: TextView = view.findViewById(R.id.planName)
            viewHolder = ViewHolder(fruitName)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val plan = getItem(position) // 获取当前项的Plan实例
        if (plan != null) {
            viewHolder.planName.text = plan.name+"\n"+plan.content
        }
        return view
    }

    inner class ViewHolder(val planName: TextView)

}