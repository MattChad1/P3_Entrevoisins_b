package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.openclassrooms.entrevoisins.events.MessageDeleteFavorite;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesFragment extends Fragment {

    String TAG =  "FavoritesFragment";
    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private List<Neighbour> mFavorites;
    private MyNeighbourRecyclerViewAdapter adapter;


    @BindView(R.id.recycler_list_favorites)
    RecyclerView mRecyclerView;


    public FavoritesFragment() { // TODO: A quoi ça sert?
        // Required empty public constructor
    }


    public static FavoritesFragment newInstance(int position) {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        ButterKnife.bind(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return view;
    }

    public List<Neighbour> filterFavorites() {
        List<Neighbour> re = new ArrayList<>();
        for (Neighbour n : mNeighbours) {
            if (n.getFavorite()) re.add(n);
        }
        return re;
    }

    private void initList() {
        mNeighbours = mApiService.getNeighbours();
        mFavorites = filterFavorites();
        adapter = new MyNeighbourRecyclerViewAdapter(getActivity(), mFavorites, "favorites");
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
        int index = mNeighbours.indexOf(event.neighbour);
        mNeighbours.get(index).setFavorite(true);
        initList();
    }

    /*Supprime un favoris à partir de la liste des favoris */
    @Subscribe
    public void onDeleteFavorites(MessageDeleteFavorite event) {
        int index = mNeighbours.indexOf(event.neighbour);
        Log.i(TAG, "onDeleteFavorites: " + index);
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