package com.ronin.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 27.03.2014
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "anket_secim")
public class AnketSecim {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "anket_id", referencedColumnName = "id")
    @ManyToOne
    private Anket anket;

    @Column(name = "secim")
    private String secim;

    @Column(name = "tanitim_zamani")
    private Date tanitimZamani;


    public Date getTanitimZamani() {
        return tanitimZamani;
    }

    public void setTanitimZamani(Date tanitimZamani) {
        this.tanitimZamani = tanitimZamani;
    }

    public String getSecim() {
        return secim;
    }

    public void setSecim(String secim) {
        this.secim = secim;
    }

    public Anket getAnket() {
        return anket;
    }

    public void setAnket(Anket anket) {
        this.anket = anket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnketSecim)) return false;

        AnketSecim that = (AnketSecim) o;

        if (secim != null ? !secim.equals(that.secim) : that.secim != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return secim != null ? secim.hashCode() : 0;
    }
}
