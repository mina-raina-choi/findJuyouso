package com.example.minachoi.findjuyou.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minachoi.findjuyou.R;
import com.example.minachoi.findjuyou.models.OIL;

public class MapFragment extends Fragment{

    final String TAG = "MapFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mapView = inflater.inflate(R.layout.fragment_map, container, false);


        return mapView;
    }

    public void showSelectedOilStation(OIL oil) {
        Log.d(TAG, "showSelectedOilStation: " + oil.getOSNM());
    }
}
