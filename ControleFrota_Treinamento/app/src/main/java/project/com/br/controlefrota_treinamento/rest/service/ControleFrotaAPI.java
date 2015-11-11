package project.com.br.controlefrota_treinamento.rest.service;

import project.com.br.controlefrota_treinamento.rest.model.AvariaRequest;
import project.com.br.controlefrota_treinamento.rest.model.AvariaResponse;
import project.com.br.controlefrota_treinamento.rest.model.SincronizacaoRequest;
import project.com.br.controlefrota_treinamento.rest.model.SincronizacaoResponse;
import project.com.br.controlefrota_treinamento.rest.model.TransacaoRequest;
import project.com.br.controlefrota_treinamento.rest.model.TransacaoResponse;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by mbueno on 26/08/2015.
 */
public interface ControleFrotaAPI {

    @POST("/mobile/redirect")
    public void getResponseSync(@Body SincronizacaoRequest params, retrofit.Callback<SincronizacaoResponse> cb);

    @POST("/mobile/redirect")
    public void getSendTransacao(@Body TransacaoRequest params, retrofit.Callback<TransacaoResponse> cb);

    @POST("/mobile/redirect")
    public void getSendAvaria(@Body AvariaRequest params, retrofit.Callback<AvariaResponse> cb);

}
