package br.com.jrsoft.palpitero.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public abstract class ModalidadeLoteria implements Parcelable {
    protected int totalDezenas;
    protected int apostaMinima;
    protected int apostaMaxima;
    protected String nome;
    protected int fundo;
    protected int icone;

    public ModalidadeLoteria(String nome, int totalDezenas, int apostaMinima, int apostaMaxima, int fundo, int icone) {
        this.nome = nome;
        this.totalDezenas = totalDezenas;
        this.apostaMinima = apostaMinima;
        this.apostaMaxima = apostaMaxima;
        this.fundo = fundo;
        this.icone = icone;
    }

    // Métodos getters...

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeInt(totalDezenas);
        dest.writeInt(apostaMinima);
        dest.writeInt(apostaMaxima);
        dest.writeInt(fundo);
        dest.writeInt(icone);
    }

    protected ModalidadeLoteria(Parcel in) {
        nome = in.readString();
        totalDezenas = in.readInt();
        apostaMinima = in.readInt();
        apostaMaxima = in.readInt();
        fundo = in.readInt();
        icone = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getTotalDezenas() {
        return totalDezenas;
    }

    public int getApostaMinima() {
        return apostaMinima;
    }

    public int getApostaMaxima() {
        return apostaMaxima;
    }

    public String getNome() {
        return nome;
    }

    public int getFundo() {
        return fundo;
    }

    public int getIcone() {
        return icone;
    }

    // Método abstrato para geração de palpite
    public abstract List<List<Integer>> gerarPalpite(int quantidade);
}
