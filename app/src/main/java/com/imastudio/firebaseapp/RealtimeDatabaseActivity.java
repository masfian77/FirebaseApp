package com.imastudio.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RealtimeDatabaseActivity extends AppCompatActivity {

    EditText edtNama, edtAsal;
    Button btnSimpan;
    RecyclerView rvPeserta;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_database);

        edtNama = findViewById(R.id.edt_nama);
        edtAsal = findViewById(R.id.edt_asal);
        btnSimpan = findViewById(R.id.btn_create);

        rvPeserta = findViewById(R.id.rv_peserta);
        rvPeserta.setHasFixedSize(true);
        rvPeserta.setLayoutManager(new LinearLayoutManager(this));

        showPeserta();

        databaseReference = FirebaseDatabase.getInstance().getReference("Participants");

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = edtNama.getText().toString().trim();
                String asal = edtAsal.getText().toString().trim();

                if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(asal)) {
                    Toast.makeText(RealtimeDatabaseActivity.this, "Fill required", Toast.LENGTH_SHORT).show();
                }else {

                    Peserta peserta = new Peserta();
                    peserta.setNama(nama);
                    peserta.setAsal(asal);

                    String childID = databaseReference.push().getKey();

                    databaseReference.child(childID).setValue(peserta).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(RealtimeDatabaseActivity.this, "Create Success", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void showPeserta() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Participants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Peserta> pesertaArrayList = new ArrayList<>();

                for (DataSnapshot peserta : dataSnapshot.getChildren()){
                    Peserta dataPeserta = peserta.getValue(Peserta.class);
                    dataPeserta.setId(peserta.getKey());
                    pesertaArrayList.add(dataPeserta);
                }

                PesertaAdapter adapter = new PesertaAdapter(pesertaArrayList);
                adapter.notifyDataSetChanged();
                rvPeserta.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
