package com.ronin.model;

import com.ronin.common.model.Kullanici;
import com.ronin.model.constant.Belge;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 27.03.2014
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "daire_belge")
public class DaireBelge {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;


    @JoinColumn(name = "daire_id", referencedColumnName = "id")
    @ManyToOne
    private Daire daire;

    @JoinColumn(name = "belge_id", referencedColumnName = "id")
    @ManyToOne
    private Belge belge;

    @JoinColumn(name = "talep_id", referencedColumnName = "id")
    @ManyToOne
    private Talep talep;

    public DaireBelge(Belge belge) {
        this.belge = belge;
    }

    public DaireBelge() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Daire getDaire() {
        return daire;
    }

    public void setDaire(Daire daire) {
        this.daire = daire;
    }

    public Belge getBelge() {
        return belge;
    }

    public void setBelge(Belge belge) {
        this.belge = belge;
    }

    public Talep getTalep() {
        return talep;
    }

    public void setTalep(Talep talep) {
        this.talep = talep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DaireBelge)) return false;

        DaireBelge that = (DaireBelge) o;

        if (belge != null ? !belge.equals(that.belge) : that.belge != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (talep != null ? !talep.equals(that.talep) : that.talep != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (belge != null ? belge.hashCode() : 0);
        result = 31 * result + (talep != null ? talep.hashCode() : 0);
        return result;
    }
}
