package com.example.easylease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ScheduleActivity extends AppCompatActivity {

    public static final String TAG = "ScheduleActivity";
    private EditText name, date, category;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        //name = findViewById(R.id.scName);
        date = findViewById(R.id.scDate);
        category = findViewById(R.id.scCategory);
        submit = findViewById(R.id.submit_schedule);

        Log.i(TAG, "entereddd");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onCLick login button");
                Toast.makeText(getApplicationContext(),"Event Scheduled Successfully",Toast.LENGTH_SHORT).show();
                //Put current user name into scName textbox
                ParseUser user = ParseUser.getCurrentUser();
                String nameEntry = user.getUsername();
                Log.i("nameOut", nameEntry);

                String cat = category.getText().toString();
                String dat = date.getText().toString();

                raiseRequest(nameEntry, cat, dat);
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
                    Toast.makeText(getApplicationContext(),"Request submitted!",Toast.LENGTH_SHORT).show();
                    clearFields();
                }else{
                    //Something went wrong
                    Toast.makeText(getApplicationContext(),"Couldn't submit request, Try again",Toast.LENGTH_SHORT).show();
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