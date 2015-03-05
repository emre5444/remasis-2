package com.ronin.model;

import com.ronin.common.model.Rol;
import com.ronin.model.constant.BildirimTipi;
import com.ronin.model.constant.Durum;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * Created by fcabi on 09.03.2014.
 */
@Entity
@Table(name = "bildirim_tipi_rol")
public class BildirimTipiRol {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "bildirim_tipi_id", referencedColumnName = "id")
    @ManyToOne
    private BildirimTipi bildirimTipi;

    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    @ManyToOne
    private Rol rol;

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

    public BildirimTipi getBildirimTipi() {
        return bildirimTipi;
    }

    public void setBildirimTipi(BildirimTipi bildirimTipi) {
        this.bildirimTipi = bildirimTipi;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Sirket getSirket() {
        return sirket;
    }

    public void setSirket(Sirket sirket) {
        this.sirket = sirket;
    }
}
