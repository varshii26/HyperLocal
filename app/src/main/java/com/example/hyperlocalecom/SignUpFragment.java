package com.example.hyperlocalecom;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    EditText mfullName, mphoneNo, memailId, mpassword, maddress;
    Button mregisterBtn;
    ImageButton closebtn;
    ProgressBar pbar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFireStore;
    String userID;


    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;
    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);

        alreadyHaveAnAccount = view.findViewById(R.id.goToLogin);
        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);


        mfullName = view.findViewById(R.id.sign_up_name);
        mphoneNo = view.findViewById(R.id.sign_up_phone);
        memailId = view.findViewById(R.id.sign_up_emailId);
        mpassword = view.findViewById(R.id.sign_up_password);
        maddress = view.findViewById(R.id.sign_up_address);

        mregisterBtn = view.findViewById(R.id.registerBtn);
        closebtn = view.findViewById(R.id.sign_up_closeButton);

        pbar = view.findViewById(R.id.sign_up_ProgressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFireStore = FirebaseFirestore.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());

            }


        });

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainIntent();

            }
        });


        memailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();

            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        mfullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();

            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        mphoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();

            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        maddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();

            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        mpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkInputs();

            }


            @Override
            public void afterTextChanged(Editable s) {


            }
        });



//register--todo for commiting

        mregisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkEmail();
                //register--todo for commiting
            }
        });
    }

        private void setFragment(Fragment fragment) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
            fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
            fragmentTransaction.commit();
        }

        private void checkInputs(){
        if(!TextUtils.isEmpty(mfullName.getText())){
            if(!TextUtils.isEmpty(memailId.getText())){
                if(!TextUtils.isEmpty(mphoneNo.getText())){
                    if(!TextUtils.isEmpty(maddress.getText())){
                        if(!TextUtils.isEmpty(mpassword.getText()) && mpassword.length() >=8){
                            mregisterBtn.setEnabled(true);

                        }else{
                            mregisterBtn.setEnabled(false);
                        }
                    }

                }else{
                    mregisterBtn.setEnabled(false);
                }
            }else{
                mregisterBtn.setEnabled(false);

            }
        }else{
            mregisterBtn.setEnabled(false);

        }
        }

        private void checkEmail(){

        if(memailId.getText().toString().matches(emailPattern)){

            pbar.setVisibility(View.VISIBLE);
            mregisterBtn.setEnabled(false);
            firebaseAuth.createUserWithEmailAndPassword(memailId.getText().toString(),mpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser fuser = firebaseAuth.getCurrentUser();

                      /* Intent mainIntent = new Intent(getActivity(),RegisterActivity.class);
                        ((Activity) getActivity()).overridePendingTransition(0, 0);
                       startActivity(mainIntent);
                        // getActivity().finish(); //

                       */
                       // Toast.makeText(SignUpFragment.this, "User created", Toast.LENGTH_SHORT).show();
                        userID = firebaseAuth.getCurrentUser().getUid();
                        mregisterBtn.setEnabled(false);
                        DocumentReference documentReference = firebaseFireStore.collection("users").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("fName",mfullName.getText().toString());
                        user.put("email",memailId.getText().toString());
                        user.put("phone",mphoneNo.getText().toString());
                        user.put("address",maddress.getText().toString());
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                pbar.setVisibility(View.INVISIBLE);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.toString());
                                mainIntent();
                            }
                        });



                    }else{
                        mregisterBtn.setEnabled(true);
                        pbar.setVisibility(View.INVISIBLE);
                        String error = task.getException().getMessage();
                        Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();

                    }

                }
            });


        }else{

            memailId.setError("Invalid Email!");

        }

        }

        private void mainIntent(){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();

        }
}