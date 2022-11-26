package vn.edu.stu.doangiuakymobile.model;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;
import java.util.Date;

import vn.edu.stu.doangiuakymobile.utils.FormatUtil;

public class SinhVien implements Serializable {
    String ma;
    String ten;
    String email;
    Date ngaysinh;
    Boolean phai;
    //Bitmap avatar; //vì không thể truyền kiểu bitmap qua serializable nên phải dùng kiểu dưới
    String avatarEncodedStr; //cái này dùng để mã hoá bitmap lại về chuỗi string

    public String getAvatarEncodedStr() {
        return avatarEncodedStr;
    }

    public void setAvatarEncodedStr(String avatarEncodedStr) {
        this.avatarEncodedStr = avatarEncodedStr;
    }

    Lop lop;

    public SinhVien() {
    }

//    public SinhVien(String ma, String ten, String email, String ngaysinh, Boolean phai, Bitmap avatar) {
//        this.ma = ma;
//        this.ten = ten;
//        this.email = email;
//        this.ngaysinh = ngaysinh;
//        this.phai = phai;
//        //this.avatar = avatar;
//    }

    public SinhVien(String ma, String ten, String email, Date ngaysinh, Boolean phai, String avatarEncodedStr) {
        this.ma = ma;
        this.ten = ten;
        this.email = email;
        this.ngaysinh = ngaysinh;
        this.phai = phai;
        this.avatarEncodedStr = avatarEncodedStr;
    }


    public SinhVien(String ma, String ten, String email, Date ngaysinh, Boolean phai) {
        this.ma = ma;
        this.ten = ten;
        this.email = email;
        this.ngaysinh = ngaysinh;
        this.phai = phai;
    }

//    public SinhVien(String ma, String ten, String email, String ngaysinh, Boolean phai, Lop lop, Bitmap avatar) {
//        this.ma = ma;
//        this.ten = ten;
//        this.email = email;
//        this.ngaysinh = ngaysinh;
//        this.phai = phai;
//        this.avatar = avatar;
//        this.lop = lop;
//    }
    public SinhVien(String ma, String ten, String email, Date ngaysinh, Boolean phai, Lop lop, String avatarEncodedStr) {
        this.ma = ma;
        this.ten = ten;
        this.email = email;
        this.ngaysinh = ngaysinh;
        this.phai = phai;
        this.avatarEncodedStr = avatarEncodedStr;
        this.lop = lop;
    }

    public SinhVien(String ma, String ten, String email, Date ngaysinh, Boolean phai, Lop lop) {
        this.ma = ma;
        this.ten = ten;
        this.email = email;
        this.ngaysinh = ngaysinh;
        this.phai = phai;
        this.lop = lop;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public Boolean getPhai() {
        return phai;
    }

    public void setPhai(Boolean phai) {
        this.phai = phai;
    }

//    public Bitmap getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(Bitmap avatar) {
//        this.avatar = avatar;
//    }

    public Lop getLop() {
        return lop;
    }

    public void setLop(Lop lop) {
        this.lop = lop;
    }

    @Override
    public String toString() {
        if (phai) {
            return "Mã: " + ma +
                    "\nTên: " + ten +
                    "\nemail: " + email +
                    "\nNgày sinh: " + FormatUtil.formatDate(ngaysinh) +
                    "\nPhái: Nam" +
                    "\nLớp: " + lop;
        } else {
            return "Mã: " + ma +
                    "\nTên: " + ten +
                    "\nemail=" + email +
                    "\nNgày sinh: " + FormatUtil.formatDate(ngaysinh) +
                    "\nPhái: Nữ" +
                    "\nLớp: " + lop;
        }
    }
}
