package com.example.rsc.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rsc.myapplication.R;
import com.example.rsc.myapplication.adapters.MiMascotaAdapter;
import com.example.rsc.myapplication.pojo.Mascota;
import com.example.rsc.myapplication.presenters.PerfilMascotaFragmentPresenter;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class PerfilMascotaFragment extends Fragment implements IPerfilMascotaFragment{

    private RecyclerView gridMiMascota;
    private PerfilMascotaFragmentPresenter presenter;
    private CircularImageView ivMiMascota;
    private TextView tvMiMascotaNombre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil_mascota,container,false);
        gridMiMascota = (RecyclerView) v.findViewById(R.id.reciclador_mimascota);
        ivMiMascota = (CircularImageView) v.findViewById(R.id.ivMiMascota);
        tvMiMascotaNombre = (TextView) v.findViewById(R.id.tvMiMascotaNombre);

        presenter = new PerfilMascotaFragmentPresenter(this,getContext());

        return v;
    }

    @Override
    public void generarGridLayout() {
        GridLayoutManager glm = new GridLayoutManager(getActivity(),3);
        gridMiMascota.setLayoutManager(glm);
    }

    @Override
    public MiMascotaAdapter crearAdaptadorMiMascota(ArrayList<Mascota> miMascota) {
        MiMascotaAdapter miMascotaAdapter = new MiMascotaAdapter(miMascota,getActivity());
        return miMascotaAdapter;
    }

    @Override
    public void inicMiMascotaAdapter(MiMascotaAdapter miMascotaAdapter) {
        gridMiMascota.setAdapter(miMascotaAdapter);
    }

    @Override
    public void datosMiMascota(Mascota mascotaPerfil) {
        Picasso.with(getContext())
                .load(mascotaPerfil.getImagen())
                .placeholder(R.drawable.dog_footprint_48)
                .into(ivMiMascota);
        tvMiMascotaNombre.setText(mascotaPerfil.getNombre());
    }
}
