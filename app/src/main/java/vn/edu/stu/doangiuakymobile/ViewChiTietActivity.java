package vn.edu.stu.doangiuakymobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import vn.edu.stu.doangiuakymobile.model.Lop;
import vn.edu.stu.doangiuakymobile.model.SinhVien;

public class ViewChiTietActivity extends AppCompatActivity {
    ArrayList<SinhVien> dsSV;

    int vitriSV;

    public String ma = "", ten = "", email = "", ngaysinh = "", malop = "", tenlop = "";
    public Boolean phai = true;

    TextView tvNameDetail, tvIDDetail, tvClassDetail, tvEmailDetai, tvDOBDetail, tvGenderDetail;
    MaterialButton btnEditDetail, btnBack, btnRemoveDetail;

    ImageView imageViewAvatar;
    Bitmap bitmap;

    SinhVien chon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chi_tiet);

        addControls();
        getSinhVienBenKiaGuiTraVe();
        addEvents();
    }

    private void getSinhVienBenKiaGuiTraVe() {
        //code mới
        Intent intent = getIntent();
        vitriSV = intent.getIntExtra("vitrisv", -1);
        if (intent.hasExtra("CHON")) {
            chon = (SinhVien) intent.getSerializableExtra("CHON");
            if (chon != null) {
                tvNameDetail.setText(chon.getTen());
                tvIDDetail.setText(chon.getMa());
                tvClassDetail.setText(chon.getLop().toString());
                tvDOBDetail.setText(chon.getNgaysinh());
                tvEmailDetai.setText(chon.getEmail());
                if (chon.getPhai())
                    tvGenderDetail.setText("Nam");
                else
                    tvGenderDetail.setText("Nữ");
                if(chon.getAvatarEncodedStr()!=null){
                    byte[] bytes = Base64.decode(chon.getAvatarEncodedStr(),Base64.DEFAULT);  //decode string image để tạo lại ảnh đại diện người dùng và hiện lên
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    imageViewAvatar.setImageBitmap(bitmap);
                }
            }
        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewChiTietActivity.this);
        builder.setTitle(R.string.confirm_delete);
        builder.setMessage(R.string.confirm_delete_sub);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
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
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    //Hàm chỉnh sửa này sẽ chuyển qua CreateOrEdit để tiến hành sửa thông tin
    //sau khi sửa xong, bên CreateOrEdit sẽ truyền hết dữ liệu về đây. Xem hàm OnActivityForResult
    private void chinhSuaChiTietThongTin() {

        //code mới
        Intent intent = new Intent(getApplicationContext(), CreateOrEditActivity.class);
        intent.putExtra("vitrisv", vitriSV);
        intent.putExtra("CHON", chon); //đưa sinh viên đã chọn sang bên kia
        startActivityForResult(intent, 2);
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

        chon = null;

        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        bitmap = null;
    }

    //Sau khi CreateOrEdit chỉnh xong thì sẽ truyền thông tin của Sinh Viên về lại đây
    //Ta lấy hết thông tin và cập nhật lại các TextView theo thông tin đã nhận
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                vitriSV = data.getIntExtra("vitrisv",-1);
                if (data.hasExtra("TRA")) {
                    SinhVien tra = (SinhVien) data.getSerializableExtra("TRA");
                    chon = tra;
                    tvNameDetail.setText(tra.getTen());
                    tvIDDetail.setText(tra.getMa());
                    tvClassDetail.setText(tra.getLop().toString());
                    tvDOBDetail.setText(tra.getNgaysinh());
                    tvEmailDetai.setText(tra.getEmail());
                    if (tra.getPhai())
                        tvGenderDetail.setText("Nam");
                    else
                        tvGenderDetail.setText("Nữ");
                    if(tra.getAvatarEncodedStr()!=null){
                        byte[] bytes = Base64.decode(tra.getAvatarEncodedStr(),Base64.DEFAULT);  //decode string image để tạo lại ảnh đại diện người dùng và hiện lên
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                        imageViewAvatar.setImageBitmap(bitmap);
                    }
                }
            }
        }
    }

    //vì khi từ ViewChiTiet trở về main, ta cần phải truyền luôn dữ liệu sinh viên đã nhận
    //từ CreateOrEdit. Vì nếu không thì trở về main thì dữ liệu sẽ không thay đổi
    @Override
    public void onBackPressed() {
        Intent intenttra = new Intent();
        intenttra.putExtra("TRA2", chon);
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
        switch (item.getItemId()) {
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