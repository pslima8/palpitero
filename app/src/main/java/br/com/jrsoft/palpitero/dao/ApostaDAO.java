package br.com.jrsoft.palpitero.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.jrsoft.palpitero.model.DiaDeSorte;
import br.com.jrsoft.palpitero.model.DuplaSena;
import br.com.jrsoft.palpitero.model.Loteca;
import br.com.jrsoft.palpitero.model.Lotofacil;
import br.com.jrsoft.palpitero.model.Lotomania;
import br.com.jrsoft.palpitero.model.MegaSena;
import br.com.jrsoft.palpitero.model.Milionaria;
import br.com.jrsoft.palpitero.model.Quina;
import br.com.jrsoft.palpitero.model.ModalidadeLoteria;
import br.com.jrsoft.palpitero.model.SuperSete;
import br.com.jrsoft.palpitero.model.Timemania;

public class ApostaDAO {

    public List<ModalidadeLoteria> lista() {
        List<ModalidadeLoteria> apostas = new ArrayList<>();
        apostas.add(new MegaSena());
        apostas.add(new Lotofacil());
        apostas.add(new Quina());
        apostas.add(new Lotomania());
        apostas.add(new DuplaSena());
        apostas.add(new Milionaria());
        apostas.add(new Timemania());
        apostas.add(new Loteca());
        apostas.add(new DiaDeSorte());
        apostas.add(new SuperSete());
        return apostas;
    }
}
