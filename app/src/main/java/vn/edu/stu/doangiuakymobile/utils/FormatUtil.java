package vn.edu.stu.doangiuakymobile.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static  String formatDate(Date date){
        return simpleDateFormat.format(date);
    }
}
