package AppDB.Model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class  Scoreboard : RealmObject(){
    @PrimaryKey
    var ScoreboardId : Int? = null
    var PlayerId : Int? = null
    var Subject: String? = null
    var score: Int? = null
    var isAttempt: Boolean? = null
}