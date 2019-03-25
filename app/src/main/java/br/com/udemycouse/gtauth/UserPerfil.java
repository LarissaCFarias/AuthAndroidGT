package br.com.udemycouse.gtauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserPerfil extends AppCompatActivity{

        private ImageView mProfileImage;
        private TextView mProfileName, mProfileAge, mProfileAbout;
        private Button mProfileBtn;
        private DatabaseReference mUserRefe;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_perfil);

            String user_id = getIntent().getStringExtra("user_id");

            mUserRefe = FirebaseDatabase.getInstance().getReference().child("login").child("user_id");
            inflateView();

            mUserRefe.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String display_name =  dataSnapshot.child("nome").getValue().toString();
                    String display_apelido =  dataSnapshot.child("apelido").getValue().toString();
                    String display_dtNasc =  dataSnapshot.child("dtNasc").getValue().toString();

                    mProfileName.setText(display_name);
                    mProfileAbout.setText(display_apelido);
                    mProfileAge.setText(display_dtNasc);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


    }

        private void inflateView(){
            mProfileAbout = (TextView) findViewById(R.id.txtName);
            mProfileAge = (TextView) findViewById(R.id.txtAge);
            mProfileAbout = (TextView) findViewById(R.id.txtAbout);
            mProfileImage = (ImageView) findViewById(R.id.imgVProfile);
            mProfileBtn = (Button) findViewById(R.id.btnSave);
        }
}
