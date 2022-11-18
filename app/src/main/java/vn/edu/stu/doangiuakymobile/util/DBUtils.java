package vn.edu.stu.doangiuakymobile.util;

import android.app.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DBUtils {
    public  static void copyDBFileFromAsset(Activity context, String DB_NAME, String DB_PATH_SUFFIX){
        File dbFile = context.getDatabasePath(DB_NAME);
        if(!dbFile.exists()){
            File dbDir = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!dbDir.exists()){
                dbDir.mkdir();
            }
            try {
                InputStream is = context.getAssets().open(DB_NAME);
                String outputFilePath = context.getApplicationInfo().dataDir+DB_PATH_SUFFIX+DB_NAME;
                OutputStream os = new FileOutputStream(outputFilePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer))>0){
                    os.write(buffer,0,length);
                }
                os.flush();
                os.close();
                is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
