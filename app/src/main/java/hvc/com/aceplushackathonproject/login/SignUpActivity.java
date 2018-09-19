package hvc.com.aceplushackathonproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hvc.com.aceplushackathonproject.R;

public class SignUpActivity extends AppCompatActivity {


    @BindView(R.id.edt_signUpEmail)
    EditText inputEmail;
    @BindView(R.id.edt_signUpPassword)
    EditText inputPassword;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.btn_signUp)
    public void signUp() {

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
       auth.createUserWithEmailAndPassword(email,password)
               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                       if (!task.isSuccessful()) {
                           Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                   Toast.LENGTH_SHORT).show();
                       } else {
                           startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                           finish();
                       }
                   }
               });

    }
}
