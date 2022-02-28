package com.example.hyperlocalecom;

import static com.example.hyperlocalecom.RegisterActivity.onResetPasswordFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   // private static final String ARG_PARAM1 = "param1";
   // private static final String ARG_PARAM2 = "param2";
    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText email;
    private EditText password;
    private ImageButton closeBtn;
    private Button loginBtn;
    private FirebaseAuth firebaseAuth ;
    private TextView forgotPassword;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private ProgressBar pbar;
    public static boolean disableCloseBtn = false;

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = view.findViewById(R.id.goToRegister);
        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);
        email = view.findViewById(R.id.sign_in_emailId);
        password = view.findViewById(R.id.sign_in_password);
        closeBtn = view.findViewById(R.id.sign_in_closeButton);
        loginBtn = view.findViewById(R.id.loginBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        pbar = view.findViewById(R.id.sign_in_ProgressBar);
        forgotPassword = view.findViewById(R.id.sign_in_forgot_password);
        loginBtn.setEnabled(true);
        if(disableCloseBtn==true){
            closeBtn.setVisibility(View.GONE);

        }else{
            closeBtn.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResetPasswordFragment = true;
                setFragment(new ResetPasswordFragment());
            }
        });

     email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
         password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });







        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmailAndPassword();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();

            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                loginBtn.setEnabled(true);
            }else{
                loginBtn.setEnabled(false);
            }

        }else{
                loginBtn.setEnabled(false);
        }
    }




    private void checkEmailAndPassword(){
        if(email.getText().toString().matches(emailPattern)){
            if(password.length()>=8) {

                pbar.setVisibility(View.VISIBLE);
                loginBtn.setEnabled(false);

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mainIntent();

                        }else{
                            pbar.setVisibility(View.INVISIBLE);
                            loginBtn.setEnabled(true);
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
            else{
                Toast.makeText(getActivity(),"Incorrect email or password",Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(getActivity(),"Incorrect email or password",Toast.LENGTH_SHORT).show();
        }

    }

    private void mainIntent(){
        if(disableCloseBtn){
            disableCloseBtn = false;
        }else{
            Intent intent = new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(intent);
        }


        getActivity().finish();

    }

}