package com.example.easylease.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easylease.Apartment;
import com.example.easylease.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileActivity";
    private EditText etName;
    private EditText etEmail_profile;
    private EditText etApartment;
    private Button btnUpdate;
    ParseObject aptObject;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setContentView(R.layout.activity_profile);
        etName = view.findViewById(R.id.etName);
        etEmail_profile = view.findViewById(R.id.etEmail_profile);
        etApartment = view.findViewById(R.id.etApartment);
        btnUpdate = view.findViewById(R.id.btnUpdate);

        loadDetails();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Updating profile...");

                updateProfile(etName.getText().toString(),etEmail_profile.getText().toString(),etApartment.getText().toString());
            }
        });
    }

    private void updateProfile(String name, String email, String apt_no) {
        ParseUser user = ParseUser.getCurrentUser();
        user.put("full_name",name);
        user.put("email",email);
        aptObject = Apartment.setApartmentObject(Integer.parseInt(apt_no));



        if(aptObject==null){
            Toast.makeText(getContext(), "Apartment No. doesn't exist", Toast.LENGTH_SHORT).show();
            return;
        }

        user.put("Apartment_no",aptObject);

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    loadDetails();
                }
                else{
                    Toast.makeText(getContext(), "Couldn't update profile data, try again", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void loadDetails(){
        ParseUser user = ParseUser.getCurrentUser();
        /*User user = ParseUser.getCurrentUser();
        Apartment apt = new Apartment();*/
        etName.setText(user.get("full_name").toString());
        etEmail_profile.setText(user.getEmail().toString());
        ParseObject apt = (ParseObject) user.get("Apartment_no");
        //ParseObject apt = Apartment.setApartmentObject();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Apartment");

        // The query will search for a ParseObject, given its objectId.
        // When the query finishes running, it will invoke the GetCallback
        // with either the object, or the exception thrown
        query.getInBackground(apt.getObjectId(), (object, e) -> {
            if (e == null) {
                //Object was successfully retrieved
                System.out.println(object);
                etApartment.setText(String.valueOf(object.get("Number")));
            } else {
                // something went wrong
                System.out.println("came hererer");
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
