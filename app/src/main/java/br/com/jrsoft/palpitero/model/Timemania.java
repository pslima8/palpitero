package br.com.jrsoft.palpitero.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import br.com.jrsoft.palpitero.R;

public class Timemania extends ModalidadeLoteria {

    private static final List<String> timesDoCoracao = new ArrayList<>();

    static {
        // Inicialize a lista com os 80 times
        Collections.addAll(timesDoCoracao,
                "Flamengo", "Corinthians", "São Paulo", "Palmeiras", "Vasco",
                "Santos", "Grêmio", "Internacional", "Fluminense", "Botafogo",
                "Cruzeiro", "Atlético Mineiro", "Bahia", "Sport Recife", "Vitória",
                "Fortaleza", "Ceará", "Atlético Paranaense", "Coritiba", "Goiás",
                "Atlético Goianiense", "Figueirense", "Avaí", "Ponte Preta", "Santa Cruz",
                "Náutico", "Juventude", "Criciúma", "Remo", "Paysandu",
                "Paraná Clube", "América Mineiro", "Botafogo-SP", "Sampaio Corrêa",
                "Guarani", "Operário", "São Caetano", "Portuguesa", "Joinville",
                "Brasil de Pelotas", "São Raimundo", "Confiança", "River", "Imperatriz",
                "Ituano", "XV de Piracicaba", "Ypiranga", "Anapolina", "Bangu",
                "América-RN", "Ferroviário", "Tombense", "Manaus", "Altos",
                "Treze", "Botafogo-PB", "Campinense", "Sergipe", "CSA",
                "CRB", "ASA", "Cianorte", "Icasa", "Central",
                "Atlético Acreano", "Rio Branco", "Fast Clube", "Moto Club", "Macapá",
                "São Bento", "Taubaté", "Volta Redonda", "Madureira", "Friburguense",
                "Villa Nova", "Democrata", "Uberlândia", "URT", "Boa Esporte",
                "Tupi", "Caldense", "Nacional-AM", "São José-RS", "Pelotas"
        );
    }

    public Timemania() {
        super("Timemania", 80, 10, 10, R.drawable.timemania, R.drawable.ic_timemania);
    }

    @Override
    public List<List<Integer>> gerarPalpite(int quantidade) {
        List<Integer> dezenas = new ArrayList<>();
        for (int i = 1; i <= totalDezenas; i++) {
            dezenas.add(i);
        }

        // Embaralha e seleciona 10 dezenas
        Collections.shuffle(dezenas);
        List<Integer> numerosPrincipais = new ArrayList<>(dezenas.subList(0, quantidade));

        // Seleciona um "Time do Coração" aleatoriamente
        int numeroTime = new Random().nextInt(timesDoCoracao.size()) + 1;
        String nomeTimeDoCoracao = getNomeDoTime(numeroTime);

        // Retorna os números e o "Time do Coração" como uma lista de listas
        List<List<Integer>> resultado = new ArrayList<>();
        resultado.add(numerosPrincipais);
        resultado.add(Collections.singletonList(numeroTime)); // Lista com o número do time

        return resultado;
    }

    public String getNomeDoTime(int numero) {
        // Converte o número em índice e retorna o nome do time
        return numero > 0 && numero <= timesDoCoracao.size() ? timesDoCoracao.get(numero - 1) : "Time desconhecido";
    }

    // Implementação Parcelable
    protected Timemania(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<Timemania> CREATOR = new Parcelable.Creator<Timemania>() {
        @Override
        public Timemania createFromParcel(Parcel in) {
            return new Timemania(in);
        }

        @Override
        public Timemania[] newArray(int size) {
            return new Timemania[size];
        }
    };
}
