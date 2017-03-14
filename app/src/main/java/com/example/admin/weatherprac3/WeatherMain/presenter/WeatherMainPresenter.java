package com.example.admin.weatherprac3.WeatherMain.presenter;

import android.app.Activity;

import com.example.admin.weatherprac3.base.domain.CityDTO;
import com.example.admin.weatherprac3.base.domain.RepoDTO;

import io.realm.RealmResults;

/**
 * Created by admin on 2017-03-08.
 */

public interface WeatherMainPresenter {
    interface View{
        void updateWeatherTextView(StringBuffer stringBuffer);
        void appendSeachList(RealmResults<CityDTO> result1);
        void clearSearchList();
        String getCityString();
        void setWeatherTv(RepoDTO repo);
    }
    interface Presenter{
        void attachView(WeatherMainPresenter.View view);
        void detachView();
        void loadWeatherData();
        void setGetWeatherBtn();

    }
}
