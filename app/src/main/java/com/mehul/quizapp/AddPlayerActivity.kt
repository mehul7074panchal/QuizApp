package com.mehul.quizapp

import Adapters.PlayerAdapter
import AppDB.Controller.PlayerLogic
import AppDB.Model.Player
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_add_player.*

class AddPlayerActivity : AppCompatActivity() {

    var adapter:PlayerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player)


        val lstplayers = PlayerLogic(Realm.getDefaultInstance()).getPlayers().toMutableList()
        adapter = PlayerAdapter(this,lstplayers)
        lstPlayer.adapter = adapter
        fab_add_ply.setOnClickListener { add(lstplayers) }
    }


    private fun add(lstplayers: List<Player>) {
        var realm = Realm.getDefaultInstance()
        val dialog = Dialog(this@AddPlayerActivity)
        dialog.setContentView(R.layout.dialog_add_ply)
        // set the custom dialog components - text, image and button
/* TextView text = (TextView) dialog.findViewById(R.id.tvMsg);
            text.setText("Android custom dialog example!");*/
        val edtNam = dialog.findViewById<View>(R.id.edtNam) as EditText

        val dialogButton =
            dialog.findViewById<View>(R.id.btnAdd) as Button
        dialogButton.setOnClickListener {

            if(edtNam.text.toString().trim().isEmpty()){

                Toast.makeText(this@AddPlayerActivity,"Enter Name",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(lstplayers.none{ player: Player -> player.FirstName == edtNam.text.toString() }){
                val player = Player()
                player.FirstName = edtNam.text.toString()

                player.PlayerId = PlayerLogic(realm).getNextKey()
                PlayerLogic(realm).addOrUpdatePlayer(player)
                adapter?.add(player)
                dialog.dismiss()

                adapter?.notifyDataSetChanged()

            }
        }
        dialog.show()
    }
}
