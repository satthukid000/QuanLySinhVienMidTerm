package vn.edu.stu.doangiuakymobile.model;

import android.media.Image;

import java.io.Serializable;
import java.util.Date;

public class SinhVien implements Serializable {
    String ma;
    String ten;
    String email;
    String ngaysinh;
    Boolean phai;
    Image avatar;
    Lop lop;

    public SinhVien() {
    }

    public SinhVien(String ma, String ten, String email, String ngaysinh, Boolean phai, Image avatar) {
        this.ma = ma;
        this.ten = ten;
        this.email = email;
        this.ngaysinh = ngaysinh;
        this.phai = phai;
        this.avatar = avatar;
    }

    public SinhVien(String ma, String ten, String email, String ngaysinh, Boolean phai) {
        this.ma = ma;
        this.ten = ten;
        this.email = email;
        this.ngaysinh = ngaysinh;
        this.phai = phai;
    }

    public SinhVien(String ma, String ten, String email, String ngaysinh, Boolean phai, Image avatar, Lop lop) {
        this.ma = ma;
        this.ten = ten;
        this.email = email;
        this.ngaysinh = ngaysinh;
        this.phai = phai;
        this.avatar = avatar;
        this.lop = lop;
    }

    public SinhVien(String ma, String ten, String email, String ngaysinh, Boolean phai, Lop lop) {
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

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public Boolean getPhai() {
        return phai;
    }

    public void setPhai(Boolean phai) {
        this.phai = phai;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

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
                    "\nNgày sinh: " + ngaysinh +
                    "\nPhái: Nam" +
                    "\nLớp: " + lop;
        } else {
            return "Mã: " + ma +
                    "\nTên: " + ten +
                    "\nemail=" + email +
                    "\nNgày sinh: " + ngaysinh +
                    "\nPhái: Nữ" +
                    "\nLớp: " + lop;
        }
    }
}
