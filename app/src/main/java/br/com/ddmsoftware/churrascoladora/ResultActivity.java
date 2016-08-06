package br.com.ddmsoftware.churrascoladora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ResultActivity extends AppCompatActivity {

    String message = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        AdView mAdView = (AdView) findViewById(R.id.adViewResult);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        String s_tag = "";
        Intent intent = getIntent();

        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Button btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnVoltar.setText("Voltar");

        btnVoltar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        TextView tv = (TextView)findViewById(R.id.tvResult);
        tv.setText(message);

        Button btnShare = (Button) findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                shareContent();
            }
        });
    }


    private void shareContent() {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Churrascoladora - A Calculadora do Churrasco");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(sharingIntent, "Compartilhar com..."));
    }
}
