package com.example.ayush.amad_inclass01;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

//import org.apache.commons.io.IOUtils;

public class MainActivity extends AppCompatActivity {

    TextView signuptv;
    EditText usernameed, passworded;
    Button login_btn;
    User user = new User();

    protected static String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameed = (EditText) findViewById(R.id.username_ed);
        passworded = (EditText) findViewById(R.id.password_ed);
        signuptv = (TextView) findViewById(R.id.signuptv);
        login_btn = (Button) findViewById(R.id.login_btn);



        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameed.getText().toString().matches("") || passworded.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "No field should be blank", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(usernameed.getText().toString()).matches()) {
                    Toast.makeText(getApplicationContext(), "Email should be in proper format", Toast.LENGTH_SHORT).show();
                } else {
                    String username = usernameed.getText().toString();
                    String pass = passworded.getText().toString();

                    user.setUsername(username);
                    user.setPassword(pass);

                    try {
                        String reponse = new Okhttpasynctask().execute().get();
                        System.out.println(reponse);
                        JSONObject response_data = new JSONObject(reponse);
                        token = response_data.getString("token");
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(MainActivity.this, Profile_Screen.class);
                        // intent1.putExtra("object", user1);
                        startActivity(intent1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText( MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT ).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        signuptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });


    }


    ///Test Commit
    public class TestMain {
        OkHttpClient client = new OkHttpClient();

        // code request code here
        String doGetRequest(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        // post request code here

        public final MediaType JSON
                = MediaType.parse("application/json");


        String doPostRequest(String url) throws IOException, JSONException {

            JSONObject actualdata = new JSONObject();
            actualdata.put("username", user.getUsername());
            actualdata.put("password", user.getPassword());
            RequestBody body = RequestBody.create(JSON, actualdata.toString());
            System.out.println(actualdata.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            System.out.println(String.valueOf(request));
            Response response = client.newCall(request).execute();
            return response.body().string();
        }

    }

    private class Okhttpasynctask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            TestMain example = new TestMain();

            String postResponse = null;
            try {
                postResponse = example.doPostRequest("http://ec2-18-216-97-75.us-east-2.compute.amazonaws.com:3000/signin");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println(postResponse);
            return postResponse;
        }
    }
}
