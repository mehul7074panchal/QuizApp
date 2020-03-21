package CommonUtils

import AppDB.Model.MultipleChoiceQuestion
import android.content.Context
import android.os.Build
import android.text.Html
import org.json.JSONObject

import java.io.IOException
import java.io.InputStream
import java.util.ArrayList

class Utils {

   public companion object{
        fun getJsonFromAssets(context: Context, fileName: String): List<MultipleChoiceQuestion> {
            val lstMCQ: MutableList<MultipleChoiceQuestion> = ArrayList()
            var jsonString: String
            jsonString = try {
                val stream: InputStream = context.assets.open(fileName)
                val size: Int = stream.available()
                val buffer = ByteArray(size)
                stream.read(buffer)
                stream.close()
                String(buffer)
            } catch (e: IOException) {
                e.printStackTrace()
                return lstMCQ
            }

            val josnArr =  JSONObject(jsonString).getJSONArray("questions")
            for (i in 0 until josnArr.length()) {

                val obj = josnArr.getJSONObject(i)

                val listIncorrectAnswers = Array(obj.optJSONArray("incorrect_answers").length()) {
                    obj.optJSONArray("incorrect_answers").getString(it)
                }
                lstMCQ.add(MultipleChoiceQuestion(obj.optString("question"),
                    obj.optString("correct_answer"),
                    listIncorrectAnswers.toList()))
            }
            return lstMCQ
        }

        fun stringCon(value: String): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                Html.fromHtml(value).toString()
            }
        }
    }

}