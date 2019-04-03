package br.com.udemycouse.gtauth.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import br.com.udemycouse.gtauth.Fragments.ProfileFragment;
import br.com.udemycouse.gtauth.R;

public class UserClass extends RecyclerView.Adapter<UserClass.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    private FirebaseUser firebaseUser;

    public UserClass(Context mContext, List<User> mUsers){
        this.mContext = mContext;
        this.mUsers = mUsers;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, viewGroup, false);
        return new UserClass.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        final User user = mUsers.get(i);

        viewHolder.username.setText(user.getUsername());
        viewHolder.name.setText(user.getNome());

        Glide.with(mContext).load(user.getImageUrl()).into(viewHolder.image_profile);

        //viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
              //  SharedPreferences.Editor editor = mContext.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                //editor.putString("profileid", user.getId());
               // editor.apply();

               // ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();

         //  }
       // });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public TextView name;
        public CircularImageView image_profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            name = itemView.findViewById(R.id.nome);
            image_profile = itemView.findViewById(R.id.image_profile);
        }
    }
}
