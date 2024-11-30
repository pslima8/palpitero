package br.com.jrsoft.palpitero.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import br.com.jrsoft.palpitero.R;

public class DiaDeSorte extends ModalidadeLoteria {

    // Lista de meses para o "Mês da Sorte"
    private static final String[] MESES = {
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    };

    public DiaDeSorte() {
        super("Dia de Sorte", 31, 7, 15, R.drawable.dia_de_sorte, R.drawable.ic_dia_de_sorte);
    }

    @Override
    public List<List<Integer>> gerarPalpite(int quantidade) {
        List<Integer> dezenas = new ArrayList<>();
        for (int i = 1; i <= totalDezenas; i++) {
            dezenas.add(i);
        }

        // Embaralha e seleciona as dezenas
        Collections.shuffle(dezenas);
        List<Integer> numerosPrincipais = new ArrayList<>(dezenas.subList(0, quantidade));

        // Seleciona um "Mês da Sorte" aleatoriamente
        int mesSorteado = new Random().nextInt(MESES.length);

        // Adiciona o mês como o último item na lista de listas (com o índice do mês sorteado)
        List<List<Integer>> resultado = new ArrayList<>();
        resultado.add(numerosPrincipais); // Primeira sublista: números principais
        resultado.add(Collections.singletonList(mesSorteado)); // Segunda sublista: índice do "Mês da Sorte"

        return resultado;
    }

    public String getMesDaSorte(int indice) {
        return indice >= 0 && indice < MESES.length ? MESES[indice] : "Mês desconhecido";
    }

    // Implementação Parcelable
    protected DiaDeSorte(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<DiaDeSorte> CREATOR = new Parcelable.Creator<DiaDeSorte>() {
        @Override
        public DiaDeSorte createFromParcel(Parcel in) {
            return new DiaDeSorte(in);
        }

        @Override
        public DiaDeSorte[] newArray(int size) {
            return new DiaDeSorte[size];
        }
    };
}
