package com.muhamadarief.operatorpraktek;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.muhamadarief.operatorpraktek.API.ApiService;
import com.muhamadarief.operatorpraktek.Model.BaseResponse;
import com.muhamadarief.operatorpraktek.Model.PraktekDokter;
import com.muhamadarief.operatorpraktek.Utils.ApiUtils;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {
    public static final String TAG = "BerandaFragment";

    @BindView(R.id.switch_status)
    Switch switch_status;

    @BindView(R.id.img_status)
    ImageView img_status;

    @BindView(R.id.txt_status)
    TextView txt_status;

    @BindView(R.id.txt_keterangan)
    TextView txt_keterangan;

    private String id_praktek;
    private ApiService mApiService;
    private PraktekDokter praktekDokter;

    public static BerandaFragment newInstance(String id_praktek){
        BerandaFragment berandaFragment =  new BerandaFragment();
        Bundle args = new Bundle();
        args.putString("id_praktek", id_praktek);
        berandaFragment.setArguments(args);

        return berandaFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        ButterKnife.bind(this, view);
        mApiService = ApiUtils.getApiService();


        final SharedPreferences sharedPrefs = getContext().getSharedPreferences("STATE_DAFTAR", MODE_PRIVATE);


        boolean status = sharedPrefs.getBoolean("status",false);

        switch_status.setChecked(status);
        if (status){
            img_status.setImageResource(R.drawable.statusonline);
            txt_status.setText("Online");
            txt_status.setTextColor(getResources().getColor(R.color.colorOnline));
            txt_keterangan.setText("Pendaftaran Berobat telah dibuka !");
        }

        switch_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(!isChecked){
                    Log.d(TAG,"Pendaftaran ditutup");
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("STATE_DAFTAR", MODE_PRIVATE).edit();
                    editor.putBoolean("status", false);
                    editor.commit();

                    updateStatus(id_praktek, false);
                    img_status.setImageResource(R.drawable.statusoffline2);
                    txt_status.setText("Offline");
                    txt_status.setTextColor(getResources().getColor(R.color.colorOffline));
                    txt_keterangan.setText(R.string.keterangan);

                } else {
                    Log.d(TAG, "Pendaftaran dibuka");
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("STATE_DAFTAR", MODE_PRIVATE).edit();
                    editor.putBoolean("status", true);
                    editor.commit();

                    updateStatus(id_praktek, true);
                    img_status.setImageResource(R.drawable.statusonline);
                    txt_status.setText("Online");
                    txt_status.setTextColor(getResources().getColor(R.color.colorOnline));
                    txt_keterangan.setText("Pendaftaran Berobat telah dibuka !");

                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null){
            id_praktek = args.getString("id_praktek");
        }
    }


    public void updateStatus(String id_praktek, final boolean status){
        mApiService.updateStatus(id_praktek, status).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d(TAG, "Status pendaftaran?: "+status);
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d(TAG, "failure: "+t);
            }
        });
    }
}
