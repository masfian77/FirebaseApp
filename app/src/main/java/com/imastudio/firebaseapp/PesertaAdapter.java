package com.imastudio.firebaseapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PesertaAdapter extends RecyclerView.Adapter<PesertaAdapter.ViewHolder> {

    List<Peserta> listPerson;

    public PesertaAdapter(List<Peserta> listPerson) {
        this.listPerson = listPerson;
    }

    @NonNull
    @Override
    public PesertaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peserta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PesertaAdapter.ViewHolder holder, final int position) {

        holder.txtNama.setText(listPerson.get(position).getNama());
        holder.txtAsal.setText(listPerson.get(position).getAsal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // todo siapkan data untuk dikirim
                Peserta person = new Peserta();
                person.setId(listPerson.get(position).getId());
                person.setNama(listPerson.get(position).getNama());
                person.setAsal(listPerson.get(position).getAsal());

                // todo kirim data dengan parcelable
                Intent intent = new Intent(holder.itemView.getContext(), UpdateDeleteActivity.class);
                intent.putExtra(UpdateDeleteActivity.KEY_PERSON, person);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPerson.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNama, txtAsal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNama = itemView.findViewById(R.id.tv_nama);
            txtAsal = itemView.findViewById(R.id.tv_asal);
        }
    }
}