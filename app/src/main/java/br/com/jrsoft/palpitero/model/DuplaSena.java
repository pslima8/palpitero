package br.com.jrsoft.palpitero.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import br.com.jrsoft.palpitero.R;


public class DuplaSena extends ModalidadeLoteria {

    public DuplaSena() {
        // Nome, total de dezenas, aposta mínima e máxima, fundo e ícone
        super("Dupla Sena", 50, 6, 15, R.drawable.dupla_sena, R.drawable.ic_dupla_sena);
    }

    @Override
    public List<List<Integer>> gerarPalpite(int quantidade) {
        List<Integer> dezenas = new ArrayList<>();
        for (int i = 1; i <= totalDezenas; i++) {
            dezenas.add(i);
        }

        // Realiza o primeiro sorteio
        Collections.shuffle(dezenas);
        List<Integer> primeiroSorteio = new ArrayList<>(dezenas.subList(0, quantidade));

        // Realiza o segundo sorteio
        Collections.shuffle(dezenas);
        List<Integer> segundoSorteio = new ArrayList<>(dezenas.subList(0, quantidade));

        // Retorna os dois sorteios como uma lista de listas
        List<List<Integer>> resultado = new ArrayList<>();
        resultado.add(primeiroSorteio);
        resultado.add(segundoSorteio);

        return resultado;
    }

    // Implementação Parcelable
    protected DuplaSena(Parcel in) {
        super(in);
    }

    public static final Parcelable.Creator<DuplaSena> CREATOR = new Parcelable.Creator<DuplaSena>() {
        @Override
        public DuplaSena createFromParcel(Parcel in) {
            return new DuplaSena(in);
        }

        @Override
        public DuplaSena[] newArray(int size) {
            return new DuplaSena[size];
        }
    };
}
