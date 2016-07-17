package lv.ctco.jschool.entities;

import javax.persistence.Column;

public class CafeDTO {

    private String cafeTitle;

    private String phoneNr;

    public String getCafeTitle() {
        return cafeTitle;
    }

    public void setCafeTitle(String cafeTitle) {
        this.cafeTitle = cafeTitle;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }
}
