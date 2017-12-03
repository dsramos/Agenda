package com.example.davison.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.davison.agenda.dao.Dao;
import com.example.davison.agenda.modelo.Aluno;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by Davison on 17/11/2017.
 */

public class MapaFragment  extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        getMapAsync(this);
        super.onCreate(bundle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng posicaoDaEscola = pegaCordenadaPorEndereco("Rua Vergeiro 3185 , Vila Mariana São Paulo");
        if(posicaoDaEscola != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoDaEscola,17);
            googleMap.moveCamera(update);
        }

        //Quando clica no marcador
        //googleMap.setOnMapClickListener();

        Dao dao = new Dao(getContext());
        for (Aluno aluno: dao.buscaAlunos()) {
            LatLng cordenada = pegaCordenadaPorEndereco(aluno.getEndereco());
            if(cordenada != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(cordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }

        }
        dao.close();

        new Localizador(getContext(),googleMap);
    }

    public LatLng pegaCordenadaPorEndereco(String endereco){
        try {
            Geocoder geocoder = new Geocoder(getContext());

            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);
            if(!resultados.isEmpty()){
                LatLng latitude = new LatLng(resultados.get(0).getLatitude(),resultados.get(0).getLongitude());
                return latitude;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
