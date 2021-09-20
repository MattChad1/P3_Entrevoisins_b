package com.openclassrooms.entrevoisins.ui.showneighbour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.AddFavorite;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShowNeighbourActivity extends AppCompatActivity {

    private static final String TAG = "ShowNeigbourActivity";

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

        ivBack.setOnClickListener(view -> onBackPressed());

        Neighbour neighbour = (Neighbour) getIntent().getSerializableExtra("neighbour");

        Glide.with(this)
                .load(neighbour.getAvatarUrl())
                .into(ivAvatar);

        tvTitre.setText(neighbour.getName());
        tvNom.setText(neighbour.getName());
        tvSub1.setText(neighbour.getAddress());
        tvSub2.setText(neighbour.getPhoneNumber());
        tvAPropos.setText(neighbour.getAboutMe());

        if (neighbour.getFavorite())
            fabFavoris.setColorFilter(getResources().getColor(R.color.colorAccent));

        /* Clic sur Fab = ajout d'un favori */
        fabFavoris.setOnClickListener(view -> {
            if (!neighbour.getFavorite()) {
                Toast.makeText(this, getString(R.string.toast_add_favorite, neighbour.getName()), Toast.LENGTH_SHORT).show();
                EventBus.getDefault().postSticky(new AddFavorite(neighbour));
                neighbour.setFavorite(true);
                fabFavoris.setColorFilter(getResources().getColor(R.color.colorAccent));
            } else
                Toast.makeText(this, getString(R.string.toast_already_favorite, neighbour.getName()), Toast.LENGTH_SHORT).show();
        });
    }

}