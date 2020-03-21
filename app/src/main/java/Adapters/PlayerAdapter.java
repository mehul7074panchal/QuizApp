package Adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.mehul.quizapp.HomeActivity;
import com.mehul.quizapp.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import AppDB.Controller.PlayerLogic;
import AppDB.Controller.ScoreboardLogic;
import AppDB.Model.Player;
import io.realm.Realm;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private List<Player> mValues;


    private Context mcontext;


    private ArrayList<Player> arraylist;


    String searchText = "";
    String batchName;


    public PlayerAdapter(Context context, List<Player> sLst) {


        mcontext = context;
        this.mValues = sLst;

        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(sLst);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_player, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        if (holder.mItem != null) {
            holder.tvP_Name.setText(holder.mItem.getFirstName());

            ColorGenerator generator = ColorGenerator.MATERIAL;


            char ch = 'P';

            if (holder.mItem.getFirstName() != null && holder.mItem.getFirstName().length() > 0) {

                ch = holder.mItem.getFirstName().charAt(0);
            }


            int colorRandam = generator.getColor(Character.toUpperCase(ch));

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(Character.toUpperCase(holder.mItem.getFirstName().charAt(0)) + "", colorRandam);

            holder.ivP.setImageDrawable(drawable);
        }


        holder.btnE.setOnClickListener(v -> {
            update(holder.mItem, position);

        });

        holder.btnD.setOnClickListener(v -> {


            Realm realm = Realm.getDefaultInstance();
            PlayerLogic playerLogic = new PlayerLogic(realm);
            new ScoreboardLogic(realm).deleteScoreboardByPlayerId(holder.mItem.getPlayerId());

            playerLogic.deletePlayer(holder.mItem);

            mValues.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();

        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void add(Player obj) {

        if (obj.getFirstName() != null) {
            obj = new PlayerLogic(Realm.getDefaultInstance()).getPlayer(obj.getFirstName()).first();
            mValues.add(obj);
        }

    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;

        final TextView tvP_Name;
        final ImageButton btnD, btnE;
        Player mItem;
        final ImageView ivP;

        public ViewHolder(View view) {
            super(view);
            mView = view;


            tvP_Name = view.findViewById(R.id.tvName);
            btnD = view.findViewById(R.id.btnPDelete);
            btnE = view.findViewById(R.id.btnPEdit);
            ivP = view.findViewById(R.id.ivProfile);


            view.setOnClickListener(v -> {
                mcontext.startActivity(new Intent(mcontext, HomeActivity.class).putExtra("player", mItem));
            });


        }

        @Override
        public String toString() {
            return "";
        }
    }


    // Filter Class
    public void filter(String searchText) throws ParseException {
        searchText = searchText.toLowerCase(Locale.getDefault());
        this.searchText = searchText;
        mValues.clear();
        if (searchText.length() == 0) {
            mValues.addAll(arraylist);
        } else {
            for (Player player : arraylist) {
                if (player.getFirstName().toLowerCase(Locale.getDefault()).contains(searchText)) {
                    mValues.add(player);
                }
            }
        }
        notifyDataSetChanged();
    }


    private void update(Player player, int pos) {
        final Dialog dialog = new Dialog(mcontext);
        Realm realm = Realm.getDefaultInstance();
        dialog.setContentView(R.layout.dialog_add_ply);


        // set the custom dialog components - text, image and button
           /* TextView text = (TextView) dialog.findViewById(R.id.tvMsg);
            text.setText("Android custom dialog example!");*/
        final EditText edtname = (EditText) dialog.findViewById(R.id.edtNam);

        edtname.setText(player.getFirstName());

        final Button dialogButton = (Button) dialog.findViewById(R.id.btnAdd);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtname.getText().toString().trim().length() > 0) {
                    PlayerLogic playerLogic = new PlayerLogic(realm);
                    if (playerLogic.getPlayer(edtname.getText().toString()).size() > 1) {
                        Toast.makeText(mcontext, "Already exist", Toast.LENGTH_LONG).show();

                    } else {
                        realm.beginTransaction();
                        player.setFirstName(edtname.getText().toString());
                        playerLogic.addOrUpdatePlayer(player);

                        mValues.remove(pos);
                        mValues.add(pos, playerLogic.getPlayer(edtname.getText().toString()).first());
                        dialog.dismiss();
                        notifyItemChanged(pos);

                    }
                }else {
                   edtname.setError("Please enter name here.");

                }

            }
        });
        dialog.show();
    }

}
