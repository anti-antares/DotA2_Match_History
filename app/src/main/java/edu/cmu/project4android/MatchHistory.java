package edu.cmu.project4android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import com.google.gson.*;
import java.net.URLDecoder;

/**
 * The main class of the android app
 * The UI thread
 */
public class MatchHistory extends AppCompatActivity {
    // member variable to record the pictures
    Bitmap avatar;
    Bitmap hero1pic;
    Bitmap hero2pic;
    Bitmap hero3pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // launch
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set the class properly
        final MatchHistory mh = this;

        //initialize the button
        Button submitButton = (Button)findViewById(R.id.submit);

        //add listener to button
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {
                String searchID = ((EditText)findViewById(R.id.searchID)).getText().toString();
                GetMatchHistory gmh = new GetMatchHistory();
                mh.avatar=null;
                mh.hero3pic=null;
                mh.hero2pic=null;
                mh.hero1pic=null;
                gmh.search(searchID, mh); // Done asynchronously in another thread.  It calls ip.pictureReady() in this thread when complete.
            }
        });
    }

    /**
     * set the ui when the search is done
     * @param result
     * two cases:
     *      case 1: full result got - set all elements respectively
     *      case 2: no result found - reply "try again to user"
     */
    public void searchDone(String result){
        // initialize all the views
    ImageView avatarView = (ImageView) findViewById(R.id.avatar);
    ImageView hero1View = (ImageView) findViewById(R.id.hero1);
    ImageView hero2View = (ImageView) findViewById(R.id.hero2);
    ImageView hero3View = (ImageView) findViewById(R.id.hero3);
    TextView searchView = (EditText) findViewById(R.id.searchID);
    TextView lastlogoffView = (TextView) findViewById(R.id.last_logoff);
    TextView userView = (TextView) findViewById(R.id.user_name);
    TextView feedbackView = (TextView) findViewById(R.id.submitfeedback);
    TextView hero2nameView = (TextView) findViewById(R.id.heroname2);
    TextView hero1nameView = (TextView) findViewById(R.id.heroname1);
    TextView hero3nameView = (TextView) findViewById(R.id.heroname3);
    TextView hero1killView = (TextView) findViewById(R.id.kill1);
    TextView hero2killView = (TextView) findViewById(R.id.kill2);
    TextView hero3killView = (TextView) findViewById(R.id.kill3);
    TextView hero1deathView = (TextView) findViewById(R.id.death1);
    TextView hero2deathView = (TextView) findViewById(R.id.death2);
    TextView hero3deathView = (TextView) findViewById(R.id.death3);
    TextView hero1assView = (TextView) findViewById(R.id.ass1);
    TextView hero2assView = (TextView) findViewById(R.id.ass2);
    TextView hero3assView = (TextView) findViewById(R.id.ass3);
    TextView hero1levelView = (TextView) findViewById(R.id.level1);
    TextView hero2levelView = (TextView) findViewById(R.id.level2);
    TextView hero3levelView = (TextView) findViewById(R.id.level3);
    TextView hero1gpmView = (TextView) findViewById(R.id.gpm1);
    TextView hero2gpmView = (TextView) findViewById(R.id.gpm2);
    TextView hero3gpmView = (TextView) findViewById(R.id.gpm3);
    TextView hero1resultView = (TextView) findViewById(R.id.result1);
    TextView hero2resultView = (TextView) findViewById(R.id.result2);
    TextView hero3resultView = (TextView) findViewById(R.id.result3);
    // if full result is returned - parse the json and get all elements
        // and then set all the views
        if (result!=null) {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject returnData = parser.parse(result).getAsJsonObject();
            String nickName = null;
            try {
            nickName = URLDecoder.decode(returnData.get("nickName").getAsString(), "UTF-8");
            }
            catch (Exception e){}

//        Bitmap avatar = getRemoteImage(returnData.get("avatarURL").getAsString());
            String lastLogoff = returnData.get("lastLogin").getAsString();
//        Bitmap hero1pic = getRemoteImage(returnData.get("hero1URL").getAsString());
//        Bitmap hero2pic = getRemoteImage(returnData.get("hero2URL").getAsString());
//        Bitmap hero3pic = getRemoteImage(returnData.get("hero3URL").getAsString());
            String hero1kill = returnData.get("hero1Kill").getAsString();
            String hero2kill = returnData.get("hero2Kill").getAsString();
            String hero3kill = returnData.get("hero3Kill").getAsString();
            String hero1death = returnData.get("hero1Death").getAsString();
            String hero2death = returnData.get("hero2Death").getAsString();
            String hero3death = returnData.get("hero3Death").getAsString();
            String hero1ass = returnData.get("hero1Assists").getAsString();
            String hero2ass = returnData.get("hero2Assists").getAsString();
            String hero3ass = returnData.get("hero3Assists").getAsString();
            String hero1name = returnData.get("hero1Name").getAsString();
            String hero2name = returnData.get("hero2Name").getAsString();
            String hero3name = returnData.get("hero3Name").getAsString();
            String hero1level = returnData.get("hero1Level").getAsString();
            String hero2level = returnData.get("hero2Level").getAsString();
            String hero3level = returnData.get("hero3Level").getAsString();
            String hero1result = returnData.get("hero1Result").getAsString();
            String hero2result = returnData.get("hero2Result").getAsString();
            String hero3result = returnData.get("hero3Result").getAsString();
            String hero1gpm = returnData.get("hero1GPM").getAsString();
            String hero2gpm = returnData.get("hero2GPM").getAsString();
            String hero3gpm = returnData.get("hero3GPM").getAsString();

//            avatarView.invalidate();
//    hero1View.invalidate();



            userView.setText(nickName + ", welcome!");
            searchView.setText("");
            lastlogoffView.setText("Last log-off: " + lastLogoff);



            feedbackView.setText("");

            avatarView.setImageBitmap(avatar);
            avatarView.setVisibility(View.VISIBLE);
            hero3View.setImageBitmap(hero3pic);

            hero1View.setImageBitmap(hero1pic);
            hero2View.setImageBitmap(hero2pic);

            hero1View.setVisibility(View.VISIBLE);


            hero2View.setVisibility(View.VISIBLE);

            hero3View.setImageBitmap(hero3pic);
            hero3View.setVisibility(View.VISIBLE);


            hero1nameView.setText(hero1name);


            hero2nameView.setText(hero2name);


            hero3nameView.setText(hero3name);


            hero1killView.setText("Kill: " + hero1kill);


            hero2killView.setText("Kill: " + hero2kill);


            hero3killView.setText("Kill: " + hero3kill);


            hero1deathView.setText("Death: " + hero1death);


            hero2deathView.setText("Death: " + hero2death);


            hero3deathView.setText("Death: " + hero3death);


            hero1assView.setText("Assist: " + hero1ass);


            hero2assView.setText("Assist: " + hero2ass);


            hero3assView.setText("Assist: " + hero3ass);


            hero1levelView.setText("Level: " + hero1level);


            hero2levelView.setText("Level: " + hero2level);


            hero3levelView.setText("Level: " + hero3level);


            hero1gpmView.setText("GPM: " + hero1gpm);


            hero2gpmView.setText("GPM: " + hero2gpm);


            hero3gpmView.setText("GPM: " + hero3gpm);


            hero1resultView.setText(hero1result);


            hero2resultView.setText(hero2result);


            hero3resultView.setText(hero3result);
        }

        // if no result is returned - reply "No results" and display
        // and clear all views
        else {

            searchView.setText("");


            feedbackView.setText("Oops! No results found, please try again!");

            userView.setText("");
            lastlogoffView.setText("");

            avatarView.setImageBitmap(avatar);
            avatarView.setVisibility(View.VISIBLE);
            hero3View.setImageBitmap(hero3pic);

            hero1View.setImageBitmap(hero1pic);
            hero2View.setImageBitmap(hero2pic);

            hero1View.setVisibility(View.VISIBLE);


            hero2View.setVisibility(View.VISIBLE);

            hero3View.setImageBitmap(hero3pic);
            hero3View.setVisibility(View.VISIBLE);

            //set all other views blank
            hero1nameView.setText("");


            hero2nameView.setText("");


            hero3nameView.setText("");


            hero1killView.setText("");


            hero2killView.setText("");


            hero3killView.setText("");


            hero1deathView.setText("");


            hero2deathView.setText("");


            hero3deathView.setText("");


            hero1assView.setText("");


            hero2assView.setText("");


            hero3assView.setText("");


            hero1levelView.setText("");


            hero2levelView.setText("");


            hero3levelView.setText("");


            hero1gpmView.setText("");


            hero2gpmView.setText("");


            hero3gpmView.setText("");


            hero1resultView.setText("");


            hero2resultView.setText("");


            hero3resultView.setText("");
        }
    }
}
