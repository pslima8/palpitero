package br.com.jrsoft.palpitero.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.jrsoft.palpitero.R;
import br.com.jrsoft.palpitero.model.ModalidadeLoteria;
import br.com.jrsoft.palpitero.util.ResourcesUtil;

public class ListaJogosAdapter extends BaseAdapter {

    private final List<ModalidadeLoteria> apostas;
    private final Context context;

    public ListaJogosAdapter(List<ModalidadeLoteria> apostas, Context context) {

        this.apostas = apostas;
        this.context = context;
    }


    @Override
    public int getCount() {
        return apostas.size();
    }

    @Override
    public Object getItem(int posicao) {
        return apostas.get(posicao);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup parent) {
        View viewCriada = LayoutInflater.from(context).inflate(R.layout.item_apostas, parent, false);

        ModalidadeLoteria aposta = apostas.get(posicao);
        Log.d("fundo", "Id: " + aposta.getFundo());
        mostraFundo(viewCriada, aposta);
        mostraIcone(viewCriada, aposta);
        mostraJogo(viewCriada, aposta);
        return viewCriada;
    }

    private void mostraJogo(View viewCriada, ModalidadeLoteria aposta) {
        TextView jogo = viewCriada.findViewById(R.id.item_apostas_jogo);
        jogo.setText(aposta.getNome());
    }

    private void mostraIcone(View viewCriada, ModalidadeLoteria aposta) {
        ImageView icone = viewCriada.findViewById(R.id.item_apostas_icone);
        Drawable drawableIconeAposta = ResourcesUtil.devolveDrawable(context, aposta.getIcone());
        icone.setImageDrawable(drawableIconeAposta);
    }

    private void mostraFundo(View viewCriada, ModalidadeLoteria aposta) {
        ImageView imagem = viewCriada.findViewById(R.id.item_apostas_imagem);
        Drawable drawableImagemAposta = ResourcesUtil.devolveDrawable(context, aposta.getFundo());
        imagem.setImageDrawable(drawableImagemAposta);
    }
}
