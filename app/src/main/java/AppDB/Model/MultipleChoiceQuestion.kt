package AppDB.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.json.JSONArray

@Parcelize
class MultipleChoiceQuestion (var question: String, var correct_answer: String, var incorrect_answers: List<String>) :Parcelable
