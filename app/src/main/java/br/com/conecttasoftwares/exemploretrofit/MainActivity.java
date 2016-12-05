package br.com.conecttasoftwares.exemploretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.conecttasoftwares.exemploretrofit.models.FilaPendentes;
import br.com.conecttasoftwares.exemploretrofit.models.Pendente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "pendentesErro";
    int total;
    private Button botao;
    private TextView textNome;
    private TextView textTOTAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNome = (TextView) findViewById(R.id.textNome);
        textTOTAL = (TextView) findViewById(R.id.textTotal);
        botao = (Button) findViewById(R.id.button);

        botao.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        total = 0;
        textNome.setText("");
        textTOTAL.setText("");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FilaPendentesService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FilaPendentesService service = retrofit.create(FilaPendentesService.class);
        Call<FilaPendentes> requestPendentes = service.ListPendentes();

        requestPendentes.enqueue(new Callback<FilaPendentes>() {
            @Override
            public void onResponse(Call<FilaPendentes> call, Response<FilaPendentes> response) {
                if (!response.isSuccessful()) {
                    Log.i("TAG", "ERRO: " + response.code());
                } else {
                    FilaPendentes filaPendentes = response.body();

                    for (Pendente p : filaPendentes.Pendentes) {

                        String operadora = String.format("%s: ", p.Operadora);
                        String quantidade = String.format("%s ", p.Quantidade);

                        total = total + Integer.parseInt(p.Quantidade);

                        textNome.append(operadora + quantidade + "\n");
                    }
                    textTOTAL.setText("Total: " + String.valueOf(total));
                }
            }

            @Override
            public void onFailure(Call<FilaPendentes> call, Throwable t) {
                Log.e(TAG, "Erro: " + t.getMessage());
            }
        });
    }
}