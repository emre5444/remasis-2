package com.ronin.model;

import com.ronin.model.constant.Durum;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ealtun on 09.03.2014.
 */
@Entity
@Table(name = "daire_borc")
public class DaireBorc {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "daire_id", referencedColumnName = "id")
    @ManyToOne
    private Daire daire;

    @JoinColumn(name = "borc_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Borc borc;

    @JoinColumn(name = "durum_id", referencedColumnName = "id")
    @ManyToOne
    private Durum durum;

    @OneToMany(mappedBy = "daireBorc",fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<DaireBorcKalem> daireBorcKalems;

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

    public Borc getBorc() {
        return borc;
    }

    public void setBorc(Borc borc) {
        this.borc = borc;
    }

    public Durum getDurum() {
        return durum;
    }

    public void setDurum(Durum durum) {
        this.durum = durum;
    }

    public List<DaireBorcKalem> getDaireBorcKalems() {
        return daireBorcKalems;
    }

    public void setDaireBorcKalems(List<DaireBorcKalem> daireBorcKalems) {
        this.daireBorcKalems = daireBorcKalems;
    }
}
