package br.com.jrsoft.palpitero.ui.telas;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.jrsoft.palpitero.R;
import br.com.jrsoft.palpitero.dao.ApostaDAO;
import br.com.jrsoft.palpitero.model.ModalidadeLoteria;
import br.com.jrsoft.palpitero.ui.adapter.ListaJogosAdapter;
import br.com.jrsoft.palpitero.util.LoteriaUtils;
import br.com.jrsoft.palpitero.util.RetrofitClient;
import br.com.jrsoft.palpitero.util.SorteioApi;
import br.com.jrsoft.palpitero.util.SorteioResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscolhaJogoActivity extends AppCompatActivity {

    private SorteioApi sorteioApi;
    private List<SorteioResponse> proximosSorteios = new ArrayList<>();
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1; // Código de requisição para a permissão

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha_jogo);
        inicializaMobileAds();
        setTitle("PALPITERO!!!");
        configuraLista();
        verificarPermissaoNotificacao();

        sorteioApi = RetrofitClient.getClient().create(SorteioApi.class);

    }

    private void verificarPermissaoNotificacao() {
        // Para Android 13 (API 33) ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            // Para versões anteriores do Android, verifique se o serviço de notificações está ativado
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (!notificationManager.areNotificationsEnabled()) {
                    Toast.makeText(this, "Ative as notificações para receber mensagens do PALPITERO.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão para notificações concedida!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão para notificações negada. Você não receberá alertas.", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_escolha_jogo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_proximos_sorteios) {
            exibirPopupProximosSorteios();
            return true;
        } else if (item.getItemId() == R.id.menu_exoneracao){
            exibirPopupExoneracao();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exibirPopupExoneracao() {
        // Cria e exibe o popup de exoneração de responsabilidade
        new AlertDialog.Builder(this)
                .setTitle("Exoneração de Responsabilidade")
                .setMessage("Este aplicativo é um gerador de palpites para loterias e não possui qualquer afiliação com a Caixa Econômica Federal ou com qualquer órgão governamental. Ele não garante prêmios, e as informações aqui disponibilizadas são para fins de entretenimento.")
                .setPositiveButton("Fechar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void exibirPopupProximosSorteios() {
        proximosSorteios.clear();

        for (String modalidade : LoteriaUtils.getModalidades()) {
            Call<SorteioResponse> call = sorteioApi.obterUltimoSorteio(modalidade);
            call.enqueue(new Callback<SorteioResponse>() {
                @Override
                public void onResponse(Call<SorteioResponse> call, Response<SorteioResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        proximosSorteios.add(response.body());
                    }
                    if (proximosSorteios.size() == LoteriaUtils.getModalidades().size()) {
                        mostrarPopup(proximosSorteios);
                    }
                }

                @Override
                public void onFailure(Call<SorteioResponse> call, Throwable t) {
                    // Trate o erro, por exemplo, exibir uma mensagem de erro
                }
            });
        }
    }

    private void mostrarPopup(List<SorteioResponse> proximosSorteios) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_proximos_sorteios, null);
        //popupView.setBackgroundResource(R.drawable.popup_background);
        LinearLayout conteudoPopup = popupView.findViewById(R.id.conteudo_popup);


        for (SorteioResponse sorteio : proximosSorteios) {
            TextView infoSorteio = new TextView(this);

            // Formatar o valor do prêmio
            NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            String premioFormatado = formatador.format(sorteio.getValorEstimadoProximoConcurso());

            // Construir o texto para exibição
            String texto = sorteio.getTipoJogo() + "\nSorteio " + sorteio.getNumeroConcursoProximo() +
                    " - " + sorteio.getDataProximoConcurso() + "\nPrêmio Estimado: " + premioFormatado;
            infoSorteio.setText(texto);
            infoSorteio.setPadding(8, 8, 8, 8);

            // Definir a cor com base no status de acumulação
            if (sorteio.isAcumulado()) {
                infoSorteio.setTextColor(getResources().getColor(R.color.verde_acumulado));
            } else {
                infoSorteio.setTextColor(getResources().getColor(R.color.azul_nao_acumulado));
            }

            conteudoPopup.addView(infoSorteio);
        }

        View legendaView = getLayoutInflater().inflate(R.layout.legenda_acumulacao, null);
        conteudoPopup.addView(legendaView);


        new AlertDialog.Builder(this)
                .setView(popupView)
                .setPositiveButton("Fechar", (dialog, id) -> dialog.dismiss())
                .show();
    }

    private void inicializaMobileAds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    private void configuraLista() {
        ListView listaDeJogos = findViewById(R.id.escolha_jogo_listview);
        final List<ModalidadeLoteria> apostas = new ApostaDAO().lista();
        listaDeJogos.setAdapter(new ListaJogosAdapter(apostas, this));
        listaDeJogos.setOnItemClickListener((adapterView, view, posicao, id) -> {
            ModalidadeLoteria apostaClicada = apostas.get(posicao);
            vaiParaGeraPalpite(apostaClicada);
        });
    }

    private void vaiParaGeraPalpite(ModalidadeLoteria aposta) {
        Intent intent = new Intent(EscolhaJogoActivity.this, GeraPalpiteActivity.class);
        intent.putExtra("aposta", aposta);
        startActivity(intent);
    }
}
