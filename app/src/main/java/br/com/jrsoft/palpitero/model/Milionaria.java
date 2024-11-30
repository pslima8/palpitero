package br.com.jrsoft.palpitero.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import br.com.jrsoft.palpitero.R;

public class Milionaria extends ModalidadeLoteria {

    public Milionaria() {
        // Nome, total de dezenas, aposta mínima e máxima, fundo e ícone
        super("+Milionária", 50, 6, 12, R.drawable.milionaria, R.drawable.ic_milionaria);
    }

    @Override
    public List<List<Integer>> gerarPalpite(int quantidade) {
        List<Integer> dezenas = new ArrayList<>();
        for (int i = 1; i <= totalDezenas; i++) {
            dezenas.add(i);
        }

        // Embaralha e seleciona os números principais
        Collections.shuffle(dezenas);
        List<Integer> numerosPrincipais = new ArrayList<>(dezenas.subList(0, quantidade));

        // Seleciona os "trevos" da matriz separada (1 a 6)
        List<Integer> trevos = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            trevos.add(i);
        }
        Collections.shuffle(trevos);
        List<Integer> numerosTrevos = new ArrayList<>(trevos.subList(0, 2));

        // Retorna ambos os conjuntos em uma lista de listas
        List<List<Integer>> resultado = new ArrayList<>();
        resultado.add(numerosPrincipais); // Primeira sublista: números principais
        resultado.add(numerosTrevos);     // Segunda sublista: "trevos"

        return resultado;
    }

    // Implementação Parcelable
    protected Milionaria(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<Milionaria> CREATOR = new Parcelable.Creator<Milionaria>() {
        @Override
        public Milionaria createFromParcel(Parcel in) {
            return new Milionaria(in);
        }

        @Override
        public Milionaria[] newArray(int size) {
            return new Milionaria[size];
        }
    };
}
