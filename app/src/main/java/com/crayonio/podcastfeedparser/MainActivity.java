package com.crayonio.podcastfeedparser;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.crayonio.podcastfeedparser.rss.Channel;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String TAG = "Main Activity";
    private final PodcastFeedParser parser = new PodcastFeedParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        try {
            File httpCacheDir = new File(getApplicationContext().getCacheDir(), "http");
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        }
        catch (IOException e) {
            Log.i(TAG, "HTTP response cache installation failed:" + e);
        }


    }

    private void fetchAndparseURL(URL url) {
        new AsyncTask<URL, Void, ArrayList<Channel>>(){
            @Override
            protected ArrayList<Channel> doInBackground(URL... urls) {
                ArrayList<Channel> podcastChannels = null;
                try {
                    podcastChannels = parser.parseFeed(urls[0].openStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return podcastChannels;
            }

            @Override
            protected void onPostExecute(ArrayList<Channel> channels) {
                TextView result = (TextView) findViewById(R.id.testResult);
                if(channels.size() > 0){
                    result.setText(getString(R.string.passed));
                    result.setTextColor(Color.GREEN);
                }else{
                    result.setText(getString(R.string.failed));
                    result.setTextColor(Color.RED);
                }
            }


        }.execute(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buttonClicked(View view) {
        EditText text = (EditText) findViewById(R.id.rssfield);

        try {
            URL url = new URL(text.getText().toString());
            fetchAndparseURL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
