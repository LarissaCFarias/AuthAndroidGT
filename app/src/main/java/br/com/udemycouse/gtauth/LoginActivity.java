package br.com.udemycouse.gtauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Button login;
    private String email;
    private String password;
    private EditText emailT;
    private EditText senhaT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        emailT = (EditText) findViewById(R.id.etEmail_Lg);
        senhaT = (EditText) findViewById(R.id.etSenha_sn);
        login = (Button) findViewById(R.id.btnLogin);

        currentUser = mAuth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowtoLogin();
            }
        });

    }

    private void AllowtoLogin() {
        email = emailT.getText().toString();
        password = senhaT.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter email...", Toast.LENGTH_SHORT);
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter password...", Toast.LENGTH_SHORT);
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "YEY", Toast.LENGTH_SHORT).show();
                        telaNova();
                    } else {
                        String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();

                    }
                }

            });


        }
    }

    public void telaNova(){

        Intent intent = new Intent(LoginActivity.this, Home.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(currentUser != null){
            telaNova();
        }
    }
}