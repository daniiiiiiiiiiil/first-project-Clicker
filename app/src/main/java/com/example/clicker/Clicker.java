package com.example.clicker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Clicker extends AppCompatActivity {

    private ImageButton coin;
    private TextView balanc;
    private TextView price_pumping;
    private TextView income;
    private Button upgrade;
    private TextView Name;
    private int counter = 0;
    private double incomePerClick = 1.0;
    private double totalIncome = 0.0;
    private double upgradeCost = 10.0;
    private double upgradeMultiplier = 0.5;
    private MediaPlayer mediaPlayer;
    private Switch musicSwitch;
    private boolean isMusicPlaying = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicker);

        coin = findViewById(R.id.coin);
        balanc = findViewById(R.id.balanc);
        price_pumping = findViewById(R.id.price_pumping);
        income = findViewById(R.id.income);
        upgrade = findViewById(R.id.upgrade);
        musicSwitch = findViewById(R.id.musicSwitch);
        mediaPlayer = MediaPlayer.create(this, R.raw.music);

        coin.setOnClickListener(v -> {
            counter++;
            totalIncome += incomePerClick;
            balanc.setText("Текущий баланс: " + String.format("%.2f", totalIncome));
        });

        Toast.makeText(this,"Цена прокачки: " + upgradeCost,Toast.LENGTH_LONG).show();
        upgrade.setOnClickListener(v -> {
            if (totalIncome >= upgradeCost) {
                totalIncome -= upgradeCost;
                incomePerClick += upgradeMultiplier;
                upgradeCost *= 1.5;
                price_pumping.setText("Цена прокачки: "+ String.format("%.2f",upgradeCost));
                income.setText("Цена прокачки: "+ String.format("%.2f",incomePerClick));
                upgradeMultiplier *= 1.1;
                Toast.makeText(this, "Доход увеличен на " + upgradeMultiplier, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Недостаточно денег!", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("plainText")) {
            String plainText = intent.getStringExtra("plainText");
            Name.setText(plainText);
        }
        musicSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startMusic();
            } else {
                stopMusic();
            }
        });
    }

    private void startMusic() {
        if (!isMusicPlaying) {
            mediaPlayer.start();
            isMusicPlaying = true;
        }
    }

    private void stopMusic() {
        if (isMusicPlaying) {
            mediaPlayer.pause();
            isMusicPlaying = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
