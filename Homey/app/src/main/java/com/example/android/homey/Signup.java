package com.example.android.homey;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by user on 29-Jan-18.
 */

public class Signup extends AppCompatActivity {

    private EditText mNameEditText;
    private Spinner mGenderSpinner;
    private EditText mAddressEditText;
    private EditText mCityEditText;
    private Spinner mStateSpinner;
    private EditText mZipCodeEditText;
    private EditText mPhoneNumberEditText;
    private EditText mEmailEditText;

    private String mName;
    private String mGender;
    private String mAddress;
    private String mCity;
    private String mState;
    private String mZipCode;
    private String mPhoneNumber;
    private String mEmail;
    private static int mDayDOB;
    private static int mMonthDOB;
    private static int mYearDOB;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mNameEditText = (EditText) findViewById(R.id.edit_name);
        mAddressEditText = (EditText) findViewById(R.id.edit_address);
        mCityEditText = (EditText) findViewById(R.id.edit_city);
        mZipCodeEditText = (EditText) findViewById(R.id.edit_zip_code);
        mPhoneNumberEditText = (EditText) findViewById(R.id.edit_phone_number);
        mEmailEditText = (EditText) findViewById(R.id.edit_email);

        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        mStateSpinner = (Spinner) findViewById(R.id.spinner_state);
        setupGenderSpinner();
        setupStateSpinner();
    }


    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupGenderSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGender = (String) parent.getItemAtPosition(position);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = "Select"; // Unknown
            }
        });
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            mYearDOB = year;
            mMonthDOB = month+1;
            mDayDOB = day;
        }
    }

    private void extractUserInputsFromUI() {
        mName = mNameEditText.getText().toString().trim();
        mAddress = mAddressEditText.getText().toString().trim();
        mCity = mCityEditText.getText().toString().trim();
        mZipCode = mZipCodeEditText.getText().toString().trim();
        mPhoneNumber = mPhoneNumberEditText.getText().toString().trim();
        mEmail = mEmailEditText.getText().toString().trim();
    }

    private void saveUserDetails() {
        UserGeneralDetails userGeneralDetails = new UserGeneralDetails(mName, mGender, mDayDOB, mMonthDOB, mYearDOB, mAddress, mCity, mState, mZipCode, mPhoneNumber, mEmail);
        FirebaseSDKs.mDatabaseReference.child(FirebaseSDKs.user.getUid()).child("user_gen_info").setValue(userGeneralDetails);

        UserTemplate userTemplate = new UserTemplate(true);
        FirebaseSDKs.mDatabaseReference.child(FirebaseSDKs.user.getUid()).child("check_if_fill").setValue(userTemplate);

        Intent intent = new Intent(Signup.this, Decision.class);
        startActivity(intent);
    }

    private boolean checkUserInputs() {
        int flag=1;

        if("".equals(mName)){
            mNameEditText.setError("You can't leave this empty.");
            flag=0;
        }else if (mName.length()<2) {
            mNameEditText.setError("Name is too short.");
            flag=0;
        }

        if("Select".equals(mGender)){
            ((TextView) mGenderSpinner.getSelectedView()).setError("You can't leave this empty.");
            flag=0;
        }

        if("".equals(mAddress)){
            mAddressEditText.setError("You can't leave this empty.");
            flag=0;
        }else if (mAddress.length()<5) {
            mAddressEditText.setError("Address is too short.");
            flag=0;
        }

        if("".equals(mCity)){
            mCityEditText.setError("You can't leave this empty.");
            flag=0;
        }else if (mCityEditText.length()<3) {
            mCityEditText.setError("City is too short.");
            flag=0;
        }

        if("Select".equals(mState)){
            ((TextView) mStateSpinner.getSelectedView()).setError("You can't leave this empty.");
            flag=0;
        }

        if("".equals(mZipCode)){
            mZipCodeEditText.setError("You can't leave this empty.");
            flag=0;
        }else if (mName.length()<6) {
            mZipCodeEditText.setError("ZipCode is too short.");
            flag=0;
        }

        if("".equals(mPhoneNumber)){
            mPhoneNumberEditText.setError("You can't leave this empty.");
            flag=0;
        } else if(mPhoneNumber.length()<10 || !Pattern.compile("^[7-9]").matcher(mPhoneNumber).find()) {
            mPhoneNumberEditText.setError("Please enter valid mobile no.");
            flag=0;
        }

        if ("".equals(mEmail)){
            mEmailEditText.setError("You can't leave this empty.");
            flag=0;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()){
            mEmailEditText.setError("Please enter valid email.");
            flag=0;
        }

        if(flag == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
                if(checkUserInputs()) {
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
}