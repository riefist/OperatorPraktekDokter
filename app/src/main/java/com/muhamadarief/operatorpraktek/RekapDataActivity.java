package com.muhamadarief.operatorpraktek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RekapDataActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.txt_ubah_tanggal)
    TextView txtTanggal;

    @BindView(R.id.rv_item)
    RecyclerView rv_item;


    public static final String TAG = "RekapActivity";
    public static final String EXTRA_ID_PRAKTEK = "id_praktek";
    private String id_praktek;

    private Calendar pick_calendar;
    private int DATETIME_TAG = 0;
    private DateFormat dateFormat;
    private SimpleDateFormat simpleDateFormat;

    private ApiService mApiService;
    private ArrayList<Pendaftaran> listPasien = new ArrayList<Pendaftaran>();
    private PendaftaranAdapter pendaftaranAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap_data);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Rekap Data");
        mApiService = ApiUtils.getApiService();
        id_praktek = getIntent().getStringExtra(EXTRA_ID_PRAKTEK);

        pick_calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_item.setLayoutManager(linearLayoutManager);

    }

    @OnClick (R.id.txt_ubah_tanggal)
    public void onUbahTanggalClick(){
        DatePickerDialog.newInstance(RekapDataActivity.this, pick_calendar.get(Calendar.YEAR), pick_calendar.get(Calendar.MONTH), pick_calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
        DATETIME_TAG = 1;
    }



    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        switch (DATETIME_TAG) {
            case 1 :
                pick_calendar.set(year, monthOfYear, dayOfMonth);
                txtTanggal.setText(dateFormat.format(pick_calendar.getTime()));
                String formattedDate = simpleDateFormat.format(pick_calendar.getTime());
                getPasien(id_praktek, formattedDate);
                break;
        }
    }

    public void getPasien(String id_praktek, String tanggal){
        mApiService.getDaftarPasien(id_praktek, tanggal).enqueue(new Callback<List<Pendaftaran>>() {
            @Override
            public void onResponse(Call<List<Pendaftaran>> call, Response<List<Pendaftaran>> response) {
                int jumlah = response.body().size();
                Toast.makeText(RekapDataActivity.this, ""+jumlah, Toast.LENGTH_SHORT).show();

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

                pendaftaranAdapter = new PendaftaranAdapter(getApplicationContext(), listPasien);
                rv_item.setAdapter(pendaftaranAdapter);

            }

            @Override
            public void onFailure(Call<List<Pendaftaran>> call, Throwable t) {
                Log.d(TAG, "Failure retrieve from server: " +t);
            }
        });
    }
}
