package com.openclassrooms.entrevoisins.showneighbour;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.AddFavorite;
import com.openclassrooms.entrevoisins.events.MessageDeleteFavorite;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShowNeighbourActivity extends AppCompatActivity {

    String TAG = "ShowNeigbour";
    Context ctx;

    private List<Neighbour> listFavoris = new ArrayList<Neighbour>();

    @BindView(R.id.tv_nom)
    TextView tvNom;

    @BindView(R.id.card1_tv_titre)
    TextView tvTitre;

    @BindView(R.id.card1_tv_sub1)
    TextView tvSub1;

    @BindView(R.id.card1_tv_sub2)
    TextView tvSub2;

    @BindView(R.id.card1_tv_sub3)
    TextView tvSub3;

    @BindView(R.id.card2_tv_apropos)
    TextView tvAPropos;

    @BindView(R.id.backToMain)
    ImageView ivBack;

    @BindView(R.id.fab_favoris)
    ImageView fabFavoris;

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_neighbour);
        ButterKnife.bind(this);
        ctx = this;

        ivBack.setOnClickListener(view ->{
            onBackPressed();
        });

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        Neighbour neighbour = (Neighbour) getIntent().getSerializableExtra("neighbour");
        Log.i(TAG, "Neighbour" + neighbour.toString());
        //Log.i(TAG, "ListFavoris" + listFavoris.toString());

        Glide.with(this)
                .load(neighbour.getAvatarUrl())
                .into(ivAvatar);

        tvTitre.setText(neighbour.getName());
        tvNom.setText(neighbour.getName());
        tvSub1.setText(neighbour.getAddress());
        tvSub2.setText(neighbour.getPhoneNumber());
        tvAPropos.setText(neighbour.getAboutMe());

        if (neighbour.getFavorite()) fabFavoris.setColorFilter(getResources().getColor(R.color.colorAccent));

        /* Clic sur Fab = ajout d'un favori */
        fabFavoris.setOnClickListener(view ->{
            if (!neighbour.getFavorite()) {
                Toast.makeText(this, neighbour.getName() + " a été ajouté(e) à vos favoris.", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(new AddFavorite(neighbour));
                neighbour.setFavorite(true);
                fabFavoris.setColorFilter(getResources().getColor(R.color.colorAccent));
            }
            else Toast.makeText(this, neighbour.getName() + " fait déjà partie de vos favoris.", Toast.LENGTH_SHORT).show();

            /*Snackbar.make(view, neighbour.getName() + " a été ajouté(e) à vos favoris.", Snackbar.LENGTH_LONG).setAction("Mes favoris", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ctx, ListNeighbourActivity.class);
                    startActivity(intent);
                }
            }).show();*/
            });
    }

}