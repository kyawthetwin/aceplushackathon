package hvc.com.aceplushackathonproject;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hvc.com.aceplushackathonproject.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.txt_message)
    TextView tv_message;
    private FirebaseAuth auth;
    private String message;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        getExtra();
        tv_message.setText(message);
        auth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        auth.addAuthStateListener(authListener);
    }

    @OnClick(R.id.btn_logout)
    public void goToLogout(){
        if (auth.getCurrentUser() != null) {
            auth.signOut();
        }
    }

    private void getExtra(){
        if (getIntent() != null) {
            Intent intent = getIntent();
            message = intent.getStringExtra("message");
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.cancelAll();
            }
        }
    }
}
