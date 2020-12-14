package com.github.adm.countrycodepicker;

public class Country {

    private int countryFlag;
    private String countryName;
    private String countryNameCode;
    private String countryPhoneCode;


    public Country(int countryFlag, String countryNameCode, String countryPhoneCode) {
        this.countryFlag = countryFlag;
        this.countryNameCode = countryNameCode;
        this.countryPhoneCode = countryPhoneCode;
    }

    public Country(int countryFlag, String countryName, String countryNameCode, String countryPhoneCode) {
        this.countryFlag = countryFlag;
        this.countryName = countryName;
        this.countryNameCode = countryNameCode;
        this.countryPhoneCode = countryPhoneCode;
    }

    public int getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(int countryFlag) {
        this.countryFlag = countryFlag;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryNameCode() {
        return countryNameCode;
    }

    public void setCountryNameCode(String countryNameCode) {
        this.countryNameCode = countryNameCode;
    }

    public String getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(String countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }
}
