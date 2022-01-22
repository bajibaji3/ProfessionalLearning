package com.example.professionallearning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.professionallearning.R;

public class SetPasswordActivity extends AppCompatActivity {
    private String mPhone;
    private EditText editPas;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        editPas = findViewById(R.id.editText4);
        buttonNext = findViewById(R.id.button17);
        buttonNext.setEnabled(false);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            String phone1 = bundle.getString("phone");
            String phone2 = bundle.getString("phoneBack");
            if (phone1 == null) {
                mPhone = phone2;
            } else {
                mPhone = phone1;
            }
        }

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pas = editPas.getText().toString().trim();
                buttonNext.setEnabled(!pas.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        editPas.addTextChangedListener(textWatcher);

        buttonNext.setOnClickListener(view -> {
            if (isLetterDigit(editPas.getText().toString())) {
                if (editPas.getText().toString().length() < 6 || editPas.getText().toString().length() > 20) {
                    Toast.makeText(SetPasswordActivity.this, "密码必须为6到20位", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SetPasswordActivity.this,FillBaseMesActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("mPhone",mPhone);
                    bundle1.putString("password",editPas.getText().toString());
                    intent.putExtras(bundle1);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(SetPasswordActivity.this, "密码必须为字母或数字", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //判断密码中是不是只含有数字和字母
    private boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SetPasswordActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
