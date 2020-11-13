package com.example.madassignment2.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.madassignment2.R;
import com.example.madassignment2.activities.DetailsMenu;
import com.example.madassignment2.activities.MainActivity;
import com.example.madassignment2.activities.SettingsMenu;
import com.example.madassignment2.activities.StartActivity;
import com.example.madassignment2.database.GameCursor;
import com.example.madassignment2.object.GameData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class FragmentStatus extends Fragment {

    private FragmentMap map;

    //display the money
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private String sMoney;
    private Chronometer timer;
    private TextView currMoney, gameTimer, cityName, popDisplay, eDisplay, tempDisplay;
    private Button nextDayButton, demolishButton, detailBtn, saveBtn;
    boolean demolishClick = false, detailClick = false;
    int nextDay;

    //setting variables to make life easier
    int money = GameData.get().getMoney();
    int salary = GameData.get().getSetting().getSalary();
    double taxRate = GameData.get().getSetting().getTaxRate();
    int serviceCost = GameData.get().getSetting().getServiceCost();
    int population;
    double employmentRate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle) {

        View view = inflater.inflate(R.layout.status_menu, ui, false);

        population = GameData.get().getSetting().getFamilySize() * map.getNRes();
        employmentRate = Math.min(1, (double) map.getNComm() * (double) GameData.get().getSetting().getShopSize() / (double) population);

        timer = (Chronometer) view.findViewById(R.id.real_time); // initiate a chronometer
        currMoney = (TextView) view.findViewById(R.id.text_curr_money);
        gameTimer = (TextView) view.findViewById(R.id.text_game_time);
        cityName = (TextView) view.findViewById(R.id.text_city_name);
        popDisplay = (TextView) view.findViewById(R.id.text_current_population);
        eDisplay = (TextView) view.findViewById(R.id.text_employment_rate);
        tempDisplay = (TextView) view.findViewById(R.id.text_curr_temp);
        nextDayButton = (Button) view.findViewById(R.id.next_day_button);
        demolishButton = (Button) view.findViewById(R.id.demolish_button);
        detailBtn = (Button) view.findViewById(R.id.detail_button);
        saveBtn = (Button) view.findViewById(R.id.save_button);


        sMoney = Integer.toString(GameData.get().getMoney());
        money += population * (employmentRate * salary * taxRate - serviceCost);

        //Initial set texts to display on status screen
        cityName.setText("City Name: " + GameData.get().getSetting().getCityName());
        currMoney.setText("Money: " + sMoney);
        gameTimer.setText("Day: " + GameData.get().getGameTime());
        popDisplay.setText("Population: " + GameData.get().getPopulation());
        eDisplay.setText("Employ Rate: %" + GameData.get().getEmploymentRate());
        findWeather();
        timer.start();

        //updates the status
        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDay++;
                gameTimer.setText("Game Timer: " + nextDay);
                update();
            }
        });

        //this button onClick accesses if statement in FragmentMap to demolish a building.
        demolishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!demolishClick) {
                    demolishButton.setBackgroundColor(Color.DKGRAY);
                    demolishClick = true;
                    if (detailClick == true) {
                        detailBtn.setBackgroundColor(Color.LTGRAY);
                        detailClick = false;
                    }
                } else {
                    demolishButton.setBackgroundColor(Color.LTGRAY);
                    demolishClick = false;
                }
            }
        });

        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!detailClick) {
                    detailBtn.setBackgroundColor(Color.DKGRAY);
                    detailClick = true;
                    if (demolishClick == true) {
                        demolishButton.setBackgroundColor(Color.LTGRAY);
                        demolishClick = false;
                    }
                } else {
                    detailBtn.setBackgroundColor(Color.LTGRAY);
                    detailClick = false;
                }
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //sets all needed statuses, mapElements and settings
                    saveStatus();
                    GameData.get().addSetting();
                    GameData.get().addMapData();
                    Toast.makeText(getContext(), "Game Data Saved!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    public void setFragmentMap(FragmentMap map) {
        this.map = map;
    }

    public void update() {
        //calculations and things
        population = GameData.get().getSetting().getFamilySize() * map.getNRes();
        employmentRate = Math.min(1, (double) map.nComm * (double) GameData.get().getSetting().getShopSize() / (double) population);
        money += population * (employmentRate * salary * taxRate - serviceCost);
        findWeather();
        int totalMoney = GameData.get().getMoney() + money;
        GameData.get().setMoney(totalMoney);

        sMoney = Integer.toString(GameData.get().getMoney());

        if (GameData.get().getMoney() > 0) {
            gameTimer.setText("Day: " + nextDay);
            if (money >= 0)
                currMoney.setText("Money: " + sMoney + " +" + money);
            else
                currMoney.setText("Money: " + sMoney + " " + money);
            popDisplay.setText("Population: " + population);
            eDisplay.setText("Employ. Rate: %" + df2.format( employmentRate * 100));
        } else {
            Toast.makeText(getActivity(), "GAME OVER", Toast.LENGTH_SHORT).show();
            currMoney.setText("Money: " + sMoney);
        }
    }

    public void updateMoney() {
        sMoney = Integer.toString(GameData.get().getMoney());
        if (money >= 0)
            currMoney.setText("Money: " + sMoney + " +" + money);
        else
            currMoney.setText("Money: " + sMoney + " " + money);
    }


    //reference: https://www.youtube.com/watch?v=8-7Ip6xum6E
    public void findWeather() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=perth&appid=0e0c6f6314c3186b32492dabda6d2195&units=metric";

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String temp;
                    JSONObject main_object = response.getJSONObject("main");
                    temp = String.valueOf(main_object.getDouble("temp"));
                    tempDisplay.setText("Temp: " + temp + "°C");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(jor);
    }

    //sets all needed statuses
    public void saveStatus() {
        GameData.get().setCurrMoney(GameData.get().getMoney());
        GameData.get().setGameTime(nextDay);
        GameData.get().setCityName(GameData.get().getSetting().getCityName());
        GameData.get().setPopulation(population);
        GameData.get().setEmploymentRate((int)employmentRate*100);
        GameData.get().setnRes(map.getNRes());
        GameData.get().setnComm(map.getNComm());
        GameData.get().addStatusMenuData();
    }

    //GETTERS
    public boolean getClick() {
        return demolishClick;
    }
    public boolean getDetailClick() {
        return detailClick;
    }
}
