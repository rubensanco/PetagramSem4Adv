package com.example.rsc.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rsc.myapplication.R;
import com.example.rsc.myapplication.adapters.MascotaAdapter;
import com.example.rsc.myapplication.pojo.Mascota;
import com.example.rsc.myapplication.presenters.IMascotaRVFragmentPresenter;
import com.example.rsc.myapplication.presenters.MascotaRVFragmentPresenter;

import java.util.ArrayList;


public class MascotaRVFragment extends Fragment implements IMascotaRVFragment {

    private RecyclerView listaMascotas;
    private IMascotaRVFragmentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_mascota_rv, container, false);
        listaMascotas = (RecyclerView) v.findViewById(R.id.reciclador);
        presenter = new MascotaRVFragmentPresenter(this,getContext());

        return v;
    }

    @Override
    public void generarLLVertical() {
        LinearLayoutManager llm =new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listaMascotas.setLayoutManager(llm);
    }

    @Override
    public MascotaAdapter crearAdaptador(ArrayList<Mascota> mascotas) {
        MascotaAdapter mascotaAdapter = new MascotaAdapter(mascotas, getActivity());
        return mascotaAdapter;
    }

    @Override
    public void inicMascotaAdapter(MascotaAdapter mascotaAdapter) {
        listaMascotas.setAdapter(mascotaAdapter);
    }
}
