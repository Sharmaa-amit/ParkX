package com.parkx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
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
    private DatabaseReference ref;
    TextView tvSpots;
    String vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);
        setTitle(getString(R.string.parkinglot_title));

        database = FirebaseDatabase.getInstance();
        tvSpots = findViewById(R.id.tvSpots);
        loadSpots();
        loadVehicle();

        Button btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(v -> soundBuzzer(vehicle));
    }

    public void loadSpots() {
        ref = database.getReference().child(getString(R.string.parkinglot_childspots));

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

    public void loadVehicle() {
        ref = database.getReference().child(getString(R.string.parkinglot_childvehicle));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vehicle = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void soundBuzzer(String vehicleValue) {
        if (vehicleValue.equals(getString(R.string.parkinglot_true))) {
            Toast.makeText(ParkingLotActivity.this, getString(R.string.parkinglot_helpmessage), Toast.LENGTH_SHORT).show();
            ref = database.getReference().child(getString(R.string.parkinglog_childbuzzer));
            ref.setValue(getString(R.string.parkinglot_true));

            new Handler().postDelayed(() -> ref.setValue(getString(R.string.parkinglot_false)), 5000);
        } else
            Toast.makeText(ParkingLotActivity.this, getString(R.string.parkinglot_message), Toast.LENGTH_SHORT).show();
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