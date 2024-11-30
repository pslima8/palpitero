package br.com.jrsoft.palpitero.util;

public class SorteioResponse {
    private boolean acumulado;
    private String dataProximoConcurso;
    private int numeroConcursoProximo;
    private double valorEstimadoProximoConcurso;
    private String tipoJogo;

    // Getters
    public boolean isAcumulado() {
        return acumulado;
    }

    public String getDataProximoConcurso() {
        return dataProximoConcurso;
    }

    public int getNumeroConcursoProximo() {
        return numeroConcursoProximo;
    }

    public double getValorEstimadoProximoConcurso() {
        return valorEstimadoProximoConcurso;
    }

    public String getTipoJogo() {
        return tipoJogo;
    }
}
