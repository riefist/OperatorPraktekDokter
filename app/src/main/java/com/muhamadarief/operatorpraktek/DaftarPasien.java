package com.muhamadarief.operatorpraktek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.muhamadarief.operatorpraktek.Adapter.PendaftaranAdapter;
import com.muhamadarief.operatorpraktek.API.ApiService;
import com.muhamadarief.operatorpraktek.Model.Pendaftaran;
import com.muhamadarief.operatorpraktek.Utils.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarPasien extends AppCompatActivity {

    @BindView(R.id.txt_tanggal)
    TextView txt_tanggal;

    @BindView(R.id.rv_item)
    RecyclerView rv_item;

    public static final String TAG = "DaftarPasienActivity";

    public static final String EXTRA_ID_PRAKTEK = "id_praktek";

    private PendaftaranAdapter pendaftaranAdapter;
    private ArrayList<Pendaftaran> listPasien = new ArrayList<Pendaftaran>();

    private ApiService mApiService;

    private String id_praktek;
    private String tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pasien);
        ButterKnife.bind(this);
        mApiService = ApiUtils.getApiService();

        id_praktek = getIntent().getStringExtra(EXTRA_ID_PRAKTEK);

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        tanggal = df.format(calendar.getTime());
        txt_tanggal.setText(tanggal);

        Log.d(TAG, "id praktek: "+id_praktek+" tanggal: "+tanggal);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_item.setLayoutManager(linearLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getPasien(id_praktek, tanggal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                getPasien(id_praktek, tanggal);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPasien(String id_praktek, String tanggal){

        mApiService.getDaftarPasien(id_praktek, tanggal).enqueue(new Callback<List<Pendaftaran>>() {
            @Override
            public void onResponse(Call<List<Pendaftaran>> call, Response<List<Pendaftaran>> response) {
                int jumlah = response.body().size();
                Log.d(TAG, "jumlah object: "+jumlah);

                listPasien.clear();

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
                Log.d(TAG, "Failed retrieve from server: " +t);
            }
        });
    }

}
