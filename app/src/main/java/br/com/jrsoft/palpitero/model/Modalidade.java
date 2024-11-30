package br.com.jrsoft.palpitero.model;

public class Modalidade {
    private String nome;
    private String numeroSorteio;
    private String data;
    private String premioEstimado;

    public Modalidade(String nome, String numeroSorteio, String data, String premioEstimado) {
        this.nome = nome;
        this.numeroSorteio = numeroSorteio;
        this.data = data;
        this.premioEstimado = premioEstimado;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public String getNumeroSorteio() {
        return numeroSorteio;
    }

    public String getData() {
        return data;
    }

    public String getPremioEstimado() {
        return premioEstimado;
    }
}
