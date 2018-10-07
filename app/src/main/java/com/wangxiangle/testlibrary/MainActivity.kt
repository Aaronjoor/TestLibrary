package com.wangxiangle.testlibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.wangxiangle.mylibrary.GridItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycle_View.layoutManager = StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL)
        var mData = ArrayList<String>()
        for(item in 1..100){
            mData.add((Math.random() * 500).toInt().toString())
        }

        recycle_View.addItemDecoration(GridItemDecoration.Builder(applicationContext)
                .size(36)
                .setDrawFirstTopLine(true)
                .build())
        recycle_View.adapter = MyAdapter(mData)
    }
}
