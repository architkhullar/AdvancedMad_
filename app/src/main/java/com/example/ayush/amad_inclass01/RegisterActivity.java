package com.example.ayush.amad_inclass01;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {

    EditText name_ed, age_ed, weight_ed, address_ed, confirm_pass_ed, pass_ed, username_ed;
    Button login_btn;
    static User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name_ed = (EditText) findViewById(R.id.name_ed);
        age_ed = (EditText) findViewById(R.id.age_ed);
        weight_ed = (EditText) findViewById(R.id.weight_ed);
        address_ed = (EditText) findViewById(R.id.address_ed);
        username_ed = (EditText) findViewById(R.id.username_ed);
        pass_ed = (EditText) findViewById(R.id.password_ed);
        confirm_pass_ed = (EditText) findViewById(R.id.confirm_password_ed);
        login_btn = (Button) findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!confirm_pass_ed.getText().toString().matches(pass_ed.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Passwords dont match", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(username_ed.getText().toString()).matches()) {
                    Toast.makeText(getApplicationContext(), "Email should be in proper format", Toast.LENGTH_SHORT).show();
                } else if (username_ed.getText().toString().matches("") ||
                        pass_ed.getText().toString().matches("") ||
                        age_ed.getText().toString().matches("") ||
                        address_ed.getText().toString().matches("") ||
                        weight_ed.getText().toString().matches("") ||
                        name_ed.getText().toString().matches("")
                        ) {
                    Toast.makeText(getApplicationContext(), "No field should be left blank", Toast.LENGTH_SHORT).show();
                } else {
                    try {

                        user.setAddress(address_ed.getText().toString());
                        user.setAge(Integer.parseInt(age_ed.getText().toString()));
                        user.setName(name_ed.getText().toString());
                        user.setUsername(username_ed.getText().toString());
                        user.setPassword(pass_ed.getText().toString());
                        user.setWeight(Integer.parseInt(weight_ed.getText().toString()));
                        String reponse = new Signupmain.SignupASynctask().execute(user).get();

                        JSONObject response_data = new JSONObject(reponse);
                        String name = (String) response_data.get("Status");
                        if (200 == Integer.parseInt(name)) {
                            Intent intent = new Intent(RegisterActivity.this, Profile_Screen.class);
                            intent.putExtra("object", user);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }

            }
        });


    }

    public static class Signupmain {
        OkHttpClient client = new OkHttpClient();

        public final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        // test data

        String doPostRequest(String url) throws IOException, JSONException {

            System.out.println(user.getUsername());
            JSONObject actualdata = new JSONObject();
            actualdata.put("username", user.getUsername());
            actualdata.put("password", user.getPassword());
            actualdata.put("age", String.valueOf(user.getAge()));
            actualdata.put("weight", String.valueOf(user.getWeight()));
            actualdata.put("address", user.getAddress());
            actualdata.put("name", user.getName());
            RequestBody body = RequestBody.create(JSON, actualdata.toString());


            // System.out.println(body);
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            System.out.println(String.valueOf(request));
            Response response = client.newCall(request).execute();
            return response.body().string();
        }


        private static class SignupASynctask extends AsyncTask<User, Void, String> {
            @Override
            protected String doInBackground(User... voids) {
                Signupmain example = new Signupmain();
               /* String getResponse = null;
                try {
                    getResponse = example.doGetRequest("http://www.vogella.com");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(getResponse);
*/

                // issue the post request

                String postResponse = null;
                try {
                    postResponse = example.doPostRequest("http://inclass01-env.8f2emn6mpx.us-east-1.elasticbeanstalk.com/webapi/UserService/signup");
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
}

