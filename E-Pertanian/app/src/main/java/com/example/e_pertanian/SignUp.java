package com.example.e_pertanian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    public TextInputEditText inputEmail, inputPassword, inputPassword2;
    Button btnSignup, btnSignin;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        inputEmail = findViewById(R.id.textInputEmail);
        inputPassword = findViewById(R.id.textInputPassword);
        inputPassword2 = findViewById(R.id.textInputPassword2);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignin = findViewById(R.id.btnLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePetani();
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });

    }

    private boolean validateInput() {
        String emailInput = inputEmail.getText().toString().trim();
        String passwordInput = inputPassword.getText().toString().trim();
        String passwordInput2 = inputPassword2.getText().toString().trim();

        if(emailInput.isEmpty()){
            inputEmail.setError("Email tidak boleh kosong");
            return false;
        } else if (passwordInput.isEmpty()) {
            inputPassword.setError("Password tidak boleh kosong");
            return false;
        } else if (passwordInput2.isEmpty()) {
            inputPassword2.setError("Password tidak boleh kosong");
            return false;
        } else if(!passwordInput.equals(passwordInput2)) {
            inputPassword.setError("Kedua password harus sama!");
            inputPassword2.setError("Kedua password harus sama!");
            return false;
        } else {
            inputEmail.setError(null);
            inputPassword.setError(null);
            inputPassword2.setError(null);
            return true;
        }
    }


    public void savePetani(){
        String emailInput = inputEmail.getText().toString().trim();
        String passwordInput = inputPassword.getText().toString().trim();


        if(!validateInput()) {
            Toast.makeText(SignUp.this, "Gagal...", Toast.LENGTH_SHORT).show();
            return;
        }

        mFirebaseAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(SignUp.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                    //FirebaseUser user = mFirebaseAuth.getCurrentUser();
                }
                else {
                    finish();
                    startActivity(new Intent(SignUp.this,MainActivity.class));
                }
            }
        });
    }
}