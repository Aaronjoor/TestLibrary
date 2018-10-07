package com.wangxiangle.testlibrary

import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * @author wangxiangle
 * @date  06/10/2018 18:43
 * @email 1063257495@qq.com
 **/

class MyAdapter(list: List<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    var mDatas: List<String> = ArrayList()

    init {
        this.mDatas = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_adapter, parent, false))
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.setData(mDatas.get(position))
    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTextView: TextView? = null

        init {
            mTextView =view.findViewById(R.id.textView)
        }

        public fun setData(text:String): MyViewHolder {
            mTextView?.setText(text)
            mTextView?.layoutParams?.height = text.toInt()
            return this
        }

    }

}