package com.muhamadarief.operatorpraktek.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muhamadarief.operatorpraktek.Model.Pendaftaran;
import com.muhamadarief.operatorpraktek.R;

import java.util.ArrayList;

/**
 * Created by Muhamad Arief on 26/04/2017.
 */

public class PendaftaranAdapter extends RecyclerView.Adapter<PendaftaranAdapter.HolderItem> {

    private ArrayList<Pendaftaran> pendaftarans;
    private Context context;
    private Pendaftaran pendaftaran;

    public PendaftaranAdapter(Context context, ArrayList<Pendaftaran> pendaftarans){
        this.pendaftarans = pendaftarans;
        this.context = context;
    }

    @Override
    public HolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_row_item, parent, false);

        HolderItem holderItem = new HolderItem(view);
        return holderItem;
    }

    @Override
    public void onBindViewHolder(HolderItem holder, int position) {
        holder.txt_no_antrian.setText(pendaftarans.get(position).getNo_antrian());
        holder.txt_nama_pasien.setText(pendaftarans.get(position).getNama());
        holder.txt_alamat_pasien.setText(pendaftarans.get(position).getAlamat());
        holder.txt_nohp_pasien.setText(pendaftarans.get(position).getNohp());
        holder.txt_status.setText(pendaftarans.get(position).getStatus());
        if (pendaftarans.get(position).getStatus().equals("Pernah Mendaftar")){
            holder.txt_status.setTextColor(ContextCompat.getColor(context,R.color.colorOnline));
        } else {
            holder.txt_status.setTextColor(ContextCompat.getColor(context,R.color.colorOffline));
        }

    }

    @Override
    public int getItemCount() {
        return pendaftarans.size();
    }


    public class HolderItem extends RecyclerView.ViewHolder{

        public TextView txt_no_antrian,
        txt_nama_pasien, txt_alamat_pasien, txt_nohp_pasien, txt_status;
        public CardView card_item;


        public HolderItem(View itemView) {
            super(itemView);

            txt_no_antrian = (TextView)itemView.findViewById(R.id.txt_no_antrian);
            txt_nama_pasien = (TextView)itemView.findViewById(R.id.txt_nama_pasien);
            txt_alamat_pasien = (TextView)itemView.findViewById(R.id.txt_alamat_pasien);
            txt_nohp_pasien = (TextView)itemView.findViewById(R.id.txt_nohp_pasien);
            card_item = (CardView)itemView.findViewById(R.id.card_item);
            txt_status = (TextView)itemView.findViewById(R.id.txt_status);

        }
    }
}
