package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mehul.quizapp.QuizActivity;
import com.mehul.quizapp.R;

import java.util.ArrayList;
import java.util.List;

import AppDB.Controller.ScoreboardLogic;
import AppDB.Model.Item;
import AppDB.Model.Player;
import AppDB.Model.Scoreboard;
import CommonUtils.Utils;
import io.realm.Realm;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private ArrayList<Item> mValues;
    private Context mContext;
    private Player mplayer;


    public HomeAdapter(Context context, ArrayList<Item> values, Player player) {
        mValues = values;
        mContext = context;
        mplayer = player;

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSub, tvScore;
        private ImageView imageView;
        private RelativeLayout relativeLayout;
        final View mView;

        ViewHolder(View v) {
            super(v);
            mView = v;
            tvSub = v.findViewById(R.id.tvSub);
            tvScore = v.findViewById(R.id.tvScore);
            imageView = v.findViewById(R.id.imageView);
            relativeLayout = v.findViewById(R.id.relativeLayout);
        }

        @SuppressLint("SetTextI18n")
        void setData(Item item) {
            tvSub.setText(item.getText());
            if (mplayer.getPlayerId() != null) {
                Realm realm = Realm.getDefaultInstance();
                ScoreboardLogic sbl = new ScoreboardLogic(realm);
                List<Scoreboard> scoreboardList = sbl.getScoreboardByPlayer(mplayer.getPlayerId(),
                        true, item.getText());
                if (scoreboardList.size() > 0  && scoreboardList.get(0) != null) {
                    tvScore.setText((scoreboardList.get(0) != null ? scoreboardList.get(0).getScore() : 0) + "/" +  Utils.Companion.
                            getJsonFromAssets(mContext, item.getText().toLowerCase() + ".json")
                            .size());
                }
            }
            imageView.setImageResource(item.getDrawable());
            relativeLayout.setBackgroundColor(Color.parseColor(item.getColor()));
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_grid_subject, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setData(mValues.get(position));

        viewHolder.mView.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, QuizActivity.class)
                    .putExtra("sub", mValues.get(position).getText())
                    .putExtra("player",mplayer));
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


}
