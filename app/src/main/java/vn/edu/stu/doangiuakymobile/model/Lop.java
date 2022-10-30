package vn.edu.stu.doangiuakymobile.model;

import java.io.Serializable;

public class Lop implements Serializable {
    String malop;
    String tenlop;

    public Lop() {
    }

    public Lop(String malop, String tenlop) {
        this.malop = malop;
        this.tenlop = tenlop;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }

    @Override
    public String toString() {
        return malop +
                " - " + tenlop;
    }
}
