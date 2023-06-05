package com.example.input_nytb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FloatingActionButton fab =findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingInflatedId")
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.add, null);

                TextInputLayout titlelayout, nadalayout, baitlayout, koorlayout, nolayout;
                titlelayout = view1.findViewById(R.id.edtitlelayout);
                nadalayout = view1.findViewById(R.id.ednadalayout);
                baitlayout = view1.findViewById(R.id.edbaitlayout);
                koorlayout = view1.findViewById(R.id.edkoorlayout);
                nolayout = view1.findViewById(R.id.ednolayout);

                TextInputEditText edtitle, ednada, edbait, edkoor, edno;
                edtitle = view1.findViewById(R.id.edtitle);
                ednada = view1.findViewById(R.id.ednada);
                edbait = view1.findViewById(R.id.edbait);
                edkoor = view1.findViewById(R.id.edkoor);
                edno = view1.findViewById(R.id.edno);

                AlertDialog ad = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("tambah data nytb")
                        .setView(view1).setPositiveButton("tambah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (edtitle.getText().toString().isEmpty()){
                                    titlelayout.setError("Harus Diisi ya bro");
                                } else if (ednada.getText().toString().isEmpty()) {
                                    nadalayout.setError("Harus diisi ya bro");
                                } else if (edbait.getText().toString().isEmpty()) {
                                    baitlayout.setError("Harus diisi ya bro");
                                } else if (edkoor.getText().toString().isEmpty()) {
                                    koorlayout.setError("Harus diisi ya bro");
                                } else if (edno.getText().toString().isEmpty()) {
                                    nolayout.setError("Harus diisi ya bro");
                                }else {
                                    ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                                    progressDialog.setMessage("Menyimpan...");
                                    progressDialog.show();
                                    nytb nytb =new nytb();
                                    nytb.setTitle(edtitle.getText().toString());
                                    nytb.setNada(ednada.getText().toString());
                                    nytb.setBait(edbait.getText().toString());
                                    nytb.setKoor(edkoor.getText().toString());
                                    nytb.setNo(edno.getText().toString());
                                    database.getReference().child("nytb").push().setValue(nytb)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    progressDialog.dismiss();
                                                    dialogInterface.dismiss();
                                                    Toast.makeText(MainActivity.this, "Tersimpan", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(MainActivity.this, "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create();
                ad.show();


            }
        });

        TextView empty = findViewById(R.id.empty);
        RecyclerView recyclerView  =findViewById(R.id.recyclerView);
        LinearLayoutManager lm =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        database.getReference().child("nytb").orderByChild("key").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<nytb> nytbArrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    nytb n = dataSnapshot.getValue(nytb.class);
                    Objects.requireNonNull(n).setKey(dataSnapshot.getKey());
                    nytbArrayList.add(n);
                }

                if (nytbArrayList.isEmpty()){
                    empty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else {
                    empty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                NAdapter adapter = new NAdapter(MainActivity.this, nytbArrayList);

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                adapter.setItemClick(new NAdapter.ItemClick() {
                    @SuppressLint("MissingInflatedId")
                    @Override
                    public void OnClick(nytb nytb) {
                        View m =LayoutInflater.from(MainActivity.this).inflate(R.layout.add, null);

                        TextInputLayout titlelayout, nadalayout, baitlayout, koorlayout, nolayout;
                        titlelayout = m.findViewById(R.id.edtitlelayout);
                        nadalayout = m.findViewById(R.id.ednadalayout);
                        baitlayout = m.findViewById(R.id.edbaitlayout);
                        koorlayout = m.findViewById(R.id.edkoorlayout);
                        nolayout = m.findViewById(R.id.ednolayout);

                        TextInputEditText edtitle1, ednada1, edbait1, edkoor1, edno1;
                        edtitle1 = m.findViewById(R.id.edtitle);
                        ednada1 = m.findViewById(R.id.ednada);
                        edbait1 = m.findViewById(R.id.edbait);
                        edkoor1 = m.findViewById(R.id.edkoor);
                        edno1 = m.findViewById(R.id.edno);

                        edtitle1.setText(nytb.getTitle());
                        ednada1.setText(nytb.getNada());
                        edbait1.setText(nytb.getBait());
                        edkoor1.setText(nytb.getKoor());
                        edno1.setText(nytb.getNo());

                        ProgressDialog pm =new ProgressDialog(MainActivity.this);

                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Edit & Delete")
                                .setView(m)
                                .setPositiveButton("edit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (edtitle1.getText().toString().isEmpty()){
                                            titlelayout.setError("Harus Diisi Ya Bro");
                                        } else if (ednada1.getText().toString().isEmpty()) {
                                            nadalayout.setError("Harus Diisi Ya Bro");
                                        } else if (edbait1.getText().toString().isEmpty()) {
                                            baitlayout.setError("Harus Diisi Ya Bro");
                                        } else if (edkoor1.getText().toString().isEmpty()) {
                                            koorlayout.setError("Harus Diisi Ya Bro");
                                        } else if (edno1.getText().toString().isEmpty()) {
                                            nolayout.setError("Harus Diisi Ya Bro");
                                        } else {
                                            pm.setMessage("Update NYTB...");
                                            pm.show();
                                            nytb enyetebe = new nytb();
                                            enyetebe.setTitle(edtitle1.getText().toString());
                                            enyetebe.setNada(ednada1.getText().toString());
                                            enyetebe.setBait(edbait1.getText().toString());
                                            enyetebe.setKoor(edkoor1.getText().toString());
                                            enyetebe.setNo(edno1.getText().toString());
                                            database.getReference().child("nytb").child(nytb.getKey())
                                                    .setValue(enyetebe)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            pm.dismiss();
                                                            dialogInterface.dismiss();
                                                            Toast.makeText(MainActivity.this, "Berhasil Update", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            pm.dismiss();
                                                            Toast.makeText(MainActivity.this, "Gagal Update", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    }
                                }).setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        pm.setTitle("Menghapus...");
                                        pm.show();
                                        database.getReference().child("nytb").child(nytb.getKey())
                                                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        pm.dismiss();
                                                        Toast.makeText(MainActivity.this, "Berhasil Hapus", Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        pm.dismiss();
                                                        Toast.makeText(MainActivity.this, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }).create();
                        alertDialog.show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}