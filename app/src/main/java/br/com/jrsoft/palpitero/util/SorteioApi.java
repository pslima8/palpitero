package br.com.jrsoft.palpitero.util;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SorteioApi {
    @GET("loteria/{modalidade}/ultimo")
    Call<SorteioResponse> obterUltimoSorteio(@Path("modalidade") String modalidade);
}
