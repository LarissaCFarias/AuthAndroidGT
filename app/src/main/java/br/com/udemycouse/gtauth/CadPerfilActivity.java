package br.com.udemycouse.gtauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadPerfilActivity extends AppCompatActivity {

    private EditText mNome;
    private EditText mApelido, mDtNasc, meMail, mSenha;
    private DatabaseReference loginReference;
    private FirebaseDatabase firebaseDatabase;
    private Button btnInsert, btnButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_perfil);

        InflateActivity();
        configureFirebase();
        //String message = intent.getStringExtra("message");
        //meMail.setText(message);

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nome = mNome.getEditableText().toString();
                String apelido = mApelido.getEditableText().toString();
                String dtNasc = mDtNasc.getEditableText().toString();
                String eMail = meMail.getEditableText().toString();
                String senha = mSenha.getEditableText().toString();
                String id = loginReference.push().getKey();

                Login login = new Login (id, nome, apelido, dtNasc, eMail, senha);
                loginReference.child(id).setValue(login);

                Toast.makeText(CadPerfilActivity.this, getString(R.string.login_criado), Toast.LENGTH_SHORT).show();
                telaNova();

            }
        });

    }


    public void InflateActivity() {
        mNome = (EditText) findViewById(R.id.etName);
        mApelido = (EditText) findViewById(R.id.etApelido);
        mDtNasc = (EditText) findViewById(R.id.etDtNasc);
        meMail = (EditText) findViewById(R.id.etEmail);
        mSenha = (EditText) findViewById(R.id.etSenha);


    }

    private void configureFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        loginReference = firebaseDatabase.getReference("login");


    }

    public void telaNova(){

        Intent intent = new Intent(CadPerfilActivity.this, InicioActivity.class);

        startActivity(intent);
    }



}
