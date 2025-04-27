package com.petestudy.HT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.petestudy.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private boolean statsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switchToHome(View view) {
        hideStats();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void switchToShop(View view) {
        hideStats();
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    public void switchToTournament(View view) {
        hideStats();
        Intent intent = new Intent(this, TournamentMenuActivity.class);
        startActivity(intent);
    }

    public void switchToStats(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (statsVisible) {
            transaction.remove(fragmentManager.findFragmentById(R.id.fragmentContainer));
            statsVisible = false;
        } else {
            transaction.replace(R.id.fragmentContainer, new StatsFragment());
            statsVisible = true;
        }

        transaction.commit();
    }

    private void hideStats() {
        if (statsVisible) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragmentManager.findFragmentById(R.id.fragmentContainer));
            transaction.commit();
            statsVisible = false;
        }
    }
}