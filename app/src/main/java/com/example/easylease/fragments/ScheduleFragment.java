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

import com.example.easylease.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ScheduleFragment extends Fragment {

    public static final String TAG = "ScheduleActivity";
    private EditText name, date, category;
    private Button submit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        date = view.findViewById(R.id.scDate);
        category = view.findViewById(R.id.scCategory);
        submit = view.findViewById(R.id.submit_schedule);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onCLick login button");
                Toast.makeText(getContext(),"Event Scheduled Successfully",Toast.LENGTH_SHORT).show();
                //Put current user name into scName textbox
                ParseUser user = ParseUser.getCurrentUser();
                String nameEntry = user.getUsername();
                Log.i("nameOut", nameEntry);

                String cat = category.getText().toString();
                String dat = date.getText().toString();
                clearFields();
                //raiseRequest(nameEntry, cat, dat);
            }
        });
    }

    private void raiseRequest(String name, String cat, String date) {
        ParseObject entity = new ParseObject("Schedule");
        ParseUser user = ParseUser.getCurrentUser();

        entity.put("Name", name);
        entity.put("DateTime", date);
        entity.put("Category", cat);

        // Saves the new object.
        // Notice that the SaveCallback is totally optional!
        /*entity.saveInBackground(e -> {
            if (e==null){
                //Save was done
            }else{
                //Something went wrong
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/

        entity.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    //Save was done
                    Toast.makeText(getContext(),"Request submitted!",Toast.LENGTH_SHORT).show();
                    clearFields();
                }else{
                    //Something went wrong
                    Toast.makeText(getContext(),"Couldn't submit request, Try again",Toast.LENGTH_SHORT).show();
                }
            }
        } );
    }

    private void clearFields() {
        //name.setText("");
        category.setText("");
        date.setText("");


    }
}
