package com.consul.edu.educationconsultant.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.consul.edu.educationconsultant.R;
import com.consul.edu.educationconsultant.asyncTasks.UserSendEmailTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText inputEmail;
    private Button btnResetPassword;
    private FrameLayout frameProgressBar;
    private FirebaseAuth auth;


    /**
     *
     * This method gets called immediately after your activity is launched.
     * This method is where you do all your normal activity setup such as calling setContentView().
     *
     * You should always override this method. If you don’t override it, you won’t be able to tell Android what layout your activity should use.
     * At this point, the activity isn’t yet visible.
     *
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        frameProgressBar = (FrameLayout) findViewById(R.id.frame_progress_bar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        inputEmail = (EditText) findViewById(R.id.email);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
    }

    /**
     * Called when a reset password button has been clicked.
     *
     * @param view The view (in this case a button) that was clicked.
     *
     * */
    public void onClickResetPassword(View view){
        if (btnResetPassword != null) {
            String email = inputEmail.getText().toString().trim();

            // Check if the email field is empty
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplication(), R.string.msg_enter_email, Toast.LENGTH_SHORT).show();
                return;
            }

            new UserSendEmailTask().execute(email);
            Toast.makeText(ResetPasswordActivity.this, R.string.msg_send_instructions, Toast.LENGTH_LONG).show();

            /*frameProgressBar.setVisibility(View.VISIBLE);

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, R.string.msg_send_instructions, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ResetPasswordActivity.this, R.string.msg_failed_send, Toast.LENGTH_LONG).show();
                            }

                            frameProgressBar.setVisibility(View.GONE);
                        }
                    });*/

        }
    }
}
