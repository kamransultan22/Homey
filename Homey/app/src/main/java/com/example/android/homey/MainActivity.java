package com.example.android.homey;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.android.homey.FirebaseSDKs.*;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static final int RC_SIGN_IN = 1;
    private UserTemplate userTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseSDKs.mFirebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseSDKs.mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseSDKs.mDatabaseReference = FirebaseSDKs.mFirebaseDatabase.getReference().child("users");

        FirebaseSDKs.mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseSDKs.user = firebaseAuth.getCurrentUser();
                if (FirebaseSDKs.user != null) {
                    // User is signed in
                    onSignedInInitialize();

                } else {
                    // User is signed out
                    //onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.PhoneBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.FacebookBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Sign-in succeeded, set up the UI
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // Sign in was canceled by the user, finish the activity
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseSDKs.mFirebaseAuth.addAuthStateListener(FirebaseSDKs.mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (FirebaseSDKs.mAuthStateListener != null) {
            FirebaseSDKs.mFirebaseAuth.removeAuthStateListener(FirebaseSDKs.mAuthStateListener);
        }
    }

    private void onSignedInInitialize() {
        FirebaseSDKs.mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(dataSnapshot.hasChild(FirebaseSDKs.user.getUid())) {
                        userTemplate = dataSnapshot.child(FirebaseSDKs.user.getUid()).child("check_if_fill").getValue(UserTemplate.class);
                        Log.v(TAG, "yash ========////======= : " + userTemplate.getGen_info_fill());
                    } else {
                        userTemplate = new UserTemplate(false);
                        Log.v(TAG, "yash22 ========////======= : " + userTemplate.getGen_info_fill());
                        FirebaseSDKs.mDatabaseReference.child(FirebaseSDKs.user.getUid()).child("check_if_fill").setValue(userTemplate);
                    }
                //}

                if (userTemplate.getGen_info_fill()) {
                    Intent intent = new Intent(MainActivity.this, Decision.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, Signup.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
