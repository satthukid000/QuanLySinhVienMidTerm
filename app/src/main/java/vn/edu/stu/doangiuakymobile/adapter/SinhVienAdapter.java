package vn.edu.stu.doangiuakymobile.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import vn.edu.stu.doangiuakymobile.R;
import vn.edu.stu.doangiuakymobile.model.SinhVien;

public class SinhVienAdapter extends ArrayAdapter<SinhVien> {
    Activity context;
    int resource;
    List<SinhVien> objects;

    public SinhVienAdapter(
            Activity context,
            int resource,
            List<SinhVien> objects) {
        super(context,resource,objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();

        View item = inflater.inflate(this.resource,null);

        //lấy 3 text view từ layout đã tạo ra
        TextView tvMaSV = item.findViewById(R.id.tvMaSV);
        TextView tvTenSV = item.findViewById(R.id.tvTenSV);
        TextView tvLopSV = item.findViewById(R.id.tvLopSV);

        //lệnh vẽ liên tục
        SinhVien sv = this.objects.get(position);
        tvMaSV.setText(sv.getMa().toString());
        tvTenSV.setText(sv.getTen().toString());
        tvLopSV.setText(sv.getLop().toString());


        return item;
    }
}
