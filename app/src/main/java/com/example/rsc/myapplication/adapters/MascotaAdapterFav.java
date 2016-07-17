package com.example.rsc.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rsc.myapplication.R;
import com.example.rsc.myapplication.pojo.Mascota;

import java.util.ArrayList;


public class MascotaAdapterFav extends RecyclerView.Adapter<MascotaAdapterFav.MascotaViewHolder> {

    private ArrayList<Mascota> mascotas;

    public static class MascotaViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagen;
        public TextView nombre;
        public TextView numero;
        public ImageButton huesoLike;


        public MascotaViewHolder(View itemView) {
            super(itemView);
            imagen = (ImageView) itemView.findViewById(R.id.imagen_card);
            nombre = (TextView) itemView.findViewById(R.id.nombre_card);
            numero = (TextView) itemView.findViewById(R.id.numero_card);
            huesoLike = (ImageButton) itemView.findViewById(R.id.huesito_card);

        }
    }

    public MascotaAdapterFav(ArrayList<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mascota_card,parent,false);
        return new MascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MascotaViewHolder holder, final int i) {
//        holder.imagen.setImageResource(mascotas.get(i).getImagen());

        holder.nombre.setText(mascotas.get(i).getNombre());
        holder.numero.setText(String.valueOf(mascotas.get(i).getLikes()));
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }


}
