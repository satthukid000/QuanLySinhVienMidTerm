package vn.edu.stu.doangiuakymobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import vn.edu.stu.doangiuakymobile.adapter.SinhVienAdapter;
import vn.edu.stu.doangiuakymobile.model.Lop;
import vn.edu.stu.doangiuakymobile.model.SinhVien;

public class MainActivity extends AppCompatActivity {
    ListView lvSinhVien;

    ArrayList<SinhVien> dsSinhViens;
    //ArrayAdapter<SinhVien> adapter;

    //adapter đã chỉnh
    SinhVienAdapter adapter;
    Button btnAdd;

    int vitriSinhVien = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
        addSinhVienExample();
    }

    private void addSinhVienExample() {
        Lop lop1 = new Lop("Lớp 1", "Công nghệ");
        Lop lop2 = new Lop("Lớp 2", "Kiến trúc");
        Lop lop3 = new Lop("Lớp 3", "Kỹ Sư");

        SinhVien sv1 = new SinhVien("DH51905495", "Nguyễn Văn Thanh Đức", "ducducthanh2305@gmail.com", "23/05/2001", true, lop1);
        SinhVien sv2 = new SinhVien("DH51905123", "Hồ Thị Ngọc Hương", "b@gmail.com", "26/05/2001", false, lop1);
        SinhVien sv3 = new SinhVien("DH51999999", "Nguyễn Thanh Bằng", "bangbang@gmail.com", "22/05/2001", true, lop2);

        dsSinhViens.add(sv1);
        dsSinhViens.add(sv2);
        dsSinhViens.add(sv3);

    }


    private void addEvents() {
        registerForContextMenu(lvSinhVien); //đăng ký context menu cho list view
        lvSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitriSinhVien = i;
                xemChiTietThongTin();
            }
        });
        lvSinhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitriSinhVien = i;
                showPopup(view);
                return true;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateOrEditActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }


    private void addControls() {
        lvSinhVien = findViewById(R.id.lvSinhVien);
        dsSinhViens = new ArrayList<>();
        adapter = new SinhVienAdapter(MainActivity.this, R.layout.item_sinhvien_container, dsSinhViens);
        lvSinhVien.setAdapter(adapter);

        btnAdd = findViewById(R.id.btnAdd);

    }

    public void showPopup(View v) { //dùng để tạo pop up menu context
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_context, popup.getMenu());
        popup.setOnMenuItemClickListener(this::onContextItemSelected);//sẽ đồng thời có công dụng như hàm bên dưới, vì hàm dưới đã code sẵn chức năng
        popup.show();
    }

    public void xemChiTietThongTin() {
        Intent intent = new Intent(MainActivity.this, ViewChiTietActivity.class);
        intent.putExtra("vitrisv", vitriSinhVien); //truyền vị trí sinh viên qua bên View Chi Tiết
        //Bundle args = new Bundle(); //dùng cái này để chứa nguyên cái dsSinhViens để quăng qua bên view chi tiết
        intent.putExtra("danhsach", dsSinhViens); //đưa dsSinhViens dưới dạng serializable với tên danhsach
        //intent.putExtra("Bundle", args);    //truyền args vào view chi tiết với tên Bundle
        startActivityForResult(intent, 2);
    }

    public void chinhSuaChiTietThongTin() {
        Intent intent = new Intent(getApplicationContext(), CreateOrEditActivity.class);
        intent.putExtra("vitrisv", vitriSinhVien); //truyền vị trí sinh viên qua bên View Thêm và chỉnh sửa
        intent.putExtra("danhsach", dsSinhViens); //đưa dsSinhViens dưới dạng serializable với tên danhsach
        startActivityForResult(intent,1);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lvSinhVien) {
            getMenuInflater().inflate(R.menu.menu_context, menu); //nạp file menu context
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuView:
                xemChiTietThongTin();
                break;
            case R.id.mnuEdit:
                chinhSuaChiTietThongTin();
                break;
            case R.id.mnuRemove:
                xoaThongTin(vitriSinhVien);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void xoaThongTin(int i) {
        if (i >= 0 && i < dsSinhViens.size()) {
            dsSinhViens.remove(i);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Request Code 1 tức là bật từ menu Edit trong main, ta sẽ lấy dữ liệu từ
        //CreateOrEdit về và cập nhật lại dsSinhViens
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //cập nhật lại danh sách sinh viên sau khi thêm hoặc sửa
                String ma = "", ten = "", email = "", ngaysinh = "", malop = "", tenlop = "";
                Boolean phai;
                ten = data.getStringExtra("ten");
                ma = data.getStringExtra("ma");
                email = data.getStringExtra("email");
                ngaysinh = data.getStringExtra("ngaysinh");
                phai = data.getBooleanExtra("phai", true);
                malop = data.getStringExtra("malop");
                tenlop = data.getStringExtra("tenlop");
                vitriSinhVien = data.getIntExtra("vitrisinhvien",-1);  //lấy vị trí sinh viên từ CreateOrEdit về
                Lop lop = new Lop(malop, tenlop);
                SinhVien sv = new SinhVien(ma, ten, email, ngaysinh, phai, lop);
                //Nếu vị trí thoả điều kiện, thì tức là đang sửa thông tin
                if (vitriSinhVien >= 0 && vitriSinhVien < dsSinhViens.size()) {
                    Toast.makeText(this, "Đã thoả điều kiện", Toast.LENGTH_SHORT);
                    dsSinhViens.get(vitriSinhVien).setMa(ma);
                    dsSinhViens.get(vitriSinhVien).setTen(ten);
                    dsSinhViens.get(vitriSinhVien).setEmail(email);
                    dsSinhViens.get(vitriSinhVien).setNgaysinh(ngaysinh);
                    dsSinhViens.get(vitriSinhVien).setPhai(phai);
                    dsSinhViens.get(vitriSinhVien).setLop(lop);
                    adapter.notifyDataSetChanged();
                }
                else{   //nếu vị trí không thoả điều kiện, tức là ta đang thêm mới sinh viên
                    dsSinhViens.add(sv);
                    adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();
            }
        }
        //Request code = 2 tức là ta Edit từ ViewChiTiet
        //ViewChiTiet sẽ truyền code 2, và truyền thông tin sinh viên về lại qua main để cập nhật
        else if (requestCode == 2){
            if(resultCode ==RESULT_OK){
                String ma, ten, email, ngaysinh, malop, tenlop;
                Boolean phai;
                ten = data.getStringExtra("ten");
                ma = data.getStringExtra("ma");
                email = data.getStringExtra("email");
                ngaysinh = data.getStringExtra("ngaysinh");
                phai = data.getBooleanExtra("phai", true);
                malop = data.getStringExtra("malop");
                tenlop = data.getStringExtra("tenlop");
                vitriSinhVien = data.getIntExtra("vitrisinhvien",-1);
                Lop lop = new Lop(malop, tenlop);
                SinhVien sv = new SinhVien(ma, ten, email, ngaysinh, phai, lop);

                int vitrixoa;
                vitrixoa = data.getIntExtra("vitrixoa",-1);
                Boolean coxoa;
                coxoa = data.getBooleanExtra("coxoa", false);

                if (vitriSinhVien >= 0 && vitriSinhVien < dsSinhViens.size()) {
                    Toast.makeText(this, "Đã thoả điều kiện", Toast.LENGTH_SHORT);
                    dsSinhViens.get(vitriSinhVien).setMa(ma);
                    dsSinhViens.get(vitriSinhVien).setTen(ten);
                    dsSinhViens.get(vitriSinhVien).setEmail(email);
                    dsSinhViens.get(vitriSinhVien).setNgaysinh(ngaysinh);
                    dsSinhViens.get(vitriSinhVien).setPhai(phai);
                    dsSinhViens.get(vitriSinhVien).setLop(lop);
                    adapter.notifyDataSetChanged();
                }
                if(coxoa)
                    xoaThongTin(vitrixoa);
                adapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

}