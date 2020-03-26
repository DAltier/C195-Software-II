/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

/**
 *
 * @author Dana Altier
 */

public class City {
    
    private int cityId;
    private String city;
    private int countryId;
    
    public City() {
        
    }
    
    public City(int cityId, String city, int countryId) {
        
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
        
    }
    
    //Getters and setters for City
    
    public int getCityId() {
        
        return cityId;
    
    }

    public void setCityId(int cityId) {
        
        this.cityId = cityId;
        
    }

    public String getCity() {
        
        return city;
        
    }

    public void setCity(String city) {
        
        this.city = city;
        
    }

    public int getCountryId() {
        
        return countryId;
        
    }

    public void setCountryId(int countryId) {
        
        this.countryId = countryId;
        
    } 
    
}