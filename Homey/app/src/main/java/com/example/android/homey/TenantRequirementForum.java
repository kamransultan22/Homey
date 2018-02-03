package com.example.android.homey;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by user on 31-Jan-18.
 */

public class TenantRequirementForum extends AppCompatActivity {

    private int PLACE_PICKER_REQUEST = 1;

    private DatePickerDialog.OnDateSetListener checkin_dateListener, checkout_dateListener;
    private static EditText check_in_btn;
    private static EditText check_out_btn;
    private DatePickerDialogFragment mDatePickerDialogFragment;

    private int room = 1;
    private int adults = 1;
    private int children = 0;
    private TextView roomTextView;
    private TextView adultsTextView;
    private TextView childrenTextView;

    LatLng latLng;

    private EditText mTenantDestinationEditText;
    private EditText mPreferedDistanceEditText;

    private String mTenantDestination;
    private double mLatitude;
    private double mLongitude;
    private String mPlaceID;
    private static int mDayCheckin;
    private static int mMonthCheckin;
    private static int mYearCheckin;
    private static int mDayCheckOut;
    private static int mMonthCheckout;
    private static int mYearCheckout;
    private String mPreferedDistance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenant_requirement_forum);

        roomTextView = (TextView) findViewById(R.id.room_text_view);
        adultsTextView = (TextView) findViewById(R.id.adults_text_view);
        childrenTextView = (TextView) findViewById(R.id.children_text_view);

        mPreferedDistanceEditText = (EditText) findViewById(R.id.edit_prefered_distance);

        mTenantDestinationEditText = (EditText) findViewById(R.id.tenant_destination);
        mTenantDestinationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(TenantRequirementForum.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        check_in_btn = (EditText) findViewById(R.id.check_in_date);
        check_out_btn = (EditText) findViewById(R.id.check_out_date);

        mDatePickerDialogFragment = new DatePickerDialogFragment();
        check_in_btn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_START_DATE);
                    mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
                }
            }
        });
        check_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_START_DATE);
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        check_out_btn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_END_DATE);
                    mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
                }
            }
        });
        check_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialogFragment.setFlag(DatePickerDialogFragment.FLAG_END_DATE);
                mDatePickerDialogFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                CharSequence name = place.getAddress();
                latLng = place.getLatLng();
                mPlaceID = place.getId();
                mTenantDestinationEditText.setText(name);
            }
        }
    }

    public static class DatePickerDialogFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        public static final int FLAG_START_DATE = 0;
        public static final int FLAG_END_DATE = 1;

        private int flag = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void setFlag(int i) {
            flag = i;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            if (flag == FLAG_START_DATE) {
                mDayCheckin = dayOfMonth;
                mMonthCheckin = monthOfYear+1;
                mYearCheckin = year;
                check_in_btn.setText(format.format(calendar.getTime()));
            } else if (flag == FLAG_END_DATE) {
                mDayCheckOut = dayOfMonth;
                mMonthCheckout = monthOfYear+1;
                mYearCheckout = year;
                check_out_btn.setText(format.format(calendar.getTime()));
            }
        }
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (view.getId() == R.id.inc_room) {
            room = room + 1;
            roomTextView.setText("" + room);
        } else if (view.getId() == R.id.inc_adults) {
            adults = adults + 1;
            adultsTextView.setText("" + adults);
        } else if (view.getId() == R.id.inc_children) {
            children = children + 1;
            childrenTextView.setText("" + children);
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (view.getId() == R.id.dec_room) {
            if (room == 1) {
                Toast.makeText(this, "You cannot have less than 1 rooms", Toast.LENGTH_SHORT).show();
                return;
            }
            room = room - 1;
            roomTextView.setText("" + room);
        } else if (view.getId() == R.id.dec_adults) {
            if (adults == 1) {
                Toast.makeText(this, "You cannot have less than 1 adult", Toast.LENGTH_SHORT).show();
                return;
            }
            adults = adults - 1;
            adultsTextView.setText("" + adults);
        } else if (view.getId() == R.id.dec_children) {
            if (children == 0) {
                Toast.makeText(this, "You cannot have less than 0 children", Toast.LENGTH_SHORT).show();
                return;
            }
            children = children - 1;
            childrenTextView.setText("" + children);
        }
    }

    private void extractUserInputsFromUI() {
        mTenantDestination = mTenantDestinationEditText.getText().toString().trim();
        mLatitude = latLng.latitude;
        mLongitude = latLng.longitude;
        mPreferedDistance = mPreferedDistanceEditText.getText().toString().trim();
    }

    private void saveUserDetails() {
        TenantRequirementForumTemplate tenantRequirementForumTemplate = new TenantRequirementForumTemplate(mTenantDestination, mLatitude, mLongitude, mPlaceID, mDayCheckin, mMonthCheckin, mYearCheckin, mDayCheckOut, mMonthCheckout, mYearCheckout, room, adults, children, mPreferedDistance);
        FirebaseSDKs.mDatabaseReference.child(FirebaseSDKs.user.getUid()).child("user_tenant_info").setValue(tenantRequirementForumTemplate);

        //UserTemplate userTemplate = new UserTemplate(true);
        //FirebaseSDKs.mDatabaseReference.child(FirebaseSDKs.user.getUid()).child("check_if_fill").setValue(userTemplate);

        Intent intent = new Intent(TenantRequirementForum.this, TenantSearchResultActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_tenant_requirement_forum, menu);
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
                //if(checkUserInputs()) {
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
                //}
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
        builder.setMessage("Search and quit editing?");
        builder.setPositiveButton("Search", saveButtonClickListener);
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
