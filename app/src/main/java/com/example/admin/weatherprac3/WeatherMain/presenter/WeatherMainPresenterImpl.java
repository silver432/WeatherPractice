package com.example.admin.weatherprac3.WeatherMain.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.admin.weatherprac3.WeatherMain.model.WeatherMainModel;
import com.example.admin.weatherprac3.base.domain.CityDTO;
import com.example.admin.weatherprac3.base.domain.RepoDTO;
import com.example.admin.weatherprac3.base.listener.OnChangeListener;

import io.realm.RealmResults;

/**
 * Created by admin on 2017-03-08.
 */

public class WeatherMainPresenterImpl implements WeatherMainPresenter.Presenter,WeatherMainModel.WeatherDataChange,OnChangeListener,WeatherMainModel.WeatherCallback{
    private WeatherMainPresenter.View view;
    private WeatherMainModel mWeatherMainModel;

    @Override
    public void attachView(WeatherMainPresenter.View view) {
        this.view=view;
        mWeatherMainModel=new WeatherMainModel();
        mWeatherMainModel.setDataChangeListener(this);
        mWeatherMainModel.setOnWeatherCallbakListener(this);
    }

    @Override
    public void detachView() {
        this.view=null;
        mWeatherMainModel.setDataChangeListener(null);
        mWeatherMainModel.setOnWeatherCallbakListener(null);
        mWeatherMainModel=null;
    }

    @Override
    public void loadWeatherData() {
        mWeatherMainModel.loadWeatherData();
    }

    @Override
    public void setGetWeatherBtn() {
        String city= view.getCityString();
        mWeatherMainModel.setGetWeather(city);
    }

    @Override
    public void onSuccess(RealmResults<CityDTO> result1) {
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<result1.size();i++){
            sb.append(Double.toString(result1.get(i).getTemp())+"\n");
        }
        view.updateWeatherTextView(sb);
    }

    @Override
    public void onChange(RealmResults<CityDTO> result1) {
        view.appendSeachList(result1);
    }

    @Override
    public void onClearText() {
        view.clearSearchList();
    }

    @Override
    public void onDelete() {
        mWeatherMainModel.deleteDB();
    }

    @Override
    public void onChangeListen() {
        mWeatherMainModel.addChangeListen();
    }

    @Override
    public void onWeatherCallSuccess(RepoDTO repo) {
        view.setWeatherTv(repo);
    }

    @Override
    public void onWeatherCallFailure() {
        Log.d("MainActivity","error");
    }
}
