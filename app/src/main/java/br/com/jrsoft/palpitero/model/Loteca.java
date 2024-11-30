package br.com.jrsoft.palpitero.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.jrsoft.palpitero.R;

public class Loteca extends ModalidadeLoteria {

    public Loteca() {
        super("Loteca", 14, 14, 14, R.drawable.loteca, R.drawable.ic_loteca);
    }

    // Implementação do método abstrato `gerarPalpite(int)` com um retorno neutro
    @Override
    public List<List<Integer>> gerarPalpite(int quantidade) {
        return new ArrayList<>(); // Retorna uma lista vazia, pois esse método não será usado
    }

    // Método específico para gerar palpites na Loteca
    public List<String> gerarPalpiteLoteca() {
        List<String> palpites = new ArrayList<>();
        String[] opcoes = {"1", "X", "2"};
        Random random = new Random();

        for (int i = 0; i < totalDezenas; i++) {
            String escolha = opcoes[random.nextInt(opcoes.length)];
            palpites.add(escolha);
        }

        return palpites;
    }

    // Implementação Parcelable
    protected Loteca(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<Loteca> CREATOR = new Parcelable.Creator<Loteca>() {
        @Override
        public Loteca createFromParcel(Parcel in) {
            return new Loteca(in);
        }

        @Override
        public Loteca[] newArray(int size) {
            return new Loteca[size];
        }
    };
}
