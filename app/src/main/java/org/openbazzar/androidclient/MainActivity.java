package org.openbazzar.androidclient;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;


public class MainActivity extends ActionBarActivity {

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t = (TextView)findViewById(R.id.status);
        t.setText("Status: N/A");

        ((Button)findViewById(R.id.test)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toast("Connecting...");
                new HttpRequest().execute(((EditText)findViewById(R.id.url)).getText().toString());
            }
        });
    }

    private class HttpRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                String url = urls[0];
                HttpGet httpget = new HttpGet(url);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                HttpClient Client = new DefaultHttpClient();
                String result = Client.execute(httpget, responseHandler);
                int MAX_LENGTH = 400;
                if(result.length() > MAX_LENGTH)
                    result = result.substring(0,MAX_LENGTH) + "...";

                return "Data from " + url + ": " + result;
            } catch (Exception ex) {
                ex.printStackTrace();
                return "Error: " + ex.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            t.setText(result);
        }
    }

    private void toast(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
