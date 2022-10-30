package vn.edu.stu.doangiuakymobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Calendar;

import vn.edu.stu.doangiuakymobile.model.Lop;
import vn.edu.stu.doangiuakymobile.model.SinhVien;

public class CreateOrEditActivity extends AppCompatActivity {

    EditText etName, etID, etClassID, etClassName, etEmail;
    TextView tvDOBCreate;
    RadioGroup radioGroupGender;
    RadioButton radNam, radNu;

    MaterialButton btnCommit, btnBack;

    public int viTriSV =-1;
    ArrayList<SinhVien> dsSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit);
        dsSV = new ArrayList<>();
        addControls();
        addEvents();
    }

    private void addEvents() {
        Calendar myCalendar= Calendar.getInstance(); //đầu tiên, khởi tạo một calendar để lưu lại ngày tháng năm
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {  //khởi tạo một DatePicker để hiện ra cho chọn ngày tháng năm
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year); //chọn năm
                myCalendar.set(Calendar.MONTH, month);  //điền tháng
                myCalendar.set(Calendar.DAY_OF_MONTH, day);  //chọn ngày
                //rồi điền tất cả vào text view ngày sinh
                tvDOBCreate.setText(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" +myCalendar.get(Calendar.MONTH)+"/"+myCalendar.get(Calendar.YEAR));
            }
        };
        tvDOBCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateOrEditActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getSVInfo(); //hàm lấy thông tin sinh viên bỏ lên

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThemSua();
            }
        });
    }

    private void getSVInfo() { //nếu vị trí sinh viên nằm trong mảng dsSV tức là đã chọn sinh viên
        if(viTriSV>=0 && viTriSV <dsSV.size()){
            etName.setText(dsSV.get(viTriSV).getTen().toString());
            etID.setText(dsSV.get(viTriSV).getMa().toString());
            etEmail.setText(dsSV.get(viTriSV).getMa().toString());
            tvDOBCreate.setText(dsSV.get(viTriSV).getNgaysinh().toString());
            if(dsSV.get(viTriSV).getPhai()){
                radNam.setChecked(true);
            }else radNu.setChecked(true);
            etClassID.setText(dsSV.get(viTriSV).getLop().getMalop().toString());
            etClassName.setText(dsSV.get(viTriSV).getLop().getTenlop().toString());
        }
    }

    private void xuLyThemSua() {
        if(viTriSV>=0 && viTriSV <dsSV.size()){
            getSVInfo();
        } else{
            String ma, ten, email, ngaysinh, malop, tenlop;
            boolean phai;
            ma = etID.getText().toString();
            ten = etName.getText().toString();
            email = etEmail.getText().toString();
            ngaysinh = tvDOBCreate.getText().toString();
            malop=etClassID.getText().toString();
            tenlop = etClassName.getText().toString();
            if(radNam.isChecked())
                phai = true;
            else
                phai = false;
            Lop lop = new Lop(malop, tenlop);
            SinhVien sinhVien = new SinhVien(ma, ten, email, ngaysinh, phai, lop);

            dsSV.add(sinhVien);

//            Intent intent = new Intent();
//            intent.putExtra("danhsach", dsSV); //đưa dsSV dưới dạng serializable với tên danhsach
//            intent.putExtra("vitrisv", viTriSV); //truyền vị trí sinh viên qua bên View Chi Tiết
//            setResult(RESULT_OK, intent);
//            finish();
        }
    }

    private void addControls() {
        etName = findViewById(R.id.etName);
        etID = findViewById(R.id.etID);
        etClassID = findViewById(R.id.etClassID);
        etClassName = findViewById(R.id.etClassName);
        etEmail = findViewById(R.id.etEmail);
        tvDOBCreate = findViewById(R.id.tvDOBCreate);

        radioGroupGender = findViewById(R.id.radioGroupGender);
        radNam = findViewById(R.id.radNam);
        radNu = findViewById(R.id.radNu);

        btnCommit = findViewById(R.id.btnCommit);
        btnBack = findViewById(R.id.btnBack);

        Intent intent = getIntent();
        dsSV = new ArrayList<>();
        dsSV = (ArrayList<SinhVien>) intent.getSerializableExtra("danhsach");     //lấy serializable với tên danhsach từ bên kia gửi qua

        viTriSV = intent.getIntExtra("vitrisv",-1); //để lấy vị trí sinh viên trong array list bên kia gửi qua

    }
}