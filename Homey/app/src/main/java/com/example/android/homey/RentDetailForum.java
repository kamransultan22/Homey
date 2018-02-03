package com.example.android.homey;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.regex.Pattern;

/**
 * Created by user on 31-Jan-18.
 */

public class RentDetailForum extends AppCompatActivity {

    private int PLACE_PICKER_REQUEST = 1;
    private Button property_location;

    private EditText mAddressEditText;
    private EditText mCityEditText;
    private Spinner mStateSpinner;
    private EditText mZipCodeEditText;
    private CheckBox mACRoomsCheckBox;
    private CheckBox mAttachedWashroomsCheckBox;
    private CheckBox mParkingCheckBox;
    private CheckBox mWifiCheckBox;
    private EditText mDescriptionEditText;
    private EditText mPriceEditText;

    private String mAddress;
    private String mCity;
    private String mState;
    private String mZipCode;
    private boolean mACRooms;
    private boolean mAttachedWashrooms;
    private boolean mParking;
    private boolean mWifi;
    private String mDescription;
    private String mPrice;

    private int room = 1;
    private TextView roomTextView;

    private LatLng latLng;
    private double mLatitude;
    private double mLongitude;
    private String mPlaceID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_detail_forum);

        roomTextView = (TextView) findViewById(R.id.avl_room_text_view);

        property_location = (Button) findViewById(R.id.property_location);
        property_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(RentDetailForum.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        mAddressEditText = (EditText) findViewById(R.id.edit_rent_address);
        mCityEditText = (EditText) findViewById(R.id.edit_rent_city);
        mZipCodeEditText = (EditText) findViewById(R.id.edit_rent_zip_code);
        mACRoomsCheckBox = (CheckBox) findViewById(R.id.ac_rooms_checkbox);
        mAttachedWashroomsCheckBox = (CheckBox) findViewById(R.id.attached_washrooms_checkbox);
        mParkingCheckBox = (CheckBox) findViewById(R.id.parking_checkbox);
        mWifiCheckBox = (CheckBox) findViewById(R.id.wifi_checkbox);
        mDescriptionEditText = (EditText) findViewById(R.id.edit_description);
        mPriceEditText = (EditText) findViewById(R.id.edit_price);

        mStateSpinner = (Spinner) findViewById(R.id.spinner_state1);
        setupStateSpinner();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                latLng = place.getLatLng();
                mPlaceID=place.getId();
            }
        }
    }

    /**
     * Setup the dropdown spinner that allows the user to select the state.
     */
    private void setupStateSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter stateSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_state_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        stateSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mStateSpinner.setAdapter(stateSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mState = (String) parent.getItemAtPosition(position);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mState = "Select"; // Unknown
            }
        });
    }

    private void extractUserInputsFromUI() {
        mAddress = mAddressEditText.getText().toString().trim();
        mCity = mCityEditText.getText().toString().trim();
        mZipCode = mZipCodeEditText.getText().toString().trim();
        mLatitude = latLng.latitude;
        mLongitude = latLng.longitude;
        mACRooms = mACRoomsCheckBox.isChecked();
        mAttachedWashrooms = mAttachedWashroomsCheckBox.isChecked();
        mParking = mParkingCheckBox.isChecked();
        mWifi = mWifiCheckBox.isChecked();
        mDescription = mDescriptionEditText.getText().toString().trim();
        mPrice = mPriceEditText.getText().toString().trim();
    }

    private void saveUserDetails() {
        RentDetailForumTemplate rentDetailForumTemplate = new RentDetailForumTemplate(mAddress, mCity, mState, mZipCode, mLatitude,mLongitude,mPlaceID,room,mACRooms,mAttachedWashrooms,mParking,mWifi,mDescription,mPrice);
        FirebaseSDKs.mDatabaseReference.child(FirebaseSDKs.user.getUid()).child("user_landlord_info").setValue(rentDetailForumTemplate);

        //UserTemplate userTemplate = new UserTemplate(true);
        //FirebaseSDKs.mDatabaseReference.child(FirebaseSDKs.user.getUid()).child("check_if_fill").setValue(userTemplate);

        //Intent intent = new Intent(Signup.this, Decision.class);
        //startActivity(intent);
    }

    private boolean checkUserInputs() {
        int flag = 1;

        if ("".equals(mAddress)) {
            mAddressEditText.setError("You can't leave this empty.");
            flag = 0;
        } else if (mAddress.length() < 5) {
            mAddressEditText.setError("Address is too short.");
            flag = 0;
        }

        if ("".equals(mCity)) {
            mCityEditText.setError("You can't leave this empty.");
            flag = 0;
        } else if (mCityEditText.length() < 3) {
            mCityEditText.setError("City is too short.");
            flag = 0;
        }

        if ("Select".equals(mState)) {
            ((TextView) mStateSpinner.getSelectedView()).setError("You can't leave this empty.");
            flag = 0;
        }

        if ("".equals(mZipCode)) {
            mZipCodeEditText.setError("You can't leave this empty.");
            flag = 0;
        } else if (mZipCode.length() < 6) {
            mZipCodeEditText.setError("ZipCode is too short.");
            flag = 0;
        }

        if (flag == 0) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_rent_detail_forum, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                extractUserInputsFromUI();
                if (checkUserInputs()) {
                    // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                    // Create a click listener to handle the user confirming that
                    // changes should be discarded.
                    DialogInterface.OnClickListener saveButtonClickListener =
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    saveUserDetails();
                                }
                            };

                    // Show a dialog that notifies the user they have unsaved changes
                    showUnsavedChangesDialog(saveButtonClickListener);
                }
                return true;

            case R.id.action_sign_out:
                AuthUI.getInstance().signOut(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener saveButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Save your changes and quit editing?");
        builder.setPositiveButton("Save", saveButtonClickListener);
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        room = room + 1;
        roomTextView.setText("" + room);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (room == 1) {
            Toast.makeText(this, "You cannot have less than 1 rooms", Toast.LENGTH_SHORT).show();
            return;
        }
        room = room - 1;
        roomTextView.setText("" + room);
    }
}
