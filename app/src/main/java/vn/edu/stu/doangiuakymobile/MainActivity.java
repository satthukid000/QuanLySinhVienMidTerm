package vn.edu.stu.doangiuakymobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vn.edu.stu.doangiuakymobile.adapter.SinhVienAdapter;
import vn.edu.stu.doangiuakymobile.model.Lop;
import vn.edu.stu.doangiuakymobile.model.SinhVien;

public class MainActivity extends AppCompatActivity {
    ListView lvSinhVien;

    ArrayList<SinhVien> dsSinhViens;
    ArrayList<Lop> dsLops;
    //ArrayAdapter<SinhVien> adapter;

    //adapter đã chỉnh
    SinhVienAdapter adapter;
    Button btnAdd, btnClassManager;

    int vitriSinhVien = -2;

    SinhVien chon;
    Lop lop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
        addSinhVienExample();
    }


    private void addSinhVienExample() {
        Bitmap bitmapAva1 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ava_ex1);
        String encodedAva1 = encodedImage(bitmapAva1);
        Bitmap bitmapAva2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ava_ex2);
        String encodedAva2 = encodedImage(bitmapAva2);
        Bitmap bitmapAva3 = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ava_ex3);
        String encodedAva3 = encodedImage(bitmapAva3);

        Lop lop1 = new Lop("Lớp 1", "Công nghệ");
        Lop lop2 = new Lop("Lớp 2", "Kiến trúc");
        Lop lop3 = new Lop("Lớp 3", "Kỹ Sư");

        dsLops.add(lop1);
        dsLops.add(lop2);
        dsLops.add(lop3);

        String sDate1 = "31/12/1998";
        String sDate2 = "23/05/2001";
        String sDate3 = "25/07/2000";
        Date date1;
        Date date2;
        Date date3;
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
            date3 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate3);

            SinhVien sv1 = new SinhVien("DH51905495", "Nguyễn Trung Kiên", "kienkien@gmail.com", date1, true, lop1, encodedAva1);
            SinhVien sv2 = new SinhVien("DH51905123", "Hồ Thị Ngọc Hương", "b@gmail.com", date2, false, lop1, encodedAva2);
            SinhVien sv3 = new SinhVien("DH51999999", "Nguyễn Văn Thanh Đức", "ducducthanh2305@gmail.com", date3, true, lop2, encodedAva3);

            dsSinhViens.add(sv1);
            dsSinhViens.add(sv2);
            dsSinhViens.add(sv3);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    private void addEvents() {
        registerForContextMenu(lvSinhVien); //đăng ký context menu cho list view
        lvSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitriSinhVien = i;
                chon = adapter.getItem(i);
                xemChiTietThongTin();
            }
        });
        lvSinhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitriSinhVien = i;
                chon = adapter.getItem(i);
                showPopup(view);
                return true;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateOrEditActivity.class);
                intent.putExtra("DSLOP", dsLops);
                startActivityForResult(intent, 1);
            }
        });
        btnClassManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyClassManager();
            }
        });

    }

    private void xuLyClassManager() {
        Intent intent = new Intent(MainActivity.this, LopActivity.class);
        intent.putExtra("DSLOP", dsLops);
        intent.putExtra("DSSV", dsSinhViens);
        startActivityForResult(intent, 116);
    }


    private void addControls() {
        lvSinhVien = findViewById(R.id.lvSinhVien);
        dsSinhViens = new ArrayList<>();
        adapter = new SinhVienAdapter(MainActivity.this, R.layout.item_sinhvien_container, dsSinhViens);
        lvSinhVien.setAdapter(adapter);
        dsLops = new ArrayList<>();

        btnAdd = findViewById(R.id.btnAdd);
        chon = null;
        vitriSinhVien = -1;

        btnClassManager = findViewById(R.id.btnAddClass);

    }

    public void showPopup(View v) { //dùng để tạo pop up menu context
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_context, popup.getMenu());
        popup.setOnMenuItemClickListener(this::onContextItemSelected);//sẽ đồng thời có công dụng như hàm bên dưới, vì hàm dưới đã code sẵn chức năng
        popup.show();
    }

    public void xemChiTietThongTin() {
        //new code here
        Intent intent = new Intent(getApplicationContext(), ViewChiTietActivity.class);
        intent.putExtra("vitrisv", vitriSinhVien);
        intent.putExtra("CHON", chon); //đưa sinh viên đã chọn sang bên kia
        intent.putExtra("DSLOP", dsLops);
        startActivityForResult(intent, 2);
    }


    public void chinhSuaChiTietThongTin() {
        //code mới
        Intent intent = new Intent(getApplicationContext(), CreateOrEditActivity.class);
        intent.putExtra("vitrisv", vitriSinhVien);
        intent.putExtra("CHON", chon); //đưa sinh viên đã chọn sang bên kia
        intent.putExtra("DSLOP", dsLops);
        startActivityForResult(intent, 1);
    }

    private void openAboutMe() {
        Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
        startActivity(intent);
    }

    //hàm dùng để mã hoá Bitmap về chuỗi string nhằm lưu trữ hình ảnh lại
    public String encodedImage(Bitmap bitmap) {
        int previewWidth = 200;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT); //trả về chuỗi string đã được mã hoá dựa trên thuật toán Bitmap
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.confirm_delete);
            builder.setMessage(R.string.confirm_delete_sub);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int j) {
                    dsSinhViens.remove(i);
                    adapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int j) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Request Code 1 tức là bật từ menu Edit trong main, ta sẽ lấy dữ liệu từ
        //CreateOrEdit về và cập nhật lại dsSinhViens
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //code mới
                if (data.hasExtra("TRA")) {
                    SinhVien tra = (SinhVien) data.getSerializableExtra("TRA");
                    if (chon == null) {
                        adapter.add(tra);
                    } else {
                        chon.setTen(tra.getTen());
                        chon.setMa(tra.getMa());
                        chon.setEmail(tra.getEmail());
                        chon.setNgaysinh(tra.getNgaysinh());
                        chon.setPhai(tra.getPhai());
                        chon.setLop(tra.getLop());
                        if (tra.getAvatarEncodedStr() != null) {
                            chon.setAvatarEncodedStr(tra.getAvatarEncodedStr());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();
            }
        }
        //116 là chuyển sang LopActivity để thêm lớp và lấy danh sách lớp về lại main.
        else if (requestCode == 116) {
            if (resultCode == RESULT_OK) {
                if(data.hasExtra("DSLOPTRA")) {
                    dsLops = (ArrayList<Lop>) data.getSerializableExtra("DSLOPTRA");
//                    if(dsLops!= null)
//                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
                }
            }
        }

        //Request code = 2 tức là ta Edit từ ViewChiTiet
        //ViewChiTiet sẽ truyền code 2, và truyền thông tin sinh viên về lại qua main để cập nhật
        else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
//code mới
                if (data.hasExtra("TRA2")) {
                    SinhVien tra = (SinhVien) data.getSerializableExtra("TRA2");
                    if (chon == null) {
                        adapter.add(tra);
                    } else {
                        chon.setTen(tra.getTen());
                        chon.setMa(tra.getMa());
                        chon.setEmail(tra.getEmail());
                        chon.setNgaysinh(tra.getNgaysinh());
                        chon.setPhai(tra.getPhai());
                        chon.setLop(tra.getLop());
                        if (tra.getAvatarEncodedStr() != null) {
                            chon.setAvatarEncodedStr(tra.getAvatarEncodedStr());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();
                int vitrixoa;
                vitrixoa = data.getIntExtra("vitrisinhvien", -1);
                Boolean coxoa;
                coxoa = data.getBooleanExtra("coxoa", false);
                if (coxoa) {
                    if(vitrixoa>=0 && vitrixoa<adapter.getCount()) {
                        dsSinhViens.remove(vitrixoa);
                        adapter.notifyDataSetChanged();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
        chon = null;
        vitriSinhVien = -1;
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

}