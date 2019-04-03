package br.com.udemycouse.gtauth;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
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
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import br.com.udemycouse.gtauth.Models.User;

import static br.com.udemycouse.gtauth.CadPerfilActivity.REQUESTCODE;

public class UserPerfil extends AppCompatActivity {

    private ImageView close, image_profile;
    TextView save, tv_change;
    MaterialEditText fullname, username, dtNasc;
    FirebaseUser firebaseUser;
    private Uri mImageUri;
    private StorageTask uploadTask;
    StorageReference storageRef;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_perfil);

        inflateView();

        //img insert





        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mAuth = FirebaseAuth.getInstance();
                currentUser = mAuth.getCurrentUser();

                User user = dataSnapshot.getValue(User.class);
                fullname.setText(user.getNome());
                username.setText(user.getUsername());
                dtNasc.setText(user.getDtNasc());


                Glide.with(getApplicationContext()).load(currentUser.getPhotoUrl()).into(image_profile);

               // Glide.with(getApplicationContext()).load(user.getImageUrl()).into(image_profile);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(fullname.getText().toString(), username.getText().toString(), dtNasc.getText().toString());
            }
        });


    }

    private void updateProfile(String fullname, String username, String dtNasc) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nome", fullname);
        hashMap.put("dtNasc", dtNasc);
        hashMap.put("apelido", username);

        reference.updateChildren(hashMap);
    }

    private String getFileExtensio(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){

        if (mImageUri != null) {

            final StorageReference filereference = storageRef.child(System.currentTimeMillis() + "." + getFileExtensio(mImageUri));

            uploadTask = filereference.putFile(mImageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String myUrl = downloadUri.toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("imageUrl", "" + myUrl);

                        reference.updateChildren(hashMap);


                    } else {
                        Toast.makeText(UserPerfil.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserPerfil.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(UserPerfil.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUESTCODE && resultCode == RESULT_OK){

            //pega a porra da imagem

         //   mImageUri = result.getUri();
            uploadImage();

        }else{
            //bad things happen
        }
    }

    private void inflateView(){

            close = findViewById(R.id.close);
            image_profile = findViewById(R.id.imgVProfile);
            save = findViewById(R.id.save);
            tv_change = findViewById(R.id.tv_change);
            fullname = findViewById(R.id.fullname);
            username = findViewById(R.id.username);
            dtNasc = findViewById(R.id.dtNasc);

            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            storageRef = FirebaseStorage.getInstance().getReference("uploads");


        }





}
