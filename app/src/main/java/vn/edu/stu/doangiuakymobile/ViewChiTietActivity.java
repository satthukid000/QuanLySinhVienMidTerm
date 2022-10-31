package vn.edu.stu.doangiuakymobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import vn.edu.stu.doangiuakymobile.model.Lop;
import vn.edu.stu.doangiuakymobile.model.SinhVien;

public class ViewChiTietActivity extends AppCompatActivity {
    ArrayList<SinhVien> dsSV;

    int vitriSV;

    public String ma="", ten="", email="", ngaysinh="", malop="", tenlop="";
    public Boolean phai=true;

    TextView tvNameDetail, tvIDDetail, tvClassDetail, tvEmailDetai, tvDOBDetail, tvGenderDetail;
    MaterialButton btnEditDetail, btnBack, btnRemoveDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chi_tiet);

        addControls();
        getSinhVienBenKiaGuiTraVe();
        addEvents();


    }

    private void getSinhVienBenKiaGuiTraVe() {
        Intent intent = getIntent();
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
                onBackPressed(); //hàm đã qua chỉnh sửa, xem ở dưới
            }
        });
        btnEditDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //main.chinhSuaChiTietThongTin();
                chinhSuaChiTietThongTin();
            }
        });
        btnRemoveDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xoaChiTietThongTin();
            }
        });
    }

    private void xoaChiTietThongTin() {
        tvNameDetail.setText("");
        tvIDDetail.setText("");
        tvClassDetail.setText("");
        tvEmailDetai.setText("");
        tvDOBDetail.setText("");
        tvGenderDetail.setText("");
        Intent intentxoa = new Intent();
        intentxoa.putExtra("coxoa", true);
        intentxoa.putExtra("vitrixoa", vitriSV);

        setResult(RESULT_OK, intentxoa);
        finish();
    }


    //Hàm chỉnh sửa này sẽ chuyển qua CreateOrEdit để tiến hành sửa thông tin
    //sau khi sửa xong, bên CreateOrEdit sẽ truyền hết dữ liệu về đây. Xem hàm OnActivityForResult
    private void chinhSuaChiTietThongTin() {
        Intent intent = new Intent(ViewChiTietActivity.this, CreateOrEditActivity.class);
        intent.putExtra("vitrisv", vitriSV); //truyền vị trí sinh viên qua bên View Thêm và chỉnh sửa
        intent.putExtra("danhsach", dsSV); //đưa dsSV dưới dạng serializable với tên danhsach
        startActivityForResult(intent,2);
    }

    private void addControls() {
        tvNameDetail = findViewById(R.id.tvNameDetail);
        tvIDDetail = findViewById(R.id.tvIDDetail);
        tvClassDetail = findViewById(R.id.tvClassDetail);
        tvEmailDetai = findViewById(R.id.tvEmailDetail);
        tvDOBDetail = findViewById(R.id.tvDOBDetail);
        tvGenderDetail = findViewById(R.id.tvGenderDetail);
        btnBack = findViewById(R.id.btnBack);

        btnEditDetail = findViewById(R.id.btnEditDetail);
        btnRemoveDetail = findViewById(R.id.btnRemoveDetail);
    }

    //Sau khi CreateOrEdit chỉnh xong thì sẽ truyền thông tin của Sinh Viên về lại đây
    //Ta lấy hết thông tin và cập nhật lại các TextView theo thông tin đã nhận
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){
            if(resultCode==RESULT_OK){
                //Nhận tên, mã, email, ngaysinh, phai, malop, tenlop từ bên CreateOrEdit
                ten = data.getStringExtra("ten");
                ma = data.getStringExtra("ma");
                email = data.getStringExtra("email");
                ngaysinh = data.getStringExtra("ngaysinh");
                phai = data.getBooleanExtra("phai", true);
                malop = data.getStringExtra("malop");
                tenlop = data.getStringExtra("tenlop");
                vitriSV = data.getIntExtra("vitrisinhvien",0);
                Lop lop = new Lop(malop, tenlop);
                if (vitriSV >= 0 && vitriSV < dsSV.size()) {
                    //cập nhật dữ liệu lên các textview
                    tvNameDetail.setText(ten);
                    tvIDDetail.setText(ma);
                    tvEmailDetai.setText(email);
                    tvDOBDetail.setText(ngaysinh);
                    if(phai){
                        tvGenderDetail.setText("Nam");
                    }else tvGenderDetail.setText("Nữ");
                    tvClassDetail.setText(lop.toString());
                }
            }
        }
    }

    //vì khi từ ViewChiTiet trở về main, ta cần phải truyền luôn dữ liệu sinh viên đã nhận
    //từ CreateOrEdit. Vì nếu không thì trở về main thì dữ liệu sẽ không thay đổi
    @Override
    public void onBackPressed() {

//        ma = tvIDDetail.getText().toString();
//        ten = tvNameDetail.getText().toString();
//        email = tvEmailDetai.getText().toString();
//        ngaysinh = tvDOBDetail.getText().toString();
//        malop = tvClassDetail.getText().toString();
//        tenlop = tvClassDetail.getText().toString();
//        if (tvGenderDetail.toString().equalsIgnoreCase("nam"))
//            phai = true;
//        else
//            phai = false;

        if(ma.equalsIgnoreCase("") && ten.equalsIgnoreCase("")){
            finish();
        }

        Intent intenttra = new Intent();
        intenttra.putExtra("ten", ten);
        intenttra.putExtra("ma", ma);
        intenttra.putExtra("email", email);
        intenttra.putExtra("ngaysinh", ngaysinh);
        intenttra.putExtra("phai", phai);
        intenttra.putExtra("malop", malop);
        intenttra.putExtra("tenlop", tenlop);

        intenttra.putExtra("vitrisinhvien", vitriSV);
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
        Intent intent = new Intent(ViewChiTietActivity.this, AboutMeActivity.class);
        startActivity(intent);
    }
}