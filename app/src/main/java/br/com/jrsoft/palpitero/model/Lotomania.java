package br.com.jrsoft.palpitero.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import br.com.jrsoft.palpitero.R;

public class Lotomania extends ModalidadeLoteria {

    public Lotomania() {
        // Nome, total de dezenas, aposta mínima e máxima (ambos 50), fundo e ícone
        super("Lotomania", 100, 50, 50, R.drawable.lotomania, R.drawable.ic_lotomania);
    }

    @Override
    public List<List<Integer>> gerarPalpite(int quantidade) {
        List<Integer> dezenas = new ArrayList<>();
        for (int i = 1; i <= totalDezenas; i++) {
            dezenas.add(i);
        }
        Collections.shuffle(dezenas);
        List<Integer> resultado = dezenas.subList(0, quantidade);

        List<List<Integer>> retorno = new ArrayList<>();
        retorno.add(resultado); // Coloca o resultado em uma sublista
        return retorno;
    }


    // Implementação Parcelable
    protected Lotomania(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<Lotomania> CREATOR = new Parcelable.Creator<Lotomania>() {
        @Override
        public Lotomania createFromParcel(Parcel in) {
            return new Lotomania(in);
        }

        @Override
        public Lotomania[] newArray(int size) {
            return new Lotomania[size];
        }
    };
}
