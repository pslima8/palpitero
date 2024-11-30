package br.com.jrsoft.palpitero.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import br.com.jrsoft.palpitero.R;

public class SuperSete extends ModalidadeLoteria {

    public SuperSete() {
        super("Super Sete", 10, 7, 21, R.drawable.super_sete, R.drawable.ic_super_sete);
    }

    @Override
    public List<List<Integer>> gerarPalpite(int quantidade) {
        List<List<Integer>> resultado = new ArrayList<>();
        Random random = new Random();

        // Calcula a quantidade de números por coluna, ajustando para que cada coluna tenha entre 1 e 3 números
        int numerosRestantes = quantidade;
        for (int coluna = 0; coluna < 7; coluna++) {
            List<Integer> numerosColuna = new ArrayList<>();

            // Define a quantidade de números para esta coluna (mínimo 1, máximo 3, e não ultrapassando o restante)
            int numerosPorColuna = Math.min(3, Math.max(1, numerosRestantes - (7 - coluna - 1)));
            numerosRestantes -= numerosPorColuna;

            // Gera números únicos para a coluna (de 0 a 9)
            List<Integer> opcoes = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                opcoes.add(i);
            }
            Collections.shuffle(opcoes);
            numerosColuna.addAll(opcoes.subList(0, numerosPorColuna));

            // Adiciona os números da coluna ao resultado
            resultado.add(numerosColuna);
        }

        return resultado;
    }

    // Implementação Parcelable
    protected SuperSete(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<SuperSete> CREATOR = new Parcelable.Creator<SuperSete>() {
        @Override
        public SuperSete createFromParcel(Parcel in) {
            return new SuperSete(in);
        }

        @Override
        public SuperSete[] newArray(int size) {
            return new SuperSete[size];
        }
    };
}
