package com.example.ayush.amad_inclass01;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

public class Profile_Screen extends AppCompatActivity {

    EditText name_ed, pass_ed, address_ed, weight_ed, age_ed;
    Button submit;
    TextView tv;

    User user = new User();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_action_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logoutButton)
        {
            Intent intent = new Intent( this,MainActivity.class );
            startActivity(intent);
            this.finish();
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__screen);

        user = (User) getIntent().getSerializableExtra("object");
        tv = (TextView) findViewById(R.id.textView);
        name_ed = (EditText) findViewById(R.id.name_ed);
        age_ed = (EditText) findViewById(R.id.age_ed);
        pass_ed = (EditText) findViewById(R.id.password_ed);
        weight_ed = (EditText) findViewById(R.id.weight_ed);
        address_ed = (EditText) findViewById(R.id.address_ed);
        submit = (Button) findViewById(R.id.submit_button);

        System.out.println(user.toString());
        name_ed.setText(user.getName());
        age_ed.setText(String.valueOf(user.getAge()));
        pass_ed.setText(user.getPassword());
        weight_ed.setText(String.valueOf(user.getWeight()));
        address_ed.setText(String.valueOf(user.getAddress()));
        tv.append(user.getName());


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                        pass_ed.getText().toString().matches("") ||
                                age_ed.getText().toString().matches("") ||
                                address_ed.getText().toString().matches("") ||
                                weight_ed.getText().toString().matches("") ||
                                name_ed.getText().toString().matches("")
                        ) {
                    Toast.makeText(getApplicationContext(), "No field should be left blank", Toast.LENGTH_SHORT).show();
                } else {
                    user.setWeight(Integer.parseInt(weight_ed.getText().toString()));
                    user.setPassword(pass_ed.getText().toString());
                    user.setAge(Integer.parseInt(age_ed.getText().toString()));
                    user.setName(name_ed.getText().toString());
                    user.setAddress(address_ed.getText().toString());


                    String reponse = null;
                    try {
                        reponse = new EditAynctask().execute().get();
                        JSONObject response_data = new JSONObject(reponse);

                        if (Integer.parseInt(response_data.getString("Status")) == 200) {
                            Toast.makeText(getApplicationContext(), "Information Updated", Toast.LENGTH_SHORT).show();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


    }

    public class EditMain {
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
                = MediaType.parse("application/json; charset=utf-8");


        String doPostRequest(String url) throws IOException, JSONException {

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

    }

    private class EditAynctask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            EditMain example = new EditMain();

            String postResponse = null;
            try {
                postResponse = example.doPostRequest("http://inclass01-env.8f2emn6mpx.us-east-1.elasticbeanstalk.com/webapi/UserService/edit");
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
