package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.models.Account;
import com.example.myapplication.R;
import com.example.myapplication.activities.LoginActivity;
import com.example.myapplication.api.ApiClient;
import com.example.myapplication.api.ApiInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSignUp extends Fragment {
    EditText fullName, userName;
    EditText phoneNumber;
    EditText password;
    TextView invalidMsgSignUp;
    Spinner acc_type;

    private Account mAccount;
    CheckBox togglePassword;
    EditText retypePassword;
    Button signUp;

    // kiểm tra userName
    private static final String USERNAME_PATTERN = "^[a-z0-9@.]{6,20}$";

    public static boolean verifyUsername(String username) {
        if (username == null) return false;
        return username.matches(USERNAME_PATTERN);
    }
    public static boolean verifyFullname(String fullName){
        if(fullName == null ||fullName.isEmpty() || fullName.length() == 0){
            return false;
        }
        else return true;
    }
    public static boolean verifyPhoneNumber(String phoneNumber){
        if(phoneNumber == null ||phoneNumber.isEmpty() || phoneNumber.length() < 10 ||phoneNumber.length() >11){
            return false;
        }
        else return true;
    }

    // kiem tra pass
    private static final String PASSWORD_PATTERN ="^[a-z0-9@.]{6,30}$";

    public static boolean verifyPassword(String password) {
        if (password == null) return false;
        return password.matches(PASSWORD_PATTERN);
    }

    public static boolean verifyRepassword(String repassword, String password) {
        if(!repassword.equals(password)) return false;
        else return true;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        acc_type = (Spinner) view.findViewById(R.id.account_type);

        ///Các type account tại file strings.xml
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner, getResources().getStringArray(R.array.account_type));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        fullName = (EditText)view.findViewById(R.id.fullNameEditTextSignUp);
        userName  =(EditText) view.findViewById(R.id.usernameEditTextSignUp);
        password =  (EditText) view.findViewById(R.id.passwordEditTextSignUp);
        phoneNumber =  (EditText) view.findViewById(R.id.phoneNumber);
        retypePassword = (EditText) view.findViewById(R.id.retypePass);
        togglePassword = (CheckBox) view.findViewById(R.id.show_hide_password_signup);
        signUp = (Button)  view.findViewById(R.id.signUpButtoncommit);
        invalidMsgSignUp = (TextView)view.findViewById(R.id.invalidMessageSignUp);
        acc_type.setAdapter(adapter);

        // ẩn hiện password
        togglePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    retypePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    if(password.hasFocus()){
                        password.setSelection(password.getText().length());
                    }
                    if(retypePassword.hasFocus()){
                        retypePassword.setSelection(retypePassword.getText().length());
                    }
                }
                else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    retypePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    if(password.hasFocus()){
                        password.setSelection(password.getText().length());
                    }
                    if(retypePassword.hasFocus()){
                        retypePassword.setSelection(retypePassword.getText().length());
                    }
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stringfullName = fullName.getText().toString().trim();
                String stringPhoneNumber = phoneNumber.getText().toString().trim();
                String stringUsername = userName.getText().toString().trim();
                String stringPassword = password.getText().toString().trim();
                String stringRepassword = retypePassword.getText().toString().trim();

                int roleId = acc_type.getSelectedItemPosition() + 1;
                if(!verifyFullname(stringfullName)){
                    invalidMsgSignUp.setText("Hãy điền họ và tên!");
                    invalidMsgSignUp.setVisibility(View.VISIBLE);
                } else if(!verifyPhoneNumber(stringPhoneNumber)){
                    invalidMsgSignUp.setText("Hãy điền số điện thoại hợp lệ!");
                    invalidMsgSignUp.setVisibility(View.VISIBLE);
                } else if(!verifyUsername(stringUsername)){
                    invalidMsgSignUp.setText("Tên đăng nhập phải chứa ít nhất 6 ký tự!");
                    invalidMsgSignUp.setVisibility(View.VISIBLE);
                } else if(!verifyPassword(stringPassword)){
                    invalidMsgSignUp.setText("Mật khẩu phải chứa ít nhất 6 ký tự!");
                    invalidMsgSignUp.setVisibility(View.VISIBLE);
                } else if(!verifyRepassword(stringRepassword, stringPassword)){
                    invalidMsgSignUp.setText("Nhập lại mật khẩu không đúng");
                    invalidMsgSignUp.setVisibility(View.VISIBLE);
                } else {
                    mAccount = new Account(0,stringUsername, stringPassword, stringfullName, stringPhoneNumber, roleId);
                    Log.e("new acc", mAccount.toString());
                    ApiClient.getApiClient().create(ApiInterface.class).createAccount(mAccount).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.code() == 200){
                                Toast.makeText(getContext(), "ok", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);

                            } else if(response.code() == 500){
                                Toast.makeText(getContext(), "Đã có tk", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getContext(), "Đã xảy ra lỗi", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), "Đã xảy ra lỗi", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        return view;
    }
}