package vn.edu.stu.doangiuakymobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    String ma = "", ten = "", email = "", ngaysinh = "", malop = "", tenlop = "";
    boolean phai = true;

    public int viTriSV = -1;
    public SinhVien chon;
    ArrayList<SinhVien> dsSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit);

        addControls();
        getSVInfo();
        addEvents();
    }

    private void addEvents() {
        Calendar myCalendar = Calendar.getInstance(); //đầu tiên, khởi tạo một calendar để lưu lại ngày tháng năm
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {  //khởi tạo một DatePicker để hiện ra cho chọn ngày tháng năm
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year); //chọn năm
                myCalendar.set(Calendar.MONTH, month);  //điền tháng
                myCalendar.set(Calendar.DAY_OF_MONTH, day);  //chọn ngày
                //rồi điền tất cả vào text view ngày sinh
                tvDOBCreate.setText(myCalendar.get(Calendar.DAY_OF_MONTH) + "/" + myCalendar.get(Calendar.MONTH) + "/" + myCalendar.get(Calendar.YEAR));
            }
        };
        tvDOBCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateOrEditActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                //btnCommit.performClick();
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

    public void getSVInfo() { //nếu vị trí sinh viên nằm trong mảng dsSV tức là đã chọn sinh viên

        Intent intent = getIntent();
        viTriSV = intent.getIntExtra("vitrisv",-1);
        if(intent.hasExtra("CHON")){
            chon = (SinhVien) intent.getSerializableExtra("CHON");
            etName.setText(chon.getTen());
            etID.setText(chon.getMa());
            etEmail.setText(chon.getEmail());
            tvDOBCreate.setText(chon.getNgaysinh());
            if(chon.getPhai())
                radNam.setChecked(true);
            else
                radNu.setChecked(true);
            etClassID.setText(chon.getLop().getMalop());
            etClassName.setText(chon.getLop().getTenlop());
        }
    }

    private void xuLyThemSua() {
        ma = etID.getText().toString();
        ten = etName.getText().toString();
        email = etEmail.getText().toString();
        ngaysinh = tvDOBCreate.getText().toString();
        malop = etClassID.getText().toString();
        tenlop = etClassName.getText().toString();
        if (radNam.isChecked())
            phai = true;
        else
            phai = false;

        Intent intent2 = new Intent();
        Lop lop = new Lop(malop, tenlop);
        chon = new SinhVien(ma, ten, email, ngaysinh, phai, lop);
        intent2.putExtra("TRA", chon); //trả lại học sinh thêm vào
//        intent2.putExtra("ten", ten);
//        intent2.putExtra("ma", ma);
//        intent2.putExtra("email", email);
//        intent2.putExtra("ngaysinh", ngaysinh);
//        intent2.putExtra("phai", phai);
//        intent2.putExtra("malop", malop);
//        intent2.putExtra("tenlop", tenlop);
//
//        intent2.putExtra("vitrisinhvien", viTriSV);

        setResult(RESULT_OK, intent2);
        finish();
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

        viTriSV = intent.getIntExtra("vitrisv", -1); //để lấy vị trí sinh viên trong array list bên kia gửi qua

        chon = new SinhVien();
    }

    @Override
    public void onBackPressed() {
        getSVInfo();

        ma = etID.getText().toString();
        ten = etName.getText().toString();
        email = etEmail.getText().toString();
        ngaysinh = tvDOBCreate.getText().toString();
        malop = etClassID.getText().toString();
        tenlop = etClassName.getText().toString();
        if (radNam.isChecked())
            phai = true;
        else
            phai = false;

        Intent intenttra = new Intent();
        intenttra.putExtra("ten", ten);
        intenttra.putExtra("ma", ma);
        intenttra.putExtra("email", email);
        intenttra.putExtra("ngaysinh", ngaysinh);
        intenttra.putExtra("phai", phai);
        intenttra.putExtra("malop", malop);
        intenttra.putExtra("tenlop", tenlop);


        intenttra.putExtra("vitrisinhvien", viTriSV);
        setResult(RESULT_OK, intenttra);
        finish();

        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuAbout:
                openAboutMe();
                break;
            case R.id.mnuLogout:
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAboutMe() {
        Intent intent = new Intent(CreateOrEditActivity.this, AboutMeActivity.class);
        startActivity(intent);
    }
}