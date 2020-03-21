package AppDB.Model


import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey


open class Player() : RealmObject(),Parcelable {

    @PrimaryKey
    var PlayerId: Int? = null
    var FirstName: String? = null

    constructor(parcel: Parcel) : this() {
        PlayerId = parcel.readValue(Int::class.java.classLoader) as? Int
        FirstName = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(PlayerId)
        parcel.writeString(FirstName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }


}




