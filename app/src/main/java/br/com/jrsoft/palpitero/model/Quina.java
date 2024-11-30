package br.com.jrsoft.palpitero.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import br.com.jrsoft.palpitero.R;


public class Quina extends ModalidadeLoteria {

    public Quina() {
        super("Quina", 80, 5, 15, R.drawable.quina, R.drawable.ic_quina);
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
    protected Quina(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<Quina> CREATOR = new Parcelable.Creator<Quina>() {
        @Override
        public Quina createFromParcel(Parcel in) {
            return new Quina(in);
        }

        @Override
        public Quina[] newArray(int size) {
            return new Quina[size];
        }
    };

}
