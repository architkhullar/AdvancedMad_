package com.example.ayush.amad_inclass01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText name_ed, age_ed, weight_ed, address_ed, confirm_pass_ed, pass_ed, username_ed;
    Button login_btn;

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
                } else {

                }

            }
        });


    }
}
