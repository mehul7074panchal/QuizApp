package AppDB.Controller

import AppDB.Model.Player
import android.annotation.SuppressLint
import io.realm.Realm
import io.realm.RealmResults
import org.json.JSONArray

class PlayerLogic {

    var realm: Realm

    @SuppressLint("StaticFieldLeak")

    // user Table Columns names
    val FirstName = "FirstName"
    val Subject = "Subject"
    val Score = "Score"
    val PlayerId = "PlayerId"


    constructor(realm: Realm) {

        this.realm = realm


    }

    /*  fun addPlayer(Player: Player): Player {

          realm.beginTransaction()
          val obj = realm.copyToRealm(Player)
          realm.commitTransaction()
          return obj


      }


      */
    fun addOrUpdatePlayer(Player: Player) {

        if(!realm.isInTransaction){
            realm.beginTransaction()

        }
        realm.insertOrUpdate(Player)
        realm.commitTransaction()



    }

    fun addOrUpdatePlayer(Player: MutableList<Player>) {

        realm.beginTransaction()
        realm.insertOrUpdate(Player)
        realm.commitTransaction()



    }


    fun addOrUpdatePlayer(jsonArray: JSONArray) {

        realm.beginTransaction()
        realm.createOrUpdateAllFromJson(Player::class.java, jsonArray)
        realm.commitTransaction()


    }



    //clear all objects from Player.class
    fun clearAll() {

        realm.beginTransaction()
        realm.delete(Player::class.java)
        realm.commitTransaction()
    }

    fun deletePlayer(player: Player) {
        realm.executeTransaction { realm ->
            val result: RealmResults<Player> =
                realm.where(Player::class.java).equalTo(PlayerId, player.PlayerId).findAll()
            result.deleteAllFromRealm()
        }

    }

    //find all objects in the Player.class
    fun getPlayers(): RealmResults<Player> {

        return realm.where(Player::class.java).findAll()
    }

    //query a single item with the given id
    fun getPlayer(name: String): RealmResults<Player> {

        return realm.where(Player::class.java).equalTo(FirstName, name).findAll()
    }

    fun getNextKey(): Int {
        return try {
            val number = realm.where(Player::class.java).max(PlayerId)
            if (number != null) {
                number.toInt()  + 1
            } else {
                0
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            0
        }
    }
}