package com.ronin.model;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.EvetHayir;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@Table(name = "sistem_hata")
public class SistemHata {

    @Id
    @GeneratedValue
    @Column(name = "id",length = 10)
    private Long id;

    @JoinColumn(name = "kullanici_id", referencedColumnName = "id")
    @ManyToOne
    private Kullanici kullanici;

    @Column(name = "message",length = 4096)
    private String message;

    @Column(name = "stack_trace",length = 4096)
    private String stackTrace;

    @Column(name = "tanitim_zamani")
    private Date tanitimZamani;

    @ManyToOne
    @JoinColumn(name = "SIRKET_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Sirket sirket;

    public void initSistemHata(SessionInfo sessionInfo , String message , String stakTrace){
        this.setMessage(message);
        this.setStackTrace(stakTrace);
        this.setKullanici(sessionInfo.getKullanici());
        this.setTanitimZamani(new Date());
        this.setSirket(sessionInfo.getSirket());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Date getTanitimZamani() {
        return tanitimZamani;
    }

    public void setTanitimZamani(Date tanitimZamani) {
        this.tanitimZamani = tanitimZamani;
    }

    public Sirket getSirket() {
        return sirket;
    }

    public void setSirket(Sirket sirket) {
        this.sirket = sirket;
    }
}
