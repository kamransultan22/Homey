package com.example.android.homey;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by user on 03-Feb-18.
 */

public class TenantSearchResultActivity extends AppCompatActivity {

    private Location locationA = new Location("point A");
    private Location locationB = new Location("point B");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_search_result);

        final ArrayList<DataSnapshot> searchResultArray = new ArrayList<>();
        FirebaseSDKs.mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(FirebaseSDKs.user.getUid())) {
                    locationA.setLatitude((double) dataSnapshot.child(FirebaseSDKs.user.getUid()).child("user_tenant_info").child("mLatitude").getValue());
                    locationA.setLongitude((double) dataSnapshot.child(FirebaseSDKs.user.getUid()).child("user_tenant_info").child("mLongitude").getValue());
                }
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(!ds.getValue().equals(FirebaseSDKs.user.getUid()) && ds.hasChild("user_landlord_info")){
                        locationB.setLatitude((double) ds.child("user_landlord_info").child("mLatitude").getValue());
                        locationB.setLongitude((double) ds.child("user_landlord_info").child("mLongitude").getValue());
                        Log.v("yash////////",locationA.distanceTo(locationB)/1000+"");
                        if(locationA.distanceTo(locationB)/1000<=Float.parseFloat((String) dataSnapshot.child(FirebaseSDKs.user.getUid()).child("user_tenant_info").child("mPreferedDistance").getValue())){
                            Log.v("yashback////////",searchResultArray.size()+"");
                            searchResultArray.add(ds);
                            Log.v("yashafter////////",searchResultArray.size()+"");
                        }
                    }
                }
                SearchResultAdapter adapter = new SearchResultAdapter(TenantSearchResultActivity.this, searchResultArray);
                ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_tenant_search_result_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {

            // Respond to a click on the menu option
            case R.id.action_sign_out:
                AuthUI.getInstance().signOut(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
