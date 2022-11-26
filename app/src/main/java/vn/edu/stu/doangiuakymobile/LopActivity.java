package vn.edu.stu.doangiuakymobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.stu.doangiuakymobile.model.Lop;
import vn.edu.stu.doangiuakymobile.model.SinhVien;

public class LopActivity extends AppCompatActivity {
    Button btnAddClass, btnStudenManager;
    ListView lvLop;
    ArrayList<Lop> dsLops;
    ArrayAdapter<Lop> adapter;

    ArrayList<SinhVien> dsSinhViens;
    int RESULT_CODE_CLASS = 116;
    Lop lop;
    int vitriLop = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lop);
        addControls();
        getLopData();
        addEvents();

    }

    private void getLopData() {
//        Lop lop1= new Lop("Lớp 1", "Công nghệ thông tin");
//        Lop lop2= new Lop("Lớp 2", "Quản trị kinh doanh");
//        Lop lop3 = new Lop("Lớp 3", "Kinh tế");
//        dsLops.add(lop1);
//        dsLops.add(lop2);
//        dsLops.add(lop3);
        Intent intent = getIntent();
        if (intent.hasExtra("DSLOP")) {
            dsLops = (ArrayList<Lop>) intent.getSerializableExtra("DSLOP");
        }
    }

    private void addEvents() {
        btnStudenManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LopActivity.this, CreateOrEditClassActivity.class);
                startActivityForResult(intent, RESULT_CODE_CLASS);
            }
        });
        lvLop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitriLop = i;
                lop = adapter.getItem(i);
                Intent intent = new Intent(LopActivity.this, CreateOrEditClassActivity.class);
                intent.putExtra("LOP", lop);
                startActivityForResult(intent, RESULT_CODE_CLASS);
            }
        });
        lvLop.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                xuLyXoa(i);
                return true;
            }
        });
    }

    private void xuLyXoa(int pos) {

        Boolean checkXoa = checkXemLopCoSinhVien(pos);
        if (pos >= 0 && pos < adapter.getCount()) {
            if(checkXoa!=true) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LopActivity.this);
                builder.setTitle(R.string.confirm_delete);
                builder.setMessage(R.string.confirm_delete_sub);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        lop = adapter.getItem(pos);
                        adapter.remove(lop);
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
            }else{
                AlertDialog.Builder builder2 = new AlertDialog.Builder(LopActivity.this);
                builder2.setTitle("ALERT");
                builder2.setMessage(R.string.no_delete_class);
                builder2.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {

                    }
                });
                AlertDialog dialog = builder2.create();
                dialog.show();
            }
        }
    }

    private Boolean checkXemLopCoSinhVien(int pos) {
        for (SinhVien sv : dsSinhViens) {
            if (sv.getLop().getMalop().toString().equalsIgnoreCase(dsLops.get(pos).getMalop().toString())) {
                return true; //nếu có sinh viên trong lớp thì trả về true
            }
        }
        return false;
    }


    private void addControls() {
        btnAddClass = findViewById(R.id.btnAddClass);
        btnStudenManager = findViewById(R.id.btnStudenManager);
        lvLop = findViewById(R.id.lvLop);
        dsLops = new ArrayList<>();
        Intent intent = getIntent();
        if (intent.hasExtra("DSLOP")) {
            dsLops = (ArrayList<Lop>) intent.getSerializableExtra("DSLOP");
        }
        adapter = new ArrayAdapter<>(LopActivity.this, android.R.layout.simple_list_item_1, dsLops);
        lvLop.setAdapter(adapter);
        lop = null;

        dsSinhViens = new ArrayList<>();
        if (intent.hasExtra("DSSV")) {
            dsSinhViens = (ArrayList<SinhVien>) intent.getSerializableExtra("DSSV");
            Toast.makeText(this, dsSinhViens.get(0).toString(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CODE_CLASS) {
            if (resultCode == 1) { //result code 1 tức là bật activity CreateOrEdit class lên
                if (data.hasExtra("TRALOP")) {
                    Lop tra = (Lop) data.getSerializableExtra("TRALOP");
                    if (lop == null) {
                        dsLops.add(tra);
                        adapter.notifyDataSetChanged();
                    } else {
                        lop.setMalop(tra.getMalop());
                        lop.setTenlop(tra.getTenlop());
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
        lop = null;
    }

    @Override
    public void onBackPressed() {
        Intent intent2 = new Intent();
        intent2.putExtra("DSLOPTRA", dsLops);
        setResult(RESULT_OK, intent2);
        finish();
    }
}