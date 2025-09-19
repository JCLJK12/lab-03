package com.example.listycitylab3;

public class City {
    private String name;
    private String province;
    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }
    public String getCity() {
        return name;
    }

    public void setCity(String city) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}
