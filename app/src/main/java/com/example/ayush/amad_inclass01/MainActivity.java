package com.example.ayush.amad_inclass01;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView signuptv;
    EditText usernameed, passworded;
    Button login_btn;
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

                String username = usernameed.getText().toString();
                String pass = passworded.getText().toString();
                User user = new User();
                user.setUsername(username);
                user.setPassword(pass);
                new Login_asynctask().execute(user);

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

    public class Login_asynctask extends AsyncTask<User, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(User... arg0) {
            HttpURLConnection connection = null;


            try {
                URL url = null;

                url = new URL("http://inclass01-env.8f2emn6mpx.us-east-1.elasticbeanstalk.com/webapi/UserService/login");

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                String str = "{'username':'" + arg0[0].getUsername() + "', " +
                        "'password' = '" + arg0[0].getPassword() + "'}";
                byte[] outputInBytes = str.getBytes("UTF-8");
                Log.d("string", str);
                OutputStream os = connection.getOutputStream();
                os.write(outputInBytes);
                os.close();


                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String json = IOUtils.toString(connection.getInputStream());

                    JSONObject root = new JSONObject(json);
                    String status = root.get("Status").toString();

                    return status;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Making a request to url and getting response

            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        }
    }
}
