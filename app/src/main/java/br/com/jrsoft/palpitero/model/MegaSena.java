package br.com.jrsoft.palpitero.model;

import android.os.Parcel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.jrsoft.palpitero.R;

public class MegaSena extends ModalidadeLoteria {

    public MegaSena() {
        super("Mega Sena", 60, 6, 15, R.drawable.megasena, R.drawable.ic_megasena);
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


    // Implementando Parcelable na subclasse
    protected MegaSena(Parcel in) {
        super(in);
    }

    public static final Creator<MegaSena> CREATOR = new Creator<MegaSena>() {
        @Override
        public MegaSena createFromParcel(Parcel in) {
            return new MegaSena(in);
        }

        @Override
        public MegaSena[] newArray(int size) {
            return new MegaSena[size];
        }
    };

}
