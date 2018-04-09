package com.consul.edu.educationconsultant;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        inputEmail = (EditText) findViewById(R.id.email);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        if (btnResetPassword != null) {
            btnResetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = inputEmail.getText().toString().trim();

                    // validate email input
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplication(), R.string.msg_enter_email, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetPasswordActivity.this, R.string.msg_send_instructions, Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(ResetPasswordActivity.this, R.string.msg_failed_send, Toast.LENGTH_LONG).show();
                                    }

                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                }
            });
        }

    }
}
