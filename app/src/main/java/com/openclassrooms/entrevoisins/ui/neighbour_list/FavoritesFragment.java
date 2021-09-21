package com.openclassrooms.entrevoisins.ui.neighbour_list;

import static com.openclassrooms.entrevoisins.model.Neighbour.addFavorite;
import static com.openclassrooms.entrevoisins.model.Neighbour.filterFavorites;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.AddFavorite;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DeleteFavorite;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesFragment extends Fragment {

    String TAG = "FavoritesFragment";
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private List<Neighbour> mFavorites;


    @BindView(R.id.recycler_list_favorites)
    RecyclerView mRecyclerView;


    public static FavoritesFragment newInstance(int position) {
        return new FavoritesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        ButterKnife.bind(Objects.requireNonNull(getActivity()));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        return view;
    }


    private void initList() {
        mNeighbours = mApiService.getNeighbours();
        mFavorites = filterFavorites(mNeighbours);
        MyNeighbourRecyclerViewAdapter adapter = new MyNeighbourRecyclerViewAdapter(getActivity(), mFavorites, "favorites");
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    /* Ajoute un favoris avec Eventbus */
    @Subscribe(sticky = true)
    public void onAddFavorite(AddFavorite event) {
        Neighbour neighbour = event.neighbour;
        addFavorite(neighbour, mNeighbours);
        initList();
    }

    /*Supprime un favoris à partir de la liste des favoris */
    @Subscribe
    public void onDeleteFavorites(DeleteFavorite event) {
        int index = mNeighbours.indexOf(event.neighbour);
        mNeighbours.get(index).setFavorite(false);
        initList();
    }


    /*Si un neighbour est supprimé à partir de la liste générale, on le supprime aussi de la liste des favoris
     */
    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {
        mNeighbours.remove(event.neighbour);
        initList();
    }
}