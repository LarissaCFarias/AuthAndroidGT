package br.com.udemycouse.gtauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class InicioActivity extends AppCompatActivity {

    private Button outLog, gotoPerfil;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mAuth = FirebaseAuth.getInstance();
        outLog = (Button) findViewById(R.id.btnOut);
        gotoPerfil = (Button) findViewById(R.id.btnPerfil);

        outLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                telaEntrar();
            }
        });

        gotoPerfil.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View v){
                telaPerfil();
            }
        });
    }

    public void telaEntrar(){

        Intent intent = new Intent(InicioActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void telaPerfil(){
        Intent intent = new Intent (InicioActivity.this, UserPerfil.class);
        startActivity(intent);
    }
}
