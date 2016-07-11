package lv.ctco.jschool;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CAFE")
public class Cafe {

    @Id
    @GeneratedValue
    private int id;
    @Column
    private String cafeName;
    @Column
    private String phoneNr;

    public String getCafeName() {
        return cafeName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }
}
