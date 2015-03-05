package com.ronin.model.constant;

import com.ronin.model.Sirket;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 07.04.2014
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "iletisim_bilgileri")
public class IletisimBilgileri {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "bilgi_tipi")
    String bilgiTipi;

    @Column(name = "bilgi_tipi_ack")
    String bilgiTipiAck;

    @Column(name = "aciklama")
    String iletisimBilgisi;

    @ManyToOne
    @JoinColumn(name = "SIRKET_ID", referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private Sirket sirket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBilgiTipi() {
        return bilgiTipi;
    }

    public void setBilgiTipi(String bilgiTipi) {
        this.bilgiTipi = bilgiTipi;
    }

    public String getBilgiTipiAck() {
        return bilgiTipiAck;
    }

    public void setBilgiTipiAck(String bilgiTipiAck) {
        this.bilgiTipiAck = bilgiTipiAck;
    }

    public String getIletisimBilgisi() {
        return iletisimBilgisi;
    }

    public void setIletisimBilgisi(String iletisimBilgisi) {
        this.iletisimBilgisi = iletisimBilgisi;
    }

    public Sirket getSirket() {
        return sirket;
    }

    public void setSirket(Sirket sirket) {
        this.sirket = sirket;
    }
}
