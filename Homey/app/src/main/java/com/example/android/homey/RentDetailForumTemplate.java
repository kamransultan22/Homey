package com.example.android.homey;

/**
 * Created by user on 02-Feb-18.
 */

public class RentDetailForumTemplate {
    private String mAddress;
    private String mCity;
    private String mState;
    private String mZipCode;
    private double mLatitude;
    private double mLongitude;
    private String mPlaceID;
    private int mRooms;
    private boolean mACRooms;
    private boolean mAttachedWashrooms;
    private boolean mParking;
    private boolean mWifi;
    private String mDescription;
    private String mPrice;

    public RentDetailForumTemplate() {
    }

    public RentDetailForumTemplate(String mAddress, String mCity, String mState, String mZipCode, double mLatitude, double mLongitude, String mPlaceID,int mRooms, boolean mACRooms, boolean mAttachedWashrooms, boolean mParking, boolean mWifi, String mDescription, String mPrice){
        this.mAddress=mAddress;
        this.mCity=mCity;
        this.mState=mState;
        this.mZipCode=mZipCode;
        this.mLatitude=mLatitude;
        this.mLongitude=mLongitude;
        this.mPlaceID=mPlaceID;
        this.mRooms=mRooms;
        this.mACRooms=mACRooms;
        this.mAttachedWashrooms=mAttachedWashrooms;
        this.mParking=mParking;
        this.mWifi=mWifi;
        this.mDescription=mDescription;
        this.mPrice=mPrice;
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
    public double getmLatitude() {
        return mLatitude;
    }
    public double getmLongitude() {
        return mLongitude;
    }

    public String getmPlaceID() {
        return mPlaceID;
    }

    public int getmRooms() {
        return mRooms;
    }
    public boolean getmACRooms(){return mACRooms;}
    public boolean getmAttachedWashrooms(){return mAttachedWashrooms;}
    public boolean getmParking(){return mParking;}
    public boolean getmWifi(){return mWifi;}

    public String getmDescription() {
        return mDescription;
    }

    public String getmPrice() {
        return mPrice;
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

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public void setmPlaceID(String mPlaceID) {
        this.mPlaceID = mPlaceID;
    }

    public void setmRooms(int mRooms) {
        this.mRooms = mRooms;
    }

    public void setmACRooms(boolean mACRooms) {
        this.mACRooms = mACRooms;
    }

    public void setmAttachedWashrooms(boolean mAttachedWashrooms) {
        this.mAttachedWashrooms = mAttachedWashrooms;
    }

    public void setmParking(boolean mParking) {
        this.mParking = mParking;
    }

    public void setmWifi(boolean mWifi) {
        this.mWifi = mWifi;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }
}