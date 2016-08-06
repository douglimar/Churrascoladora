package br.com.ddmsoftware.churrascoladora;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    // Cria uma variavel para fazer o transporte de valores
    // entre intents
    public static final String EXTRA_MESSAGE = new String ("br.com.ddmsoftware.churrascoladora.MESSAGE");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnCalcular = (Button) findViewById(R.id.btnCalcular);

        View.OnClickListener myClickableHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculaChurras();
            }
        };

        //TODO: Creditar o autor do Icone: <div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>


        btnCalcular.setOnClickListener(myClickableHandler);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // Load Advertisement Banner
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void calculaChurras() {


        /* CALCULO DA CARNE

        450 g de carne para cada homem
        300 g de carne para cada mulher
        150 g de carne para cada criança


        CALCULO DA BEBIDA
        - 600 ml de refrigerante por pessoa
        - 800 ml de cerveja por pessoa
        - 200 ml de água por pessoa
        - Vinho ou espumante se não houver cerveja – calcule ½ garrafa por pessoa
        - Vinho ou espumante se houver cerveja – calcule 1 garrafa para cada 5 pessoas - diminua a cerveja para 600 ml
        - Whisky – 1 garrafa para cada 50 pessoas
        - Coquetel de frutas – 4 litros para 50 pessoas

        */

        int iqtdHomens, iqtdMulheres, iqtdCriancas = 0;

        DecimalFormat df = new DecimalFormat("0");
        DecimalFormat df2 = new DecimalFormat("0,000");



        int iTotalGeral, iTotalCarneH, iTotalCarneM, iTotalCarneC = 0;
        float fMediaCarneBovina =0, fMediaCarneSuina=0, fMediaLinguica=0, fMediaFrango=0, fTotalGeral = 0;

        boolean bKilo = false;

        float fPercentualCarne = 0;
        float fSaldo = 0;
        float fMediaRestante = 0;

        EditText edtQtdHomens, edtQtdMulheres, edtQtdCriancas;
        CheckBox chkCarneBovina, chkCarneSuina, chkLinguica, chkFrango;

        edtQtdHomens = (EditText) findViewById(R.id.edtQtdeHomens);
        edtQtdMulheres = (EditText) findViewById(R.id.edtQtdeMulheres);
        edtQtdCriancas = (EditText) findViewById(R.id.edtQtdeCriancas);

        chkCarneBovina = (CheckBox) findViewById(R.id.chkCarneBovina);
        chkCarneSuina = (CheckBox) findViewById(R.id.chkCarneSuica);
        chkFrango = (CheckBox) findViewById(R.id.chkFrango);
        chkLinguica = (CheckBox) findViewById(R.id.chkLinguica);

        if ( (edtQtdHomens.getText().length() == 0) || (edtQtdMulheres.getText().length()==0) || (edtQtdCriancas.getText().length()==0) ) {
            Toast.makeText(this, "Preencha as quantidades de Pessoas que irão ao seu churrasco", Toast.LENGTH_SHORT).show();

        } else    if (chkCarneBovina.isChecked()==false && (chkCarneSuina.isChecked()==false && (chkFrango.isChecked()==false && (chkLinguica.isChecked()==false)))) {
            Toast.makeText(this, "Selecione pelo menos um tipo de carne antes de continuar", Toast.LENGTH_SHORT).show();

        } else {

            iqtdHomens = Integer.parseInt(edtQtdHomens.getText().toString());
            iqtdMulheres = Integer.parseInt(edtQtdMulheres.getText().toString());
            iqtdCriancas = Integer.parseInt(edtQtdCriancas.getText().toString());

            iTotalCarneH = iqtdHomens * 450;
            iTotalCarneM = iqtdMulheres * 300;
            iTotalCarneC = iqtdCriancas * 150;

            iTotalGeral = (iTotalCarneH + iTotalCarneM + iTotalCarneC);

            // Valida se todos as carnes foram clicadas
            if (chkCarneBovina.isChecked() && (chkCarneSuina.isChecked() && (chkFrango.isChecked() && (chkLinguica.isChecked())))) {
                bKilo = false;

                fPercentualCarne = iTotalGeral / 3;

                fSaldo = iTotalGeral - fPercentualCarne;
                fMediaRestante = fSaldo / 3;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = fPercentualCarne;

                fMediaCarneSuina = fMediaRestante;
                fMediaFrango = fMediaRestante;
                fMediaLinguica = fMediaRestante;

            } else if (chkCarneBovina.isChecked() && (chkCarneSuina.isChecked() && (chkFrango.isChecked() && (chkLinguica.isChecked() == false)))) {

                bKilo = false;

                fPercentualCarne = iTotalGeral / 2;

                fSaldo = iTotalGeral - fPercentualCarne;
                fMediaRestante = fSaldo / 2;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = fPercentualCarne;

                fMediaCarneSuina = fMediaRestante;
                fMediaFrango = fMediaRestante;

            } else if (chkCarneBovina.isChecked() && (chkCarneSuina.isChecked() && (chkFrango.isChecked() == false && (chkLinguica.isChecked() == false)))) {

                bKilo = false;

                fPercentualCarne = (iTotalGeral * 60) / 100;

                fSaldo = iTotalGeral - fPercentualCarne;
                fMediaRestante = fSaldo;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = fPercentualCarne;
                fMediaCarneSuina = fMediaRestante;

            } else if (chkCarneBovina.isChecked() && (chkCarneSuina.isChecked() == false && (chkFrango.isChecked() == false && (chkLinguica.isChecked() == false)))) {

                bKilo = false;

                fPercentualCarne = iTotalGeral;

                fSaldo = 0;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = fPercentualCarne;

            } else if (chkCarneBovina.isChecked() == false && (chkCarneSuina.isChecked() && (chkFrango.isChecked() && (chkLinguica.isChecked())))) {

                bKilo = false;

                fMediaRestante = iTotalGeral / 3;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = 0;

                fMediaCarneSuina = fMediaRestante;
                fMediaFrango = fMediaRestante;
                fMediaLinguica = fMediaRestante;
            } else if (chkCarneBovina.isChecked() == false && (chkCarneSuina.isChecked() == false && (chkFrango.isChecked() && (chkLinguica.isChecked())))) {

                bKilo = false;

                fMediaRestante = iTotalGeral / 2;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = 0;

                fMediaCarneSuina = 0;
                fMediaFrango = fMediaRestante;
                fMediaLinguica = fMediaRestante;
            } else if (chkCarneBovina.isChecked() == false && (chkCarneSuina.isChecked() == false && (chkFrango.isChecked() == false && (chkLinguica.isChecked())))) {

                bKilo = false;

                fMediaRestante = iTotalGeral;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = 0;

                fMediaCarneSuina = 0;
                fMediaFrango = 0;
                fMediaLinguica = fMediaRestante;
            } else if (chkCarneBovina.isChecked() == false && (chkCarneSuina.isChecked() == false && (chkFrango.isChecked() == false && (chkLinguica.isChecked())))) {

                bKilo = false;

                fMediaRestante = iTotalGeral;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = 0;

                fMediaCarneSuina = 0;
                fMediaFrango = 0;
                fMediaLinguica = fMediaRestante;
            } else if (chkCarneBovina.isChecked() && (chkCarneSuina.isChecked() == false && (chkFrango.isChecked() == false && (chkLinguica.isChecked())))) {

                bKilo = false;

                fPercentualCarne = (iTotalGeral * 60) / 100;

                fSaldo = iTotalGeral - fPercentualCarne;
                fMediaRestante = fSaldo;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = fPercentualCarne;
                fMediaLinguica = fMediaRestante;
            } else if (chkCarneBovina.isChecked() && (chkCarneSuina.isChecked() == false && (chkFrango.isChecked() && (chkLinguica.isChecked() == false)))) {

                bKilo = false;

                fPercentualCarne = (iTotalGeral * 60) / 100;

                fSaldo = iTotalGeral - fPercentualCarne;
                fMediaRestante = fSaldo;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = fPercentualCarne;
                fMediaFrango = fMediaRestante;

            } else if (chkCarneBovina.isChecked() && (chkCarneSuina.isChecked() && (chkFrango.isChecked() == false && (chkLinguica.isChecked())))) {

                bKilo = false;

                fPercentualCarne = iTotalGeral / 2;

                fSaldo = iTotalGeral - fPercentualCarne;
                fMediaRestante = fSaldo / 2;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = fPercentualCarne;
                fMediaCarneSuina = fMediaRestante;
                fMediaLinguica = fMediaRestante;

            } else if (chkCarneBovina.isChecked() && (chkCarneSuina.isChecked() == false && (chkFrango.isChecked() && (chkLinguica.isChecked())))) {

                bKilo = false;

                fPercentualCarne = iTotalGeral / 2;

                fSaldo = iTotalGeral - fPercentualCarne;
                fMediaRestante = fSaldo / 2;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = fPercentualCarne;
                fMediaFrango = fMediaRestante;
                fMediaLinguica = fMediaRestante;

            } else if (chkCarneBovina.isChecked() == false && (chkCarneSuina.isChecked() && (chkFrango.isChecked() == false && (chkLinguica.isChecked() == false)))) {

                bKilo = false;

                fMediaRestante = iTotalGeral;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = 0;

                fMediaCarneSuina = fMediaRestante;
                fMediaFrango = 0;
                fMediaLinguica = 0;
            } else if (chkCarneBovina.isChecked() == false && (chkCarneSuina.isChecked() == false && (chkFrango.isChecked() && (chkLinguica.isChecked() == false)))) {

                bKilo = false;

                fMediaRestante = iTotalGeral;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = 0;

                fMediaCarneSuina = 0;
                fMediaFrango = fMediaRestante;
                fMediaLinguica = 0;
            } else if (chkCarneBovina.isChecked() == false && (chkCarneSuina.isChecked() && (chkFrango.isChecked() && (chkLinguica.isChecked() == false)))) {

                bKilo = false;

                fMediaRestante = iTotalGeral / 2;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = 0;

                fMediaCarneSuina = fMediaRestante;
                fMediaFrango = fMediaRestante;
                fMediaLinguica = 0;
            } else if (chkCarneBovina.isChecked() == false && (chkCarneSuina.isChecked() && (chkFrango.isChecked() == false && (chkLinguica.isChecked())))) {

                bKilo = false;

                fMediaRestante = iTotalGeral / 2;

                if ((fMediaRestante >= 1000) || (fMediaCarneBovina >= 1000 && fMediaRestante == 0)) {
                    bKilo = true;
                }
                fMediaCarneBovina = 0;

                fMediaCarneSuina = fMediaRestante;
                fMediaFrango = 0;
                fMediaLinguica = fMediaRestante;
            }

            Intent intent = new Intent(this, ResultActivity.class);
            String message = "Sugestão de quantidade de Carnes para seu churrasco:\n";

            if (bKilo == true) {

                message = message +
                        "\n- Carne Bovina.....: " + df2.format(fMediaCarneBovina) + " Kg" +
                        "\n- Carne Suina.......: " + df2.format(fMediaCarneSuina) + " Kg" +
                        "\n- Carne Frango.....: " + df2.format(fMediaFrango) + " Kg" +
                        "\n- Linguica.............: " + df2.format(fMediaLinguica) + " Kg";

                //Toast.makeText(this, message , Toast.LENGTH_LONG).show();

            } else {
                message = message +
                        "\n- Carne Bovina.....:  " + df.format(fMediaCarneBovina) + " Gramas" +
                        "\n- Carne Suina.......: " + df.format(fMediaCarneSuina) + " Gramas" +
                        "\n- Carne Frango.....: " + df.format(fMediaFrango) + " Gramas" +
                        "\n- Linguica.............: " + df.format(fMediaLinguica) + " Gramas";

                //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }

            message = message + "\n\nSugestões de Acompanhamentos: " +
                    "Arroz, Salada Verde, Farofa, Maionese, Pão, Pão de Alho, entre outros ao seu gosto.";


            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }



    }

    private String formataMilhar(int pValor) {

        String sValor = String.valueOf(pValor);

        if (sValor.length() > 3) {

            sValor = sValor.substring(0, 1) + "." + sValor.substring(1, sValor.length()) + " Kg. ";
        } else
            sValor = sValor + " Gramas";


        return sValor;


    }

    private String formataMilhar(float pValor) {

        String sValor = String.valueOf(pValor);

        if (sValor.length() > 3) {

            sValor = sValor.substring(0, 1) + "." + sValor.substring(1, sValor.length()) + " Kg. ";
        } else
            sValor = sValor + " Gramas";


        return sValor;


    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.ddmsoftware.churrascoladora/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.ddmsoftware.churrascoladora/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
