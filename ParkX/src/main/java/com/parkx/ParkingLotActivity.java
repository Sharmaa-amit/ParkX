package com.parkx;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ParkingLotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);
        setTitle(getString(R.string.parkinglot_title));
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