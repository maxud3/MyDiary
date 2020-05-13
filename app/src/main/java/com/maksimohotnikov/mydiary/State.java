package com.maksimohotnikov.mydiary;

public class State {
    private String time;
    private String sugarInBlood;
    private String breadUnits;
    private String doseInsulin;

    public State(String time, String sugarInBlood, String breadUnits, String doseInsulin){
        this.time = time;
        this.sugarInBlood = sugarInBlood;
        this.breadUnits = breadUnits;
        this.doseInsulin = doseInsulin;
    }

    public String getTime(){
        return this.time;
    }
    public void setTime(String time){
        this.time = time;
    }

    public String getSugarInBlood(){
        return this.sugarInBlood;
    }
    public void setSugarInBlood(String sugarInBlood){
        this.sugarInBlood = sugarInBlood;
    }

    public String getBreadUnits(){
        return this.breadUnits;
    }
    public void setBreadUnits(String breadUnits){
        this.breadUnits = breadUnits;
    }

    public String getDoseInsulin(){
        return this.doseInsulin;
    }
    public void setDoseInsulin(String doseInsulin){
        this.doseInsulin = doseInsulin;
    }
}
