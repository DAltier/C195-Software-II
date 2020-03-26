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

public class Country {
    
    private int countryId;
    private String country;
    
    public Country() {
        
    }
    
    public Country(int countryId, String country) {
        
        this.countryId = countryId;
        this.country = country;
        
    }

    //Getters and setters for Country
    
    public int getCountryId() {
        
        return countryId;
        
    }

    public void setCountryId(int countryId) {
        
        this.countryId = countryId;
        
    }

    public String getCountry() {
        
        return country;
        
    }

    public void setCountry(String country) {
        
        this.country = country;
        
    }
   
}
