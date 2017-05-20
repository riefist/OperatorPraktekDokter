package com.muhamadarief.operatorpraktek;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.muhamadarief.operatorpraktek.Model.Pendaftaran;
import com.muhamadarief.operatorpraktek.Model.User;
import com.muhamadarief.operatorpraktek.Utils.PrefUtil;
import com.muhamadarief.operatorpraktek.Utils.ViewPagerAdapter;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MainActivity";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    private String id_praktek;
    private User user;

    //This is our viewPager
    private ViewPager viewPager;


    //Fragments

    BerandaFragment berandaFragment;
    PendaftarFragment pendaftarFragment;
    ProfileFragment profileFragment;
    MenuItem prevMenuItem;

    private String[] title = {"Operator Praktek", "Daftar Pasien", "Profile"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(title[0]);

        user = PrefUtil.getUser(this, PrefUtil.USER_SESSION);

        Log.d(TAG, "nohp: "+user.getData().getNohp());

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        id_praktek = user.getData().getId_praktek();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                getSupportActionBar().setTitle(title[position]);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.action_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.action_data:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.action_profile:
                    viewPager.setCurrentItem(2);
                    break;
            }


        return true;
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        berandaFragment = BerandaFragment.newInstance(id_praktek);
        pendaftarFragment=PendaftarFragment.newInstance(id_praktek);
        profileFragment=ProfileFragment.newInstance(id_praktek);
        adapter.addFragment(berandaFragment, "Beranda");
        adapter.addFragment(pendaftarFragment, "Daftar Pasien");
        adapter.addFragment(profileFragment, "Profile");
        viewPager.setAdapter(adapter);
    }


}
