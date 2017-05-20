package com.muhamadarief.operatorpraktek;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.muhamadarief.operatorpraktek.API.ApiService;
import com.muhamadarief.operatorpraktek.Model.BaseResponse;
import com.muhamadarief.operatorpraktek.Model.User;
import com.muhamadarief.operatorpraktek.Utils.ApiUtils;
import com.muhamadarief.operatorpraktek.Utils.PrefUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";

    @BindView(R.id.edt_email)
    EditText edt_email;

    @BindView(R.id.edt_nohp)
    EditText edt_nohp;

    @BindView(R.id.edt_jenis_kelamin)
    EditText edt_jenis_kelamin;

    @BindView(R.id.txt_nama)
    TextView txt_nama;

    @BindView(R.id.btn_logout)
    Button btnLogout;

    @BindView(R.id.scroll)
    ScrollView scroll;

    private ApiService mApiService;
    private PrefUtil prefUtil;
    private String id_praktek;
    private User user;
    private String actionState;

    public static ProfileFragment newInstance(String id_praktek) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("id_praktek", id_praktek);
        profileFragment.setArguments(args);

        return profileFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setElevation(0);
        ButterKnife.bind(this, view);
        mApiService = ApiUtils.getApiService();


        edt_email.setEnabled(false);
        edt_nohp.setEnabled(false);
        edt_jenis_kelamin.setEnabled(false);

        //getDataOperator(id_praktek);

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            id_praktek = args.getString("id_praktek");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDataOperator(id_praktek);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);

        if (actionState == "HIDE_EDIT"){
            menu.findItem(R.id.action_edit).setVisible(false);
            menu.findItem(R.id.action_cancel).setVisible(true);
        } else {
            menu.findItem(R.id.action_edit).setVisible(true);
            menu.findItem(R.id.action_cancel).setVisible(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_edit:

                actionState = "HIDE_EDIT";
                getActivity().invalidateOptionsMenu();

                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(View.FOCUS_DOWN);
                    }
                });

                edt_email.setEnabled(true);
                edt_jenis_kelamin.setEnabled(true);
                edt_nohp.setEnabled(true);
                if (user.getData().getJenis_kelamin() != null){
                    edt_jenis_kelamin.requestFocus();
                }
                btnLogout.setText("SIMPAN");

                return true;
            case R.id.action_cancel:
                edt_email.setEnabled(false);
                edt_nohp.setEnabled(false);
                edt_jenis_kelamin.setEnabled(false);
                btnLogout.setText("LOGOUT");

                actionState = "SHOW_EDIT";
                getActivity().invalidateOptionsMenu();
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(View.FOCUS_UP);
                    }
                });
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick(R.id.btn_logout)
    public void logoutAct() {
        btnClick(btnLogout.getText().toString());
    }

    public void btnClick(String state) {
        if (state.equals("SIMPAN")) {
            String email = edt_email.getText().toString();
            String nohp = edt_nohp.getText().toString();
            String jenis_kelamin = edt_jenis_kelamin.getText().toString();

            updateDataOperator(id_praktek, email, nohp, jenis_kelamin);

        } else {
            prefUtil.clear(getContext());

            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
    }

    public void getDataOperator(String id_praktek) {
        mApiService.getDataOperator(id_praktek).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = (User) response.body();

                if (user != null) {
                    if (!user.isError()) {
                        edt_email.setText(user.getData().getEmail());
                        edt_nohp.setText(user.getData().getNohp());
                        edt_jenis_kelamin.setText(user.getData().getJenis_kelamin());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "Failure : " + t);
            }
        });
    }

    public void updateDataOperator(String id_praktek, String email, String nohp,
                                   String jenis_kelamin){
        mApiService.updateDataOperator(id_praktek, email, nohp, jenis_kelamin)
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        BaseResponse baseResponse = (BaseResponse) response.body();
                        Log.d(TAG, baseResponse.toString());

                        if (baseResponse != null){
                            if (!baseResponse.isError()){
                                btnLogout.setText("LOGOUT");
                        }
                            Toast.makeText(getContext(), ""+baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "Gagal karena  "+t , Toast.LENGTH_SHORT).show();
                    }
                });
    }
}