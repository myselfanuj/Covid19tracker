package com.anujsingh.covid19statewise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
   private TextView scrollView,confirm,activ,recvd,dcsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = findViewById(R.id.textView);
       confirm = findViewById(R.id.conf);
        activ =findViewById(R.id.active);
        recvd =findViewById(R.id.recovered);
        dcsd=findViewById(R.id.deceased);
        OkHttpClient client = new OkHttpClient();
        String url ="https://api.covid19india.org/data.json";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response responses = null;


        client.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                scrollView.setText(e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String  myResponse =  response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                       public void run() {
                            try {
                                JSONObject Jobject = new JSONObject(myResponse);
                                JSONArray Jarray = Jobject.getJSONArray("statewise");
                                String state,ConfirmCase,Activecase,recovered,deaths;
                                int limit = Jarray.length();
                                JSONObject string     = Jarray.getJSONObject(0);
                                String conf = string.getString("confirmed");
                                String active = string.getString("active");
                                String recover = string.getString("recovered");
                                String death = string.getString("deaths");
                                confirm.setText(conf);
                                recvd.setText(recover);
                                activ.setText(active);
                                dcsd.setText(death);
                                for(int i = 0; i < limit; i++ )
                                {
                                    JSONObject object     = Jarray.getJSONObject(i);

                                    state = object.getString("state");
                                    ConfirmCase = object.getString("confirmed");
                                    Activecase = object.getString("active");
                                    recovered = object.getString("recovered");
                                    deaths = object.getString("deaths");
                                    Log.d("JSON DATA", state + " ## " + ConfirmCase);
                                    scrollView.append(state+"\n" +ConfirmCase +"    " +Activecase + "    "+ recovered+"    "+ deaths+"\n\n");

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        });
                }






            }

        });


} }

//  }}}