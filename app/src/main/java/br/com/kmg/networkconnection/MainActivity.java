package br.com.kmg.networkconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.kmg.networkconnection.util.NetwortUtil;

public class MainActivity extends AppCompatActivity {

    EditText edSearch;
    EditText edResult;
    TextView tvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edSearch = findViewById(R.id.edSearch);
        edResult = findViewById(R.id.edResult);
        tvUrl = findViewById(R.id.tvUrl);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuUrl){
            showURL();
        }

        if(item.getItemId() == R.id.menuConsulta){
            showResult();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showURL(){
        String valueQuery = edSearch.getText().toString();
        URL buildUrl = NetwortUtil.getURL(valueQuery);
        tvUrl.setText(buildUrl.toString());


    }

    public void showResult() {
        String valueQuery = edSearch.getText().toString();
        URL buildUrl = NetwortUtil.getURL(valueQuery);
        MyAsyscTask myAsyscTask = new MyAsyscTask();
        myAsyscTask.execute(buildUrl);
//        edResult.setText(result);
    }

    class MyAsyscTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            //mostrar um load
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            //tira loading
            edResult.setText(s);
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];

            String result = null;
            try {
                result = NetwortUtil.getResponseFromHttpUrl(url);
                Log.d("MAIN_ACTIVITY", result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}
