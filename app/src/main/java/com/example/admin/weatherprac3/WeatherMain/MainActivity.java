package com.example.admin.weatherprac3.WeatherMain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.weatherprac3.R;
import com.example.admin.weatherprac3.WeatherMain.presenter.WeatherMainPresenter;
import com.example.admin.weatherprac3.WeatherMain.presenter.WeatherMainPresenterImpl;
import com.example.admin.weatherprac3.base.domain.CityDTO;
import com.example.admin.weatherprac3.base.domain.RepoDTO;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements WeatherMainPresenter.View{
    @Bind(R.id.btnWeather)
    Button btnWeather;
    @Bind(R.id.editCity)
    EditText editCity;
    @Bind(R.id.tvWeather)
    TextView tvWeather;
    @Bind(R.id.searchList)
    TextView tvSearchList;

    private WeatherMainPresenterImpl mWeatherMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }
    private void init(){
        mWeatherMainPresenter=new WeatherMainPresenterImpl();
        mWeatherMainPresenter.attachView(this);
        mWeatherMainPresenter.loadWeatherData();
        mWeatherMainPresenter.onChangeListen();

    }

    @Override
    public void updateWeatherTextView(StringBuffer stringBuffer) {
        tvSearchList.setText(stringBuffer);
    }

    @Override
    public void appendSeachList(RealmResults<CityDTO> result1) {
        tvSearchList.append(Double.toString(result1.get(result1.size()-1).getTemp())+"\n");
    }

    @Override
    public void clearSearchList() {
        tvSearchList.setText("");
    }

    @Override
    public String getCityString() {
        return editCity.getText().toString();
    }

    @Override
    public void setWeatherTv(RepoDTO repo) {
        tvWeather.setText("온도: "+String.valueOf(repo.getMain().getTemp())+"\n습도: "+String.valueOf(repo.getMain().getHumidity())+"\n압력: "+String.valueOf(repo.getMain().getPressure()
                +"\n최고온도: "+String.valueOf(repo.getMain().getTemp_max()+"\n최저온도: "+ String.valueOf(repo.getMain().getTemp_min()))
        ));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWeatherMainPresenter.detachView();
    }

    @OnClick(R.id.btnWeather)
    public void setGetWeatherBtn(View view){
        mWeatherMainPresenter.setGetWeatherBtn();
    }

    @OnClick(R.id.searchListDelete)
    public void searchDeleteBtn(View view){
        mWeatherMainPresenter.onDelete();
    }
}
