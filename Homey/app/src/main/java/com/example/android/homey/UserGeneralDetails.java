package com.example.android.homey;

/**
 * Created by user on 31-Jan-18.
 */

public class UserGeneralDetails {

    private String mName;
    private String mGender;
    private int mDay;
    private int mMonth;
    private int mYear;
    private String mAddress;
    private String mCity;
    private String mState;
    private String mZipCode;
    private String mPhoneNumber;
    private String mEmail;

    public UserGeneralDetails() {

    }

    public UserGeneralDetails(String mName, String mGender, int mDay, int mMonth, int mYear, String mAddress, String mCity, String mState, String mZipCode, String mPhoneNumber, String mEmail) {
        this.mName = mName;
        this.mGender = mGender;
        this.mDay = mDay;
        this.mMonth = mMonth;
        this.mYear = mYear;
        this.mAddress = mAddress;
        this.mCity = mCity;
        this.mState = mState;
        this.mZipCode = mZipCode;
        this.mPhoneNumber = mPhoneNumber;
        this.mEmail = mEmail;
    }

    public String getmName() {
        return mName;
    }

    public String getmGender() {
        return mGender;
    }

    public int getmDay() {
        return mDay;
    }

    public int getmMonth() {
        return mMonth;
    }

    public int getmYear() {
        return mYear;
    }

    public String getmAddress() {
        return mAddress;
    }

    public String getmCity() {
        return mCity;
    }

    public String getmState() {
        return mState;
    }

    public String getmZipCode() {
        return mZipCode;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public void setmZipCode(String mZipCode) {
        this.mZipCode = mZipCode;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
