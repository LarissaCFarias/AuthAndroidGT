package br.com.udemycouse.gtauth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CadPerfilActivity extends AppCompatActivity {

    static int PReqCode = 1;
    static  int REQUESTCODE = 1;
    private EditText mNome;
    private EditText mApelido, mDtNasc, meMail, mSenha;
    private DatabaseReference loginReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    private Button btnInsert, btnButton;
    private Intent intent;
    private Uri pickedImgURI;
    private ImageView imgUserPhoto;
    private ProgressBar loadingProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_perfil);

        InflateActivity();
        configureFirebase();
        loadingProgress.setVisibility(View.INVISIBLE);


        //img insert

        imgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= 22){
                    checkAndRequestForPermission();
                }else{
                    openGallery();
                }
            }
        });


        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                btnInsert.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);


                final String nome = mNome.getEditableText().toString();
                final String apelido = mApelido.getEditableText().toString();
                final String dtNasc = mDtNasc.getEditableText().toString();
                final String eMail = meMail.getEditableText().toString();
                final String senha = mSenha.getEditableText().toString();
                final String id = loginReference.push().getKey();

                if(nome.isEmpty() || eMail.isEmpty() || senha.isEmpty()){
                    Toast.makeText(CadPerfilActivity.this, getString(R.string.campo_vazio), Toast.LENGTH_LONG).show();
                    btnInsert.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);

                }else{
                    CreatUserAccount(eMail, senha, nome);
                }


               // Login login = new Login (id, nome, apelido, dtNasc, eMail, senha);
                //loginReference.child(id).setValue(login);


                //telaNova();

            }
        });

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);


    }



    private void checkAndRequestForPermission() {

        if(ContextCompat.checkSelfPermission(CadPerfilActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
             if(ActivityCompat.shouldShowRequestPermissionRationale(CadPerfilActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                 Toast.makeText(CadPerfilActivity.this, "Por favor, aceite para prosseguir", Toast.LENGTH_SHORT).show();

             }else{
                 ActivityCompat.requestPermissions(CadPerfilActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                                                         PReqCode);
             }

        }else{
            openGallery();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){
            pickedImgURI = data.getData();
            imgUserPhoto.setImageURI(pickedImgURI);
        }
    }

    private void CreatUserAccount(String eMail, String senha, final String nome) {
            mAuth.createUserWithEmailAndPassword(eMail, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                updateUserInfo(nome, pickedImgURI, mAuth.getCurrentUser());
                                Toast.makeText(CadPerfilActivity.this, "usuario criado", Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(CadPerfilActivity.this, getString(R.string.login_error) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                btnInsert.setVisibility(View.VISIBLE);
                                loadingProgress.setVisibility(View.INVISIBLE);

                            }
                        }
                    });
    }

    public void updateUserInfo(final String nome, Uri pickedImgURI, final FirebaseUser currentUser){
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photo");
        final StorageReference imgFilePath = mStorage.child(pickedImgURI.getLastPathSegment());
        imgFilePath.putFile(pickedImgURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //sucsseesss

                imgFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {


                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nome)
                                .setPhotoUri(uri)
                                .build();

                        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete( Task<Void> task) {

                                if(task.isSuccessful()){
                                     //Login login = new Login (id, nome, apelido, dtNasc, eMail, senha);
                                     //loginReference.child(id).setValue(login);
                                    Toast.makeText(CadPerfilActivity.this, getString(R.string.login_criado), Toast.LENGTH_SHORT);
                                    telaNova();
                                }


                            }
                        });

                    }
                });


            }
        });

    }



    public void InflateActivity() {
        mNome = (EditText) findViewById(R.id.etName);
        mApelido = (EditText) findViewById(R.id.etApelido);
        mDtNasc = (EditText) findViewById(R.id.etDtNasc);
        meMail = (EditText) findViewById(R.id.etEmail);
        mSenha = (EditText) findViewById(R.id.etSenha);
        loadingProgress = (ProgressBar) findViewById(R.id.progressBar);
        imgUserPhoto = (ImageView) findViewById(R.id.regUserPhoto);




    }

    private void configureFirebase(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        loginReference = firebaseDatabase.getReference("login");


    }

    public void telaNova(){

        Intent intent = new Intent(CadPerfilActivity.this, Home.class);

        startActivity(intent);

    }



}
