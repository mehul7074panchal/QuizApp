package AppDB.Controller

import AppDB.Model.Scoreboard
import android.annotation.SuppressLint
import io.realm.Realm
import io.realm.RealmResults
import org.json.JSONArray

class ScoreboardLogic(var realm: Realm) {

    @SuppressLint("StaticFieldLeak")


    // user Table Columns names
    val PlayerId = "PlayerId"
    val Subject = "Subject"
    val Score = "Score"
    val isAttempt = "isAttempt"
    val ScoreboardId = "ScoreboardId"


    /*  fun addScoreboard(Scoreboard: Scoreboard): Scoreboard {

          realm.beginTransaction()
          val obj = realm.copyToRealm(Scoreboard)
          realm.commitTransaction()
          return obj


      }


      */
    fun addOrUpdateScoreboard(Scoreboard: Scoreboard) {

        realm.beginTransaction()
        realm.insertOrUpdate(Scoreboard)
        realm.commitTransaction()



    }

    fun addOrUpdateScoreboard(Scoreboard: MutableList<Scoreboard>) {

        realm.beginTransaction()
        realm.insertOrUpdate(Scoreboard)
        realm.commitTransaction()



    }


    fun addOrUpdateScoreboard(jsonArray: JSONArray) {

        realm.beginTransaction()
        realm.createOrUpdateAllFromJson(Scoreboard::class.java, jsonArray)
        realm.commitTransaction()


    }



    //clear all objects from Scoreboard.class
    fun clearAll() {

        realm.beginTransaction()
        realm.delete(Scoreboard::class.java)
        realm.commitTransaction()
    }

    /*fun deleteScoreboard(Scoreboard: Scoreboard) {
        var lstPly = getScoreboards();

        realm.beginTransaction()
        lstPly.deleteFromRealm(lstPly.indexOf(lstPly.first { Scoreboard -> Scoreboard.FirstName == Scoreboard.FirstName }))
        // realm.de   (Scoreboard::class.java)
        realm.commitTransaction()
    }*/

    //find all objects in the Scoreboard.class
    fun getScoreboards(): RealmResults<Scoreboard> {

        return realm.where(Scoreboard::class.java).findAll()
    }

    //query a single item with the given id
    fun getScoreboard(id: Int): RealmResults<Scoreboard> {

        return realm.where(Scoreboard::class.java).equalTo(ScoreboardId, id).findAll()
    }

    fun getScoreboardByPlayer(idplayer: Int?, isAttemptFLg: Boolean, subject: String): RealmResults<Scoreboard> {

        return realm.where(Scoreboard::class.java).equalTo(PlayerId, idplayer)
            .and()
            .equalTo(isAttempt,isAttemptFLg)
            .and()
            .equalTo(Subject,subject)
            .findAll()
    }

    fun getNextKey(): Int {
        return try {
            val number = realm.where(Scoreboard::class.java).max(ScoreboardId)
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