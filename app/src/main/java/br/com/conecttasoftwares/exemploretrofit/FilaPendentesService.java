package br.com.conecttasoftwares.exemploretrofit;

import br.com.conecttasoftwares.exemploretrofit.models.FilaPendentes;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by luis.ferreira on 25/05/2016.
 */
public interface FilaPendentesService {

    public static final String URL = "http://conecttasms.com.br/http/getfilaJSON.ashx/";

    @GET("Pendentes")
    Call<FilaPendentes> ListPendentes();
}