package com.example.admin.weatherprac3.WeatherMain.model;

import com.example.admin.weatherprac3.base.domain.CityDTO;
import com.example.admin.weatherprac3.base.domain.RepoDTO;
import com.example.admin.weatherprac3.base.network.ApiInterface;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017-03-08.
 */

public class WeatherMainModel {
    private final static String TAG=WeatherMainModel.class.getSimpleName();

    private Realm realm;
    private RepoDTO repo;
    private RealmResults<CityDTO> result1;

    private WeatherDataChange mWeatherDataChange;

    private WeatherCallback mWeatherCallback;

    public interface WeatherDataChange{
        void onSuccess(RealmResults<CityDTO> result1);
        void onChange(RealmResults<CityDTO> result1);
        void onClearText();
        void onDelete();
    }

    public interface WeatherCallback{
        void onWeatherCallSuccess(RepoDTO repo);
        void onWeatherCallFailure();
    }

    public void setDataChangeListener(WeatherDataChange weatherDataChange){
        mWeatherDataChange=weatherDataChange;
    }

    public WeatherMainModel(){
        loadDB();
    }

    public void addChangeListen(){
        result1.addChangeListener(new RealmChangeListener<RealmResults<CityDTO>>() {
            @Override
            public void onChange(RealmResults<CityDTO> element) {
                if(result1.size()>0) mWeatherDataChange.onChange(result1);
                else mWeatherDataChange.onClearText();
            }
        });
    }

    private void loadDB(){
        realm=Realm.getDefaultInstance();
    }

    public void loadWeatherData(){
        RealmQuery<CityDTO> query=realm.where(CityDTO.class);
        result1=query.findAll();
        mWeatherDataChange.onSuccess(result1);
    }

    public void setGetWeather(String city){
        ApiInterface service=ApiInterface.retrofit.create(ApiInterface.class);
        Call<RepoDTO> call=service.repo("647711d048ec5ca74c828693a7effdda",city);
        call.enqueue(weatherCallbackListener);
    }

    public void setOnWeatherCallbakListener(WeatherCallback callback){
        mWeatherCallback=callback;
    }

    private Callback<RepoDTO> weatherCallbackListener=new Callback<RepoDTO>() {
        @Override
        public void onResponse(Call<RepoDTO> call, Response<RepoDTO> response) {
            if(response.isSuccessful()){
                repo=response.body();
                mWeatherCallback.onWeatherCallSuccess(repo);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        CityDTO city=realm.createObject(CityDTO.class);
                        city.setTemp(repo.getMain().getTemp());
                        city.setHumidity(repo.getMain().getHumidity());
                        city.setPressure(repo.getMain().getPressure());
                        city.setTemp_max(repo.getMain().getTemp_max());
                        city.setTemp_min(repo.getMain().getTemp_min());
                    }
                });
            }
            else{
                mWeatherCallback.onWeatherCallFailure();
            }
        }

        @Override
        public void onFailure(Call<RepoDTO> call, Throwable t) {
            mWeatherCallback.onWeatherCallFailure();
        }
    };

    public void deleteDB(){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(CityDTO.class);
            }
        });
    }


}
