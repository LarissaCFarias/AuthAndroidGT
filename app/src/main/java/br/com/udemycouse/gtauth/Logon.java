package br.com.udemycouse.gtauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Logon extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private EditText emailT;
    private EditText senhaT;
    private Button entrar;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);


        mAuth = FirebaseAuth.getInstance();
        emailT = (EditText) findViewById(R.id.etEmail);
        senhaT = (EditText) findViewById(R.id.etSenha);
        entrar = (Button) findViewById(R.id.button);

        progressBar = new ProgressDialog(this);


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewAccount();
            }
        });


    }

    private void CreateNewAccount() {

        email = emailT.getText().toString();
        password = senhaT.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter email...", Toast.LENGTH_SHORT);
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter password...", Toast.LENGTH_SHORT);
        } else {
            progressBar.setTitle("Creating new user");
            progressBar.setMessage("Please, be patient");
            progressBar.setCanceledOnTouchOutside(true);
            progressBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Logon.this, "YEY", Toast.LENGTH_SHORT).show();
                                progressBar.dismiss();
                                telaNova();
                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(Logon.this, "Error" + message, Toast.LENGTH_SHORT).show();
                                progressBar.dismiss();
                            }


                        }
                    });
        }
    }

    public void telaNova(){

        Intent intent = new Intent(Logon.this, CadPerfilActivity.class);
        //intent.putExtra("message", emailT.toString());
        startActivity(intent);
    }
}
