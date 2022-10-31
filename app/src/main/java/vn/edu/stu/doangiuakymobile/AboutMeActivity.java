package vn.edu.stu.doangiuakymobile;

import static android.Manifest.permission.CALL_PHONE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class AboutMeActivity extends AppCompatActivity {
    MaterialButton btnBack;
    TextView tvPhoneNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        requestPermissions(new String[]{CALL_PHONE}, 1);
        addControls();
        addEvents();
    }

    private void addEvents() {
        tvPhoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNum();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void callPhoneNum() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:(+84)981545702"));
            startActivity(callIntent);
    }

    private void addControls() {
        btnBack = findViewById(R.id.btnBack);
        tvPhoneNum = findViewById(R.id.tvPhoneNum);
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
        Intent intent = new Intent(AboutMeActivity.this, AboutMeActivity.class);
        startActivity(intent);
    }

}
