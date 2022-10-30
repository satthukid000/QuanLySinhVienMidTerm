package vn.edu.stu.doangiuakymobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutMeActivity extends AppCompatActivity {

    TextView tvPhoneNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

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
    }

    private void callPhoneNum() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:(+84)981545702"));
            startActivity(callIntent);


    }

    private void addControls() {
        tvPhoneNum = findViewById(R.id.tvPhoneNum);
    }
}