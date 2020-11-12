package com.example.madassignment2.object;

import android.widget.EditText;

public class Setting {

    //declarations
    private int id = 0;
    private int mapWidth, mapHeight, initialMoney;
    private int familySize;
    private int shopSize;
    private int salary;
    private int serviceCost;
    private int houseBuildingCost;
    private int commBuildingCost;
    private int roadBuildingCost;
    private double taxRate;
    private String cityName;

    public Setting(int mapWidth, int mapHeight, int initialMoney) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.initialMoney = initialMoney;
        this.familySize = 4;
        this.shopSize = 6;
        this.salary = 10;
        this.taxRate = 0.3;
        this.serviceCost = 2;
        this.houseBuildingCost = 100;
        this.commBuildingCost = 500;
        this.roadBuildingCost = 20;
        this.cityName = cityName;
    }

    //setters
    public void setId() { this.id = id++; }
    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }
    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }
    public void setInitialMoney(int initialMoney) {
        this.initialMoney = initialMoney;
    }
    public void setCityName(String cityName) { this.cityName = cityName; }



    //getters
    public int getId() { return id; }
    public int getMapWidth() {
        return mapWidth;
    }
    public int getMapHeight() {
        return mapHeight;
    }
    public int getInitialMoney() {
        return initialMoney;
    }
    public int getFamilySize() {
        return familySize;
    }
    public int getShopSize() {
        return shopSize;
    }
    public int getSalary() {
        return salary;
    }
    public double getTaxRate() {
        return taxRate;
    }
    public int getServiceCost() {
        return serviceCost;
    }
    public int getHouseBuildingCost() {
        return houseBuildingCost;
    }
    public int getCommBuildingCost() {
        return commBuildingCost;
    }
    public int getRoadBuildingCost() {
        return roadBuildingCost;
    }
    public String getCityName() { return cityName; }
}













