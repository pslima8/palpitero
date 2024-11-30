package br.com.jrsoft.palpitero.ui.telas;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.jrsoft.palpitero.R;
import br.com.jrsoft.palpitero.model.DiaDeSorte;
import br.com.jrsoft.palpitero.model.Loteca;
import br.com.jrsoft.palpitero.model.ModalidadeLoteria;
import br.com.jrsoft.palpitero.model.SuperSete;
import br.com.jrsoft.palpitero.model.Timemania;
import br.com.jrsoft.palpitero.util.ResourcesUtil;

public class GeraPalpiteActivity extends AppCompatActivity {

    private int total_de_dezenas;
    private Spinner sp;
    private int quantidade_de_dezenas;
    private int aposta_maxima;
    private int aposta_minima;
    private ModalidadeLoteria aposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gera_palpite);
        setTitle("Gerar palpite");
        geraBanner();
        CarregaApostaRecebida();
        inicializaTotal_Dezenas_E_Maximo_E_Minimo();
        configuraSpinner();
        configuraBotao();
    }

    private void geraBanner() {
        AdView adView = findViewById(R.id.gera_palpite_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void configuraSpinner() {
        sp = findViewById(R.id.gera_palpite_spinner);
        if (aposta_maxima == aposta_minima) {
            sp.setVisibility(View.INVISIBLE);
            quantidade_de_dezenas = aposta_minima;
        } else {
            sp.setVisibility(View.VISIBLE);
            List<Integer> dezenas = geraListaSpinner();
            configuraAdapterSpinner(dezenas);
        }
    }

    @NotNull
    private List<Integer> geraListaSpinner() {
        List<Integer> dezenas = new ArrayList<>();
        for (int i = aposta_minima; i <= aposta_maxima; i++) {
            dezenas.add(i);
        }
        return dezenas;
    }

    private void configuraAdapterSpinner(List<Integer> dezenas) {
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dezenas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quantidade_de_dezenas = dezenas.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void CarregaApostaRecebida() {
        Intent intent = getIntent();
        if (intent.hasExtra("aposta")) {
            aposta = intent.getParcelableExtra("aposta");
        }
        inicializaCampos(aposta);
    }

    private void inicializaTotal_Dezenas_E_Maximo_E_Minimo() {
        aposta_minima = aposta.getApostaMinima();
        aposta_maxima = aposta.getApostaMaxima();
        total_de_dezenas = aposta.getTotalDezenas();
    }

    private void configuraBotao() {
        Button botaoGerarPalpite = findViewById(R.id.gera_palpite_botao);
        botaoGerarPalpite.setOnClickListener(view -> preencheCampoResultado());
    }

    private void preencheCampoResultado() {
        // Gera os palpites
        List<List<Integer>> palpites = aposta.gerarPalpite(quantidade_de_dezenas);

        // Limpa o layout de resultados
        LinearLayout resultadoLayout = findViewById(R.id.resultado_layout);
        resultadoLayout.removeAllViews();
        if (aposta instanceof SuperSete) {
            exibePalpiteSuperSete(palpites, resultadoLayout);

        // Verifica se a apósta é Dia de Sorte
        } else if (aposta instanceof DiaDeSorte) {
            diaDeSorte(resultadoLayout);
            // Verifica se a apósta é Loteca
        } else if (aposta instanceof Loteca) {
            List<String> palpitesLoteca = ((Loteca) aposta).gerarPalpiteLoteca();
            exibePalpiteLoteca(palpitesLoteca, resultadoLayout);
        // Verifica se a aposta é Timemania
        } else if (aposta instanceof Timemania) {
            timemania(palpites, resultadoLayout);

        } else {
            // Para modalidades que não são Timemania, exibe múltiplos sorteios, se houver
            if (palpites.size() == 1) {
                exibePalpiteUnico(palpites.get(0), resultadoLayout); // Apenas um sorteio
            } else {
                exibePalpitesMultiplos(palpites, resultadoLayout); // Múltiplos sorteios
            }
        }
    }

    private void diaDeSorte(LinearLayout resultadoLayout) {
        // Gera e exibe o palpite específico para Dia de Sorte
        List<List<Integer>> palpitesDiaDeSorte = aposta.gerarPalpite(quantidade_de_dezenas);
        // Exibe as dezenas sorteadas
        exibePalpiteUnico(palpitesDiaDeSorte.get(0), resultadoLayout);
        // Obtém o índice do mês e exibe o "Mês da Sorte"
        int indiceMes = palpitesDiaDeSorte.get(1).get(0);
        String nomeMes = ((DiaDeSorte) aposta).getMesDaSorte(indiceMes);
        TextView mesView = new TextView(this);
        mesView.setText("Mês da Sorte: " + nomeMes);
        mesView.setTextSize(30);
        mesView.setTextColor(Color.BLUE);
        mesView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        mesView.setPadding(0, 16, 0, 16);
        resultadoLayout.addView(mesView);
    }

    private void timemania(List<List<Integer>> palpites, LinearLayout resultadoLayout) {
        // Exibe apenas o primeiro palpite (dezenas sorteadas)
        exibePalpiteUnico(palpites.get(0), resultadoLayout);

        // Obtém o nome do Time do Coração e exibe
        int numeroTime = palpites.get(1).get(0); // Número do Time do Coração
        String nomeTime = ((Timemania) aposta).getNomeDoTime(numeroTime);

        TextView timeView = new TextView(this);
        timeView.setText("Time do Coração: " + nomeTime);
        timeView.setTextSize(30);
        timeView.setTextColor(Color.BLUE);
        timeView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        timeView.setPadding(0, 16, 0, 16);
        resultadoLayout.addView(timeView);
    }

    private void exibePalpiteSuperSete(List<List<Integer>> palpites, LinearLayout layout) {
        for (int coluna = 0; coluna < palpites.size(); coluna++) {
            TextView colunaView = new TextView(this);
            colunaView.setText("Coluna " + (coluna + 1) + ": " + palpites.get(coluna));
            colunaView.setTextSize(30);
            colunaView.setTextColor(Color.BLUE);
            colunaView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            colunaView.setPadding(0, 8, 0, 8);
            layout.addView(colunaView);
        }
    }

    private void exibePalpiteLoteca(List<String> palpites, LinearLayout layout) {
        for (int i = 0; i < palpites.size(); i++) {
            TextView jogoView = new TextView(this);
            jogoView.setText("Jogo " + (i + 1) + ": " + palpites.get(i));
            jogoView.setTextSize(30);
            jogoView.setTextColor(Color.BLUE);
            jogoView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            jogoView.setPadding(0, 8, 0, 8);
            layout.addView(jogoView);
        }
    }

    private void exibePalpiteUnico(List<Integer> numeros, LinearLayout layout) {
        TextView resultadoView = new TextView(this);
        resultadoView.setText(formatarPalpiteUnico(numeros));
        resultadoView.setTextSize(30);
        resultadoView.setTextColor(Color.BLUE);
        resultadoView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        resultadoView.setPadding(0, 16, 0, 16);
        layout.addView(resultadoView);
    }

    private void exibePalpitesMultiplos(List<List<Integer>> palpites, LinearLayout layout) {
        for (int i = 0; i < palpites.size(); i++) {
            TextView sorteioView = new TextView(this);
            sorteioView.setText(formatarPalpiteMultiplo(palpites.get(i), i + 1));
            sorteioView.setTextSize(30);
            sorteioView.setTextColor(Color.BLUE);
            sorteioView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            sorteioView.setPadding(0, 16, 0, 16);
            layout.addView(sorteioView);
        }
    }

    private String formatarPalpiteUnico(List<Integer> numeros) {
        StringBuilder builder = new StringBuilder();
        builder.append("Palpite: ");
        for (Integer numero : numeros) {
            builder.append(numero).append(", ");
        }
        return builder.toString();
    }

    private String formatarPalpiteMultiplo(List<Integer> numeros, int sorteioNumero) {
        StringBuilder builder = new StringBuilder();
        builder.append("Palpite ").append(sorteioNumero).append(": ");
        for (Integer numero : numeros) {
            builder.append(numero).append(", ");
        }
        return builder.toString();
    }

    private void inicializaCampos(ModalidadeLoteria aposta) {
        mostraJogo(aposta);
        mostraIcone(aposta);
        mostraImagem(aposta);
        mostraQuantidadeDeDezenas(aposta);
    }

    private void mostraQuantidadeDeDezenas(ModalidadeLoteria aposta) {
        TextView entreAEB = findViewById(R.id.gera_palpite_entre_A_e_B);
        if (aposta.getApostaMaxima() - aposta.getApostaMinima() == 0) {
            entreAEB.setText(String.valueOf(aposta.getApostaMaxima()));
        } else {
            entreAEB.setText("(Entre " + aposta.getApostaMinima() + " e " + aposta.getApostaMaxima() + ")");
        }
    }

    private void mostraJogo(ModalidadeLoteria aposta) {
        TextView jogo = findViewById(R.id.gera_palpite_jogo);
        jogo.setText(aposta.getNome());
    }

    private void mostraIcone(ModalidadeLoteria aposta) {
        ImageView icone = findViewById(R.id.gera_palpite_icone);
        Drawable drawableIconeAposta = ResourcesUtil.devolveDrawable(this, aposta.getIcone());
        icone.setImageDrawable(drawableIconeAposta);
    }

    private void mostraImagem(ModalidadeLoteria aposta) {
        ImageView imagem = findViewById(R.id.gera_palpite_imagem);
        Drawable drawableImagemAposta = ResourcesUtil.devolveDrawable(this, aposta.getFundo());
        imagem.setImageDrawable(drawableImagemAposta);
    }
}
