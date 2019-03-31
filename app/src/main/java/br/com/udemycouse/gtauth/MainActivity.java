package br.com.udemycouse.gtauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private Button bCad;
    private  Button bLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        bCad = (Button) findViewById(R.id.btnCad);
        bLog = (Button) findViewById(R.id.btnLogin);

        bCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                telaCad();
            }
        });

        bLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                telaLog();
            }
        });

    }



    public void telaCad(){

        Intent intent = new Intent(MainActivity.this, CadPerfilActivity.class);
        startActivity(intent);
    }

    public void telaLog(){

        Intent intent = new Intent(MainActivity.this, InicioActivity.class);
        startActivity(intent);
    }
}
