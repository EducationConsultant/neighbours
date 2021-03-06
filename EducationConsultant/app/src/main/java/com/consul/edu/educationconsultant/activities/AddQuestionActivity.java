package com.consul.edu.educationconsultant.activities;

import android.Manifest;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.consul.edu.educationconsultant.R;

import com.consul.edu.educationconsultant.asyncTasks.QuestionAddTask;
import com.consul.edu.educationconsultant.model.Question;
import com.consul.edu.educationconsultant.model.User;
import com.consul.edu.educationconsultant.retrofit.RedditAPI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddQuestionActivity extends AppCompatActivity {

    private static final String TAG = "AddQuestAct";

    private Button btnAdd;

    private EditText question;
    private EditText ansOne;
    private EditText ansTwo;
    private EditText ansThree;
    private EditText ansFour;
    //private EditText eduLevel;
    //private EditText category;
    private Spinner eduLevel;
    private Spinner category;

    private Toolbar toolbar;
    private ActionBar actionBar;


    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    private SharedPreferences sharedPreferences;
    private String sharedPrefName;

    // -- location --
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String longitude = "";
    private String latitude = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        progressBar = findViewById(R.id.progressBar);

        sharedPrefName = "currentUser";
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);

        // -- location --
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Location: ", location.toString());
                Log.d("Longitude: ", String.valueOf(location.getLongitude()));
                longitude = String.valueOf(location.getLongitude());
                latitude = String.valueOf(location.getLatitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        }; // end listener
        // ----

        /*
        * config spinner for education levels
        */
        eduLevel = (Spinner) findViewById(R.id.edu_level);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> eduAdapter = ArrayAdapter.createFromResource(this,
                R.array.edu_levels_arr, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        eduAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        eduLevel.setAdapter(eduAdapter);

        /*
        * config spinner for categories
         */
        category = (Spinner) findViewById(R.id.category);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_arr, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        category.setAdapter(categoryAdapter);

        question = (EditText) findViewById(R.id.question);
        ansOne = (EditText) findViewById(R.id.ans_one);
        ansTwo = (EditText) findViewById(R.id.ans_two);
        ansThree = (EditText) findViewById(R.id.ans_three);
        ansFour = (EditText) findViewById(R.id.ans_four);
        eduLevel = (Spinner) findViewById(R.id.edu_level);
        category = (Spinner) findViewById(R.id.category);

        toolbar = (Toolbar) findViewById(R.id.toolbar_add_question);
        toolbar.setTitleTextColor(Color.WHITE);
        // To get the toolbar to behave like an app bar. Parameter: the toolbar you want to set as the activity’s app bar
        setSupportActionBar(toolbar);

        // getSupportActionBar: using the toolbar from the Support Library
        actionBar = getSupportActionBar();
        // This enables the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_close);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
    }


/**
     * TODO
     * Spinner choice made listener
     */



//    /**
//     * When button add clicked
//     */
//    public void onClickAddQuestionBtn(View view){
//        question = findViewById(R.id.question);
//        ansOne = findViewById(R.id.ans_one);
//        ansTwo = findViewById(R.id.ans_two);
//        ansThree = findViewById(R.id.ans_three);
//        ansFour = findViewById(R.id.ans_four);
//        eduLevel = findViewById(R.id.edu_level);
//        category = findViewById(R.id.category);
//
//        btnAdd = findViewById(R.id.btn_add);
//
//        if(btnAdd != null){
//            Question newQuestion = new Question();
//
//            String questionStr = question.getText().toString().trim();
//            String ansOneStr = ansOne.getText().toString().trim();
//            String ansTwoStr = ansTwo.getText().toString().trim();
//            String ansThreeStr = ansThree.getText().toString().trim();
//            String ansFourStr = ansFour.getText().toString().trim();
//            String eduLevelStr = "foo"; //eduLevel.getText().toString().trim();
//            String categoryStr = "bar";//category.getText().toString().trim();
//
//            /*
//             * check if all fields are field out
//             */
//            if (TextUtils.isEmpty(questionStr)){
//                return;
//            }
//
//            if (TextUtils.isEmpty(ansOneStr)){
//                return;
//            }
//
//            if (TextUtils.isEmpty(ansTwoStr)){
//                return;
//            }
//
//            if (TextUtils.isEmpty(ansThreeStr)){
//                return;
//            }
//
//            if (TextUtils.isEmpty(ansFourStr)){
//                return;
//            }
//
//            if (TextUtils.isEmpty(eduLevelStr)){
//                return;
//            }
//
//            if (TextUtils.isEmpty(categoryStr)){
//                return;
//            }
//
//            newQuestion.setDescription(questionStr);
//            newQuestion.setAnswer1(ansOneStr);
//            newQuestion.setAnswer2(ansTwoStr);
//            newQuestion.setAnswer3(ansThreeStr);
//            newQuestion.setAnswer4(ansFourStr);
//            newQuestion.setEduLevel(eduLevelStr);
//            newQuestion.setCategory(categoryStr);
//            newQuestion.setCorrectAns(ansOneStr);
//            newQuestion.setAnswered("");
//
//            /*
//            * TODO
//            * Here should go sending the data to server and storing it
//            */
//
//            // go back to home
//            Intent questionList = new Intent(AddQuestionActivity.this, NavigationDrawerActivity.class);
//            startActivity(questionList);
//        }
//    }


    @Override
    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.GONE);

        // -- location --
        // check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // Ask for permissions
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 99);

        }else{
            // permissions already accepted keep requesting the location
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        // ----
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop checking for location while paused
        locationManager.removeUpdates(locationListener);
    }

    /**
     * This method runs when the app bar’s menu gets created.
     *
     * @param menu Java representation of the menu resource file
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds items to the app bar
        getMenuInflater().inflate(R.menu.menu_add_question,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Implementing this method makes activity react when an action in the app bar is clicked.
     *
     * @param item Represents the action on the app bar that was clicked
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_question:
                Question newQuestion = new Question();

                String questionStr = question.getText().toString().trim();
                String ansOneStr = ansOne.getText().toString().trim();
                String ansTwoStr = ansTwo.getText().toString().trim();
                String ansThreeStr = ansThree.getText().toString().trim();
                String ansFourStr = ansFour.getText().toString().trim();
                String eduLevelStr = eduLevel.getSelectedItem().toString();
                String categoryStr = category.getSelectedItem().toString();

                /*
                 * check if all fields are field out
                 *
                 */
                if (TextUtils.isEmpty(questionStr)){
                    Toast.makeText(getApplicationContext(), R.string.msg_enter_question, Toast.LENGTH_LONG).show();
                    return false;
                }

                if (TextUtils.isEmpty(ansOneStr)){
                    Toast.makeText(getApplicationContext(), R.string.msg_enter_ans_one, Toast.LENGTH_LONG).show();
                    return false;
                }

                if (TextUtils.isEmpty(ansTwoStr)){
                    Toast.makeText(getApplicationContext(), R.string.msg_enter_ans_two, Toast.LENGTH_LONG).show();
                    return false;
                }

                if (TextUtils.isEmpty(ansThreeStr)){
                    Toast.makeText(getApplicationContext(), R.string.msg_enter_ans_three, Toast.LENGTH_LONG).show();
                    return false;
                }

                if (TextUtils.isEmpty(ansFourStr)){
                    Toast.makeText(getApplicationContext(), R.string.msg_enter_ans_four, Toast.LENGTH_LONG).show();
                    return false;
                }

                if (TextUtils.isEmpty(eduLevelStr)){
                    return false;
                }

                if (TextUtils.isEmpty(categoryStr)){
                    return false;
                }
                
                String firstName = sharedPreferences.getString("user_first_name", "");
                String lastName = sharedPreferences.getString("user_last_name", "");

                User owner = new User(sharedPreferences.getLong("user_id", -1L),firstName,lastName,firebaseUser.getEmail(),sharedPreferences.getString("user_password", ""));

                newQuestion.setOwner(owner);
                newQuestion.setDescription(questionStr);
                newQuestion.setAnswer1(ansOneStr);
                newQuestion.setAnswer2(ansTwoStr);
                newQuestion.setAnswer3(ansThreeStr);
                newQuestion.setAnswer4(ansFourStr);
                newQuestion.setEduLevel(eduLevelStr);
                newQuestion.setCategory(categoryStr);
                newQuestion.setLatitude(latitude);
                newQuestion.setLongitude(longitude);


                /*
                 * TODO
                 * Here should go sending the data to server and storing it
                 */
                new QuestionAddTask(sharedPreferences).execute(newQuestion);


                // NOTIFICATION
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(RedditAPI.BASE_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                RedditAPI redditAPI = retrofit.create(RedditAPI.class);
//                Call<JsonElement> callNotification = redditAPI.sendNotification();
//                callNotification.enqueue(new Callback<JsonElement>() {
//                    @Override
//                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                        Log.d(TAG, "onNotificationResponse: Server Response: " + response.toString());
//                    }
//                    @Override
//                    public void onFailure(Call<JsonElement> call, Throwable t) {
//                        Log.e(TAG, "onNotificationFailure: Something went wrong: " + t.getMessage() );
//                    }
//                });


                // go back to home
                Intent questionList = new Intent(AddQuestionActivity.this, NavigationDrawerActivity.class);
                startActivity(questionList);
                finish();

                // Returning true tells Android you're dealt with the item being clicked
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
