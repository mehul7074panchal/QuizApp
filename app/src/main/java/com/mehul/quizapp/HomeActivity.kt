package com.mehul.quizapp



import Adapters.HomeAdapter
import AppDB.Model.Item
import AppDB.Model.Player
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

  //  private var recyclerView: RecyclerView? = null
     lateinit var arrayList: ArrayList<Item>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
         title = "Hello,"+intent.getParcelableExtra<Player>("player").FirstName

        arrayList = ArrayList()

        arrayList.add(Item("Computer", R.drawable.computer, "#09A9FF"))
        arrayList.add(Item("Science", R.drawable.science, "#3E51B1"))
        arrayList.add(Item("History", R.drawable.history, "#673BB7"))
        arrayList.add(Item("Sports", R.drawable.sport, "#4BAA50"))
        arrayList.add(Item("GeneralKnowledge", R.drawable.gk, "#F94336"))


        val adapter = HomeAdapter(this, arrayList,intent.getParcelableExtra("player"))
        lstSubjects?.adapter = adapter



        val manager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        lstSubjects?.layoutManager = manager

    }
}
