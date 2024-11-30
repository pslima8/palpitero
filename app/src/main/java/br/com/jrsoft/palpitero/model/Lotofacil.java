package br.com.jrsoft.palpitero.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import br.com.jrsoft.palpitero.R;

public class Lotofacil extends ModalidadeLoteria {

    public Lotofacil() {
        super("Lotofácil", 25, 15, 20, R.drawable.lotofacil, R.drawable.ic_lotofacil);
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
    protected Lotofacil(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<Lotofacil> CREATOR = new Parcelable.Creator<Lotofacil>() {
        @Override
        public Lotofacil createFromParcel(Parcel in) {
            return new Lotofacil(in);
        }

        @Override
        public Lotofacil[] newArray(int size) {
            return new Lotofacil[size];
        }
    };

}
