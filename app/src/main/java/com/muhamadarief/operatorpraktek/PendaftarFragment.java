package com.muhamadarief.operatorpraktek;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.muhamadarief.operatorpraktek.API.ApiService;
import com.muhamadarief.operatorpraktek.Adapter.PendaftaranAdapter;
import com.muhamadarief.operatorpraktek.Model.Pendaftaran;
import com.muhamadarief.operatorpraktek.Utils.ApiUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class PendaftarFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "PendaftarFragment";

    @BindView(R.id.txt_ubah_tanggal)
    TextView txtTanggal;

    @BindView(R.id.rv_item)
    RecyclerView rv_item;

    private PendaftaranAdapter pendaftaranAdapter;
    private ArrayList<Pendaftaran> listPasien = new ArrayList<Pendaftaran>();

    private ApiService mApiService;

    private Calendar calendar;
    private int DATETIME_TAG = 0;
    private DateFormat dateFormat;
    private SimpleDateFormat simpleDateFormat;

    private String id_praktek;
    private String tanggal;



    public static PendaftarFragment newInstance(String id_praktek){
        PendaftarFragment pendaftarFragment = new PendaftarFragment();
        Bundle args = new Bundle();
        args.putString("id_praktek", id_praktek);
        pendaftarFragment.setArguments(args);

        return pendaftarFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pendaftar, container, false);
        ButterKnife.bind(this, view);
        mApiService = ApiUtils.getApiService();

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tanggal = simpleDateFormat.format(calendar.getTime());

        txtTanggal.setText(""+dateFormat.format(calendar.getTime()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rv_item.setLayoutManager(linearLayoutManager);

       // id_praktek = getArguments().getString("id_praktek");

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null){
            id_praktek = args.getString("id_praktek");
            Log.d(TAG,id_praktek);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListPasien(id_praktek, tanggal);
    }

    @OnClick (R.id.txt_ubah_tanggal)
    public void onUbahTanggalClick(){
        DatePickerDialog.newInstance(
                PendaftarFragment.this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show(getActivity().getFragmentManager(),"datePicker");
        DATETIME_TAG = 1;
    }


    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        switch (DATETIME_TAG) {
            case 1 :
                calendar.set(year, monthOfYear, dayOfMonth);
                txtTanggal.setText(dateFormat.format(calendar.getTime()));
                String formattedDate = simpleDateFormat.format(calendar.getTime());
                getListPasien(id_praktek, formattedDate);
                break;
        }
    }

    public void getListPasien(String id_praktek, String tanggal){
        mApiService.getDaftarPasien(id_praktek, tanggal).enqueue(new Callback<List<Pendaftaran>>() {
            @Override
            public void onResponse(Call<List<Pendaftaran>> call, Response<List<Pendaftaran>> response) {
                int jumlah = response.body().size();
                //Toast.makeText(getContext(), ""+jumlah, Toast.LENGTH_SHORT).show();

                if(listPasien != null){
                    listPasien.clear();
                }

                for(int i = 0; i < jumlah; i++){
                    Pendaftaran pendaftaran = new Pendaftaran(
                            response.body().get(i).getId(),
                            response.body().get(i).getId_praktek(),
                            response.body().get(i).getTanggal(),
                            response.body().get(i).getNama(),
                            response.body().get(i).getAlamat(),
                            response.body().get(i).getNohp(),
                            response.body().get(i).getStatus(),
                            response.body().get(i).getNo_antrian()
                    );
                    listPasien.add(pendaftaran);
                }

                pendaftaranAdapter = new PendaftaranAdapter(getContext(),listPasien);
                rv_item.setAdapter(pendaftaranAdapter);

            }

            @Override
            public void onFailure(Call<List<Pendaftaran>> call, Throwable t) {
                Log.d(TAG, "Failure retrieve from server: " +t);
            }
        });
    }


}
