package vn.edu.stu.doangiuakymobile;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vn.edu.stu.doangiuakymobile.model.Lop;
import vn.edu.stu.doangiuakymobile.model.SinhVien;
import vn.edu.stu.doangiuakymobile.utils.FormatUtil;

public class CreateOrEditActivity extends AppCompatActivity {

    EditText etName, etID, etEmail;
    Spinner spinnerClass;

    TextView tvDOBCreate, tvAddImage;
    RadioGroup radioGroupGender;
    RadioButton radNam, radNu;
    MaterialButton btnCommit, btnBack;
    ImageView imageView;
    FrameLayout frameLayoutImagePicker;

    Bitmap bitmap;

    Calendar cal;

    String ma = "", ten = "", email = "", encodedAva = "";
    boolean phai = true;
    Date ngaysinh;

    ArrayList<Lop> dsLops;
    ArrayAdapter<Lop> adapter;
    int viTriLop = -1;

    int viTriSV = -1;
    public SinhVien chon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit);

        addControls();
        getSVInfo();
        addEvents();
    }

    private void addEvents() {

        tvDOBCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyChonNgay();
                //new DatePickerDialog(CreateOrEditActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                //btnCommit.performClick();
            }
        });
        radNam.setChecked(true);
        getSVInfo(); //hàm lấy thông tin sinh viên bỏ lên

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValid())
                    xuLyThemSua();
            }
        });

        frameLayoutImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyChupHinh();
            }
        });

    }

    private void xuLyChonNgay() {
        //đầu tiên, khởi tạo một calendar để lưu lại ngày tháng năm
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                cal.set(Calendar.YEAR, year); //chọn năm
                cal.set(Calendar.MONTH, month);  //điền tháng
                cal.set(Calendar.DAY_OF_MONTH, day);  //chọn ngày
                tvDOBCreate.setText(FormatUtil.formatDate(cal.getTime()));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateOrEditActivity.this,listener,cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        datePickerDialog.show();
    }

    private void xuLyChupHinh() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateOrEditActivity.this);
        builder.setTitle(R.string.PICTURE_METHOD);
        builder.setMessage(R.string.PICTURE_METHOD_SUB);
        builder.setPositiveButton(R.string.TAKE_PICTURE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 5);
            }
        });
        builder.setNegativeButton(R.string.CHOOSE_PICTURE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pickImage.launch(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean checkValid() {
        String id = etID.getText().toString();
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String dob = tvDOBCreate.getText().toString();


        if(TextUtils.isEmpty(id)) {
            Toast.makeText(this, "Please enter ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter NAME", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter EMAIL", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(isEmailValid(etEmail.getText().toString())!=true){
            Toast.makeText(this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(dob)) {
            Toast.makeText(this, "Please enter D.O.B", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(encodedAva)){
            Toast.makeText(this, "Please select IMAGE", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    boolean isEmailValid(CharSequence email) {  //kiểm tra email có hợp lệ hay không
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void getSVInfo() { //nếu vị trí sinh viên nằm trong mảng dsSV tức là đã chọn sinh viên
        Intent intent = getIntent();
        //viTriSV = intent.getIntExtra("vitrisv", -1);
        if (intent.hasExtra("CHON")) {
            chon = (SinhVien) intent.getSerializableExtra("CHON");
            if (chon != null) {
                etName.setText(chon.getTen());
                etID.setText(chon.getMa());
                etEmail.setText(chon.getEmail());
                tvDOBCreate.setText(FormatUtil.formatDate(chon.getNgaysinh()));
                if (chon.getPhai())
                    radNam.setChecked(true);
                else
                    radNu.setChecked(true);
                if (chon.getAvatarEncodedStr() != null) {
                    encodedAva=chon.getAvatarEncodedStr();
                    byte[] bytes = Base64.decode(chon.getAvatarEncodedStr(), Base64.DEFAULT);  //decode string image để tạo lại ảnh đại diện người dùng và hiện lên
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
        if(intent.hasExtra("DSLOP")){
            dsLops = (ArrayList<Lop>) intent.getSerializableExtra("DSLOP");
            if(dsLops!= null){
                //Toast.makeText(CreateOrEditActivity.this, "success", Toast.LENGTH_LONG).show();
                adapter = new ArrayAdapter<>(CreateOrEditActivity.this, android.R.layout.simple_spinner_dropdown_item,dsLops);
                spinnerClass.setAdapter(adapter);
            }
        }
    }

    private void xuLyThemSua() {
        ma = etID.getText().toString();
        ten = etName.getText().toString();
        email = etEmail.getText().toString();
        ngaysinh = cal.getTime();

        if (radNam.isChecked())
            phai = true;
        else
            phai = false;

        Intent intent2 = new Intent();
        Lop lop = (Lop) spinnerClass.getSelectedItem();
        chon = new SinhVien(ma, ten, email, ngaysinh, phai, lop, encodedAva);
        intent2.putExtra("TRA", chon); //trả lại học sinh thêm vào

        setResult(RESULT_OK, intent2);
        finish();
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


    private void addControls() {
        etName = findViewById(R.id.etName);
        etID = findViewById(R.id.etID);

        etEmail = findViewById(R.id.etEmail);
        tvDOBCreate = findViewById(R.id.tvDOBCreate);
        imageView = findViewById(R.id.imageViewAvatar);
        frameLayoutImagePicker = findViewById(R.id.frameLayoutImagePicker);
        tvAddImage = findViewById(R.id.tvAddImage);

        dsLops = new ArrayList<>();
        spinnerClass = findViewById(R.id.spinnerClass);

        radioGroupGender = findViewById(R.id.radioGroupGender);
        radNam = findViewById(R.id.radNam);
        radNu = findViewById(R.id.radNu);

        btnCommit = findViewById(R.id.btnCommit);
        btnBack = findViewById(R.id.btnBack);
        cal =Calendar.getInstance();

        chon = null;

        bitmap = null;
    }

    @Override
    public void onBackPressed() {
        getSVInfo();
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
        Intent intent = new Intent(CreateOrEditActivity.this, AboutMeActivity.class);
        startActivity(intent);
    }

    //toàn function để có thể chọn ảnh từ thư viện ảnh trong máy
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        Uri uriImage = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uriImage);
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            encodedAva = encodedImage(bitmap);
                            imageView.setImageBitmap(bitmap);
                            tvAddImage.setVisibility(View.GONE);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5) {
            if(resultCode==RESULT_OK){
                Uri uriImage = data.getData();
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
            }
        }
    }
}