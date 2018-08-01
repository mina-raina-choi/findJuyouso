package com.example.minachoi.findjuyou.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minachoi.findjuyou.R;
import com.example.minachoi.findjuyou.adapter.CustomListAdapter;
import com.example.minachoi.findjuyou.models.GasStation;
import com.example.minachoi.findjuyou.models.OIL;
import com.example.minachoi.findjuyou.models.RESULT;
import com.example.minachoi.findjuyou.network.APIClient;
import com.example.minachoi.findjuyou.network.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    APIInterface apiInterface;

    String TAG = "ListFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View listView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = listView.findViewById(R.id.list_recyclerview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return listView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        apiInterface = APIClient.getRetrofit().create(APIInterface.class);

        Call<GasStation> call = apiInterface.getNearestGasStation("F310180626", "314681.8", "544837", "3000", "1", "B027", "json");
        call.enqueue(new Callback<GasStation>() {
            @Override
            public void onResponse(Call<GasStation> call, Response<GasStation> response) {

                GasStation result = response.body();
                List<OIL> oilList = result.getRESULT().getOIL();
                Log.d(TAG, "onResponse: result" + oilList);
                Log.d(TAG, "onResponse: oilList 0 " + oilList.get(0));
                adapter = new CustomListAdapter(oilList, getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<GasStation> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call);
                call.cancel();
            }
        });
    }

    public interface SelectOil {
        void selectOilStation(OIL oil);
    }
}
