package vn.edu.stu.doangiuakymobile;

import androidx.annotation.NonNull;
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

        SinhVien sv1 = new SinhVien("DH51905495", "Nguyễn Văn Thanh Đức", "ducducthanh2305@gmail.com", "23/05/2001",true, lop1);
        SinhVien sv2 = new SinhVien("DH51905123", "Hồ Thị Ngọc Hương", "b@gmail.com", "26/05/2001",false, lop1);
        SinhVien sv3 = new SinhVien("DH51999999", "Nguyễn Thanh Bằng", "bangbang@gmail.com", "22/05/2001",true, lop2);

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
                startActivity(intent);
            }
        });
    }



    private void addControls() {
        lvSinhVien = findViewById(R.id.lvSinhVien);
        dsSinhViens = new ArrayList<>();
        adapter =new SinhVienAdapter(MainActivity.this, R.layout.item_sinhvien_container, dsSinhViens);
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

    public void xemChiTietThongTin(){
        Intent intent = new Intent(MainActivity.this, ViewChiTietActivity.class);
        intent.putExtra("vitrisv", vitriSinhVien); //truyền vị trí sinh viên qua bên View Chi Tiết
        //Bundle args = new Bundle(); //dùng cái này để chứa nguyên cái dsSinhViens để quăng qua bên view chi tiết
        intent.putExtra("danhsach", dsSinhViens); //đưa dsSinhViens dưới dạng serializable với tên danhsach
        //intent.putExtra("Bundle", args);    //truyền args vào view chi tiết với tên Bundle
        startActivity(intent);
    }

    public void chinhSuaChiTietThongTin(){
        Intent intent = new Intent(MainActivity.this, CreateOrEditActivity.class);
        intent.putExtra("vitrisv", vitriSinhVien); //truyền vị trí sinh viên qua bên View Thêm và chỉnh sửa
        intent.putExtra("danhsach", dsSinhViens); //đưa dsSinhViens dưới dạng serializable với tên danhsach
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.lvSinhVien)
        {
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
                xoaThongTin();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void xoaThongTin() {
        if(vitriSinhVien>=0 && vitriSinhVien<dsSinhViens.size()){
            dsSinhViens.remove(vitriSinhVien);
            adapter.notifyDataSetChanged();
        }
    }
}