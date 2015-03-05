package com.ronin.model;

import com.ronin.model.constant.Blok;
import com.ronin.model.constant.DaireTipi;
import com.ronin.model.constant.Durum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Entity
@Table(name = "talep_daire")
public class TalepDaire {

    public TalepDaire() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TALEP_ID" , referencedColumnName = "id")
    private Talep talep;

    @ManyToOne
    @JoinColumn(name = "DAIRE_ID" , referencedColumnName = "id")
    private Daire daire;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Talep getTalep() {
        return talep;
    }

    public void setTalep(Talep talep) {
        this.talep = talep;
    }

    public Daire getDaire() {
        return daire;
    }

    public void setDaire(Daire daire) {
        this.daire = daire;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

}