package com.example.android.homey;

/**
 * Created by user on 02-Feb-18.
 */

public class TenantRequirementForumTemplate {
    private String mTenantDestination;
    private double mLatitude;
    private double mLongitude;
    private String mPlaceID;
    private int mDayCheckin;
    private int mMonthCheckin;
    private int mYearCheckin;
    private int mDayCheckOut;
    private int mMonthCheckout;
    private int mYearCheckout;
    private int mRooms;
    private int mAdults;
    private int mChildren;
    private String mPreferedDistance;
    public TenantRequirementForumTemplate(){
    }
    public TenantRequirementForumTemplate(String mTenantDestination, double mLatitude, double mLongitude, String mPlaceID, int mDayCheckin, int mMonthCheckin, int mYearCheckin, int mDayCheckOut, int mMonthCheckout, int mYearCheckout, int mRooms, int mAdults, int mChildren, String mPreferedDistance){
        this.mTenantDestination=mTenantDestination;
        this.mLatitude=mLatitude;
        this.mLongitude=mLongitude;
        this.mPlaceID=mPlaceID;
        this.mDayCheckin=mDayCheckin;
        this.mMonthCheckin=mMonthCheckin;
        this.mYearCheckin=mYearCheckin;
        this.mDayCheckOut=mDayCheckOut;
        this.mMonthCheckout=mMonthCheckout;
        this.mYearCheckout=mYearCheckout;
        this.mRooms=mRooms;
        this.mAdults=mAdults;
        this.mChildren=mChildren;
        this.mPreferedDistance=mPreferedDistance;
    }

    public String getmTenantDestination() {
        return mTenantDestination;
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

    public int getmDayCheckin() {
        return mDayCheckin;
    }

    public int getmMonthCheckin() {
        return mMonthCheckin;
    }

    public int getmYearCheckin() {
        return mYearCheckin;
    }

    public int getmDayCheckOut() {
        return mDayCheckOut;
    }

    public int getmMonthCheckout() {
        return mMonthCheckout;
    }

    public int getmYearCheckout() {
        return mYearCheckout;
    }

    public int getmRooms() {
        return mRooms;
    }

    public int getmAdults() {
        return mAdults;
    }

    public int getmChildren() {
        return mChildren;
    }

    public String getmPreferedDistance() {
        return mPreferedDistance;
    }

    public void setmTenantDestination(String mTenantDestination) {
        this.mTenantDestination = mTenantDestination;
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

    public void setmDayCheckin(int mDayCheckin) {
        this.mDayCheckin = mDayCheckin;
    }

    public void setmMonthCheckin(int mMonthCheckin) {
        this.mMonthCheckin = mMonthCheckin;
    }

    public void setmYearCheckin(int mYearCheckin) {
        this.mYearCheckin = mYearCheckin;
    }

    public void setmDayCheckOut(int mDayCheckOut) {
        this.mDayCheckOut = mDayCheckOut;
    }

    public void setmMonthCheckout(int mMonthCheckout) {
        this.mMonthCheckout = mMonthCheckout;
    }

    public void setmYearCheckout(int mYearCheckout) {
        this.mYearCheckout = mYearCheckout;
    }

    public void setmRooms(int mRooms) {
        this.mRooms = mRooms;
    }

    public void setmAdults(int mAdults) {
        this.mAdults = mAdults;
    }

    public void setmChildren(int mChildren) {
        this.mChildren = mChildren;
    }

    public void setmPreferedDistance(String mPreferedDistance) {
        this.mPreferedDistance = mPreferedDistance;
    }
}
