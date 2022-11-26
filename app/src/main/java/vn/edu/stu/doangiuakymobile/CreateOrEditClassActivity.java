package vn.edu.stu.doangiuakymobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

import vn.edu.stu.doangiuakymobile.model.Lop;
import vn.edu.stu.doangiuakymobile.model.SinhVien;

public class CreateOrEditClassActivity extends AppCompatActivity {
    MaterialButton btnBack, btnCommitClass, btnBackBelow;
    EditText etClassID2, etClassName2;

    SinhVien sinhVien;
    Lop lop;

    int REQUEST_CODE_CLASS = 116, RESULT_CLASS=117;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_class);
        addControls();
        getLopData();
        addEvents();
    }

    private void getLopData() {
        Intent intent = getIntent();
        if(intent.hasExtra("LOP")){
            lop = (Lop) intent.getSerializableExtra("LOP");
            if(lop!=null){
                etClassID2.setText(lop.getMalop());
                etClassName2.setText(lop.getTenlop());
            }
        }
    }

    private void addControls() {
        btnBack = findViewById(R.id.btnBack);
        btnCommitClass = findViewById(R.id.btnCommitClass);
        btnBackBelow = findViewById(R.id.btnBackBelow);
        etClassID2 = findViewById(R.id.etClassID2);
        etClassName2 = findViewById(R.id.etClassName2);
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnBackBelow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnCommitClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyLuu();
            }
        });
    }

    private void xuLyLuu() {
        String malop, tenlop;
        malop = etClassID2.getText().toString();
        tenlop = etClassName2.getText().toString();
        if(lop==null){
            lop = new Lop(malop, tenlop);
        }
        lop.setTenlop(tenlop);
        lop.setMalop(malop);

        Intent intenttra = getIntent();
        intenttra.putExtra("TRALOP", lop);
        setResult(1, intenttra);
        finish();
    }
}