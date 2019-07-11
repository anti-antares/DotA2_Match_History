package edu.cmu.project4android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLDecoder;

/**
 * Class GetMatchHistory provides the helper thread to fetch elements to fulfill the android applications
 *
 */
public class GetMatchHistory {
    MatchHistory mh = new MatchHistory();

    // Execute the AsyncMatchSearch() method
    public void search(String searchID, MatchHistory mh){
        this.mh = mh;
        new AsyncMatchSearch().execute(searchID);
    }

    // Simple Result class to help store the value
    class Result {
        String value;
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }

    // request the result json from the web service and parse the bitmaps
    // and pass to the android layout
    private class AsyncMatchSearch extends AsyncTask<String, Void, String>{
        Bitmap tempAvatar;
        Bitmap tempHero1pic;
        Bitmap tempHero2pic;
        Bitmap tempHero3pic;

        // doInBackground
        // pass the bitmap to the member variables of the main thread
        // and pass to result as returned output
        protected String doInBackground(String... urls) {
            String result = search(urls[0]);
            JsonParser parser = new JsonParser();
            try {
                JsonObject returnData = parser.parse(result).getAsJsonObject();
                tempAvatar = getRemoteImage(returnData.get("avatarURL").getAsString());
                tempHero1pic = getRemoteImage(returnData.get("hero1URL").getAsString());
                tempHero2pic = getRemoteImage(returnData.get("hero2URL").getAsString());
                tempHero3pic = getRemoteImage(returnData.get("hero3URL").getAsString());
                return result;
            }
            catch(Exception e){
                return null;
            }
        }

        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            try {
                JsonObject returnData = parser.parse(result).getAsJsonObject();
                mh.avatar = tempAvatar;
                mh.hero1pic = tempHero1pic;
                mh.hero2pic = tempHero2pic;
                mh.hero3pic = tempHero3pic;
            }
            catch(Exception e){}
//        mh.avatar=getRemoteImage(returnData.get("avatarURL").getAsString());
//        mh.hero1pic=getRemoteImage(returnData.get("hero1URL").getAsString());
//        mh.hero2pic= getRemoteImage(returnData.get("hero2URL").getAsString());
//        mh.hero3pic=getRemoteImage(returnData.get("hero3URL").getAsString());
        mh.searchDone(result);
        }

        /**
         * The method pass the searchID and get the result json back by calling doGet()
         * @param searchID STEAM 64 ID for search
         * @return the result json string
         */
    private String search(String searchID){
        Result r = new Result();
        int status = doGet(searchID, r);
        String result = r.getValue();
        return r.getValue();
    }
    }

    /**
     * The class get result id and a helper class result to record the result string
     * @param searchID Steam 64 id of user
     * @param r helper class to record result
     * @return return status code
     */
    public int doGet(String searchID,Result r){
        // Make an HTTP GET passing the name on the URL line

        r.setValue("");
        String response = "";
        HttpURLConnection conn;
        int status = 0;

        try {
            // pass the name on the URL line
            // Local address "http://10.0.2.2:8080/Project4Task1/MatchHistoryService/"+searchID
            // Task 1 address "https://quiet-bastion-93110.herokuapp.com/MatchHistoryService/"+searchID
            // Task 2 address "https://blooming-dusk-16128.herokuapp.com/MatchHistoryService/"+searchID
             URL url = new URL("https://blooming-dusk-16128.herokuapp.com/MatchHistoryService/"+searchID);
//            URL url = new URL("http://10.0.2.2:8080/Project4Task2/MatchHistoryService/"+searchID);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // tell the server what format we want back
            conn.setRequestProperty("Accept", "text/plain");

            // wait for response
            status = conn.getResponseCode();

            // If things went poorly, don't try to read any response, just return.
            if (status != 200) {
                // not using msg
                String msg = conn.getResponseMessage();
//                return conn.getResponseCode();
            }
            String output = "";
            // things went well so let's read the response
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((output = br.readLine()) != null) {
                response += output;

            }

            conn.disconnect();

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }   catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response = URLDecoder.decode(response, "UTF-8");
        }
        catch (Exception e){}
        // return value from server
        // set the response object
        r.setValue(response);
        // return HTTP status to caller

        return status;
    }

    /**
     * helper method to get picture
     * @param Surl the picture url returned by the service
     * @return the bitmap
     */
    private Bitmap getRemoteImage(final String Surl) {
        try {
            final URL url= new URL(Surl);
            final URLConnection conn = url.openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            return bm;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
