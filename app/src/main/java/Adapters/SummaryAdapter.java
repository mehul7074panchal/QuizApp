package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mehul.quizapp.R;

import java.util.HashMap;
import java.util.List;

import AppDB.Model.MultipleChoiceQuestion;
import CommonUtils.Utils;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {
    private  List<MultipleChoiceQuestion> mValues;
    //  private List<Integer> stdIDs;


    private HashMap<String,String> questionStringHashMap;





    private Context mcontext;



    public SummaryAdapter(Context context, HashMap<String,String> multipleChoiceQuestionStringHashMap,List<MultipleChoiceQuestion> choiceQuestions) {


         this.mcontext = context;
        this.questionStringHashMap = multipleChoiceQuestionStringHashMap;
        this.mValues = choiceQuestions;

        this.questionStringHashMap = new HashMap<>();
        this.questionStringHashMap.putAll(multipleChoiceQuestionStringHashMap);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tablerowrepeating_unit, parent, false);
        return new SummaryAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvValue.setText(Utils.Companion.stringCon(holder.mItem.getQuestion())+"\nIncorrect_answers\n"
                +holder.mItem.getIncorrect_answers().toString()+"\nCorrect_answer\n"+
                Utils.Companion.stringCon(holder.mItem.getCorrect_answer()));
        holder.tvKey.setText(Utils.Companion.stringCon(questionStringHashMap.get(holder.mItem.getQuestion())));
        if(Utils.Companion.stringCon(holder.mItem.getCorrect_answer()).equals(Utils.Companion.stringCon(questionStringHashMap.get(holder.mItem.getQuestion())))){
            holder.tvKey.setTextColor(mcontext.getResources().getColor(R.color.green));
        }else {
            holder.tvKey.setTextColor(mcontext.getResources().getColor(R.color.darkred));

        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;

        final TextView tvKey,tvValue;

        public MultipleChoiceQuestion mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;


            tvValue =  view.findViewById(R.id.tvValue);

            tvKey =  view.findViewById(R.id.tvKey);




        }

        @Override
        public String toString() {
            return "";
        }
    }



}
