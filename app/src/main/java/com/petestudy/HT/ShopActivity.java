package com.petestudy.HT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.petestudy.myapplication.R;

import java.util.List;

public class ShopActivity extends AppCompatActivity {
    private TextView coinText;
    private RecyclerView shopRecyclerView;
    private List<Lutemon> shopLutemons;
    private LutemonAdapter adapter;
    private int selectedPosition = -1;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        player= GameManager.getInstance().getPlayer();
        coinText = findViewById(R.id.textPlayerCoins);
        shopRecyclerView = findViewById(R.id.recyclerViewShop);
        shopLutemons = Shop.getInstance().getAvailableLutemons();

        adapter = new LutemonAdapter(this, shopLutemons, position -> selectedPosition = position);
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopRecyclerView.setAdapter(adapter);

        updateCoins();
    }

    public void onBuySelected(View view) {
        if (selectedPosition >= 0 && selectedPosition < shopLutemons.size()) {
            Lutemon lutemon = shopLutemons.get(selectedPosition);
            if (player.buyLutemon(lutemon)) {
                shopLutemons.remove(selectedPosition);
                adapter.notifyItemRemoved(selectedPosition);
                selectedPosition = -1;
                Toast.makeText(this, "Ostettiin: " + lutemon.getName(), Toast.LENGTH_SHORT).show();
                updateCoins();
            } else {
                Toast.makeText(this, "Ei tarpeeksi poletteja!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateCoins() {
        coinText.setText("Poletit: " + player.getCoins());
    }

    public void switchToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
