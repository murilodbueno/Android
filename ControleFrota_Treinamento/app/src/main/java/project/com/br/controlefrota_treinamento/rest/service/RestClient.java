package project.com.br.controlefrota_treinamento.rest.service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by mbueno on 26/08/2015.
 */
public class RestClient {

    //substituir 'mbueno'
    public static final String URL = "http://controlefrotaaux.mbueno.com";

    public static ControleFrotaAPI getClientAPI(){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL)
                .setConverter(new GsonConverter(gson))
                .build();

        ControleFrotaAPI service = restAdapter.create(ControleFrotaAPI.class);

        return service;
    }
}
