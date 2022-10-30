package vn.edu.stu.doangiuakymobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import vn.edu.stu.doangiuakymobile.model.SinhVien;

public class ViewChiTietActivity extends AppCompatActivity {
    ArrayList<SinhVien> dsSV;

    int vitriSV;

    TextView tvNameDetail, tvIDDetail, tvClassDetail, tvEmailDetai, tvDOBDetail, tvGenderDetail;
    MaterialButton btnCreateDetail, btnEditDetail, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chi_tiet);

        addControls();
        addEvents();
        getSinhVienBenKiaGuiTraVe();

    }

    private void getSinhVienBenKiaGuiTraVe() {
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("Bundle");  //lấy args tên Bundle bên main activity sang đây
        dsSV = (ArrayList<SinhVien>) intent.getSerializableExtra("danhsach");     //lấy serializable với tên danhsach từ bên kia gửi qua
        vitriSV = intent.getIntExtra("vitrisv",0); //để lấy vị trí sinh viên trong array list bên kia gửi qua

        tvNameDetail.setText(dsSV.get(vitriSV).getTen().toString());
        tvIDDetail.setText(dsSV.get(vitriSV).getMa().toString());
        tvClassDetail.setText(dsSV.get(vitriSV).getLop().toString());
        tvDOBDetail.setText(dsSV.get(vitriSV).getNgaysinh().toString());
        if(dsSV.get(vitriSV).getPhai())
            tvGenderDetail.setText("Nam");
        else
            tvGenderDetail.setText("Nữ");

        tvEmailDetai.setText(dsSV.get(vitriSV).getEmail().toString());
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); //bấm nút trở về thì sẽ tự bấm nút trở về
            }
        });
        btnCreateDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewChiTietActivity.this, CreateOrEditActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        tvNameDetail = findViewById(R.id.tvNameDetail);
        tvIDDetail = findViewById(R.id.tvIDDetail);
        tvClassDetail = findViewById(R.id.tvClassDetail);
        tvEmailDetai = findViewById(R.id.tvEmailDetail);
        tvDOBDetail = findViewById(R.id.tvDOBDetail);
        tvGenderDetail = findViewById(R.id.tvGenderDetail);
        btnBack = findViewById(R.id.btnBack);
        btnCreateDetail= findViewById(R.id.btnCreateDetail);
        btnEditDetail = findViewById(R.id.btnEditDetail);
    }
}