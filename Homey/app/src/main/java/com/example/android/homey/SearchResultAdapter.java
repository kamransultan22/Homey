package com.example.android.homey;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by user on 03-Feb-18.
 */

public class SearchResultAdapter extends ArrayAdapter<DataSnapshot> {
    public SearchResultAdapter(Activity context, ArrayList<DataSnapshot> searchResultArray) {
        super(context, 0, searchResultArray);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        DataSnapshot currentSearchResult = getItem(position);

        TextView FullNameTextView = (TextView) listItemView.findViewById(R.id.search_full_name);
        FullNameTextView.setText((String) currentSearchResult.child("user_gen_info").child("mName").getValue());

        String address = (String) currentSearchResult.child("user_landlord_info").child("mAddress").getValue() + ", " +
                (String) currentSearchResult.child("user_landlord_info").child("mCity").getValue() + ", " +
                (String) currentSearchResult.child("user_landlord_info").child("mState").getValue() + "-" +
                (String) currentSearchResult.child("user_landlord_info").child("mZipCode").getValue();
        TextView AddressTextView = (TextView) listItemView.findViewById(R.id.search_address);
        AddressTextView.setText(address);

        TextView PriceTextView = (TextView) listItemView.findViewById(R.id.search_price);
        PriceTextView.setText("Rs "+ currentSearchResult.child("user_landlord_info").child("mPrice").getValue());

        return listItemView;
    }
}
