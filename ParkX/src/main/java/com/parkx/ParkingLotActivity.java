package com.parkx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParkingLotActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    TextView tvSpots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);
        setTitle(getString(R.string.parkinglot_title));

        database = FirebaseDatabase.getInstance();
        tvSpots = findViewById(R.id.tvSpots);
        loadSpots();

        Button btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(v -> {
            Toast.makeText(ParkingLotActivity.this, getString(R.string.parkinglot_helpmessage), Toast.LENGTH_SHORT).show();
        });
    }

    public void loadSpots() {
        DatabaseReference ref = database.getReference().child(getString(R.string.parkinglot_childspots));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvSpots.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.parkinglot_dialogbackmessage))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.parkinglot_dialogpositive), (dialog, which) -> {
                    finishAffinity();
                    System.exit(0);
                })
                .setNegativeButton(getString(R.string.parkinglot_dialognegative), (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.setTitle(getString(R.string.parkinglot_dialogbacktitle));
        dialog.setIcon(R.drawable.ic_alert);
        dialog.show();
    }
}