package com.imastudio.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateDeleteActivity extends AppCompatActivity {

    EditText edtNama, edtAsal;
    Button btnUpdate, btnDelete;

    public static String KEY_PERSON = "person";

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        edtNama = findViewById(R.id.edt_nama);
        edtAsal = findViewById(R.id.edt_asal);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        Peserta peserta = getIntent().getParcelableExtra(KEY_PERSON);
        String nama = peserta.getNama();
        String asal = peserta.getAsal();
        final String id = peserta.getId();

        edtNama.setText(nama);
        edtAsal.setText(asal);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Participants").child(id).removeValue();
                Toast.makeText(UpdateDeleteActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Peserta peserta = new Peserta();
                peserta.setNama(edtNama.getText().toString());
                peserta.setAsal(edtAsal.getText().toString());

                databaseReference.child("Participants").child(id).setValue(peserta).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateDeleteActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}