package com.ronin.model.constant;

import com.ronin.model.Interfaces.IAbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ealtun on 09.03.2014.
 */

@Entity
@Table(name = "talep_onay_durumu")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "TalepOnayDurumu.findAll", query = "SELECT r FROM TalepOnayDurumu r")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TalepOnayDurumu implements IAbstractEntity {
    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        ARIZA_TALEBI_ONAY_BEKLIYOR(1L,"durum.aktif"),//1
        ARIZA_TALEBI_ONAYLANDI(2L,"durum.aktif"),//1
        ARIZA_TALEBI_REDDEDILDI(3L,"durum.pasif"),//2

        ITIRAZ_TALEBI_ONAY_BEKLIYOR(4L,"durum.aktif"),//1
        ITIRAZ_TALEBI_ONAYLANDI(5L,"durum.aktif"),//1
        ITIRAZ_TALEBI_REDDEDILDI(6L,"durum.pasif"),//2

        SIKAYET_TALEBI_ONAY_BEKLIYOR(7L,"durum.aktif"),//1
        SIKAYET_TALEBI_ONAYLANDI(8L,"durum.aktif"),//1
        SIKAYET_TALEBI_REDDEDILDI(9L,"durum.pasif"),//2

        BELGE_TALEBI_ONAY_BEKLIYOR(10L,"durum.aktif"),//1
        BELGE_TALEBI_ONAYLANDI(11L,"durum.aktif"),//1
        BELGE_TALEBI_REDDEDILDI(12L,"durum.pasif");//2

        private Long id;
        private String label;

        private ENUM(Long id,String label){
            this.id=id;
            this.label=label;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public TalepOnayDurumu(ENUM e) {
        this.id = e.getId();
    }

    public TalepOnayDurumu() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "kisa_aciklama")
    private String kisaAciklama;

    @Column(name = "aciklama")
    private String aciklama;

    @ManyToOne
    @JoinColumn(name = "TALEP_YAPILDI_MI" , referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private EvetHayir talepYapildiMi;

    @ManyToOne
    @JoinColumn(name = "ONAYLANDI_MI" , referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private EvetHayir onaylandiMi;

    @ManyToOne
    @JoinColumn(name = "REDDEDILDI_MI" , referencedColumnName = "id")
    @Fetch(FetchMode.SELECT)
    private EvetHayir reddedildiMi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKisaAciklama() {
        return kisaAciklama;
    }

    public void setKisaAciklama(String kisaAciklama) {
        this.kisaAciklama = kisaAciklama;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public EvetHayir getReddedildiMi() {
        return reddedildiMi;
    }

    public void setReddedildiMi(EvetHayir reddedildiMi) {
        this.reddedildiMi = reddedildiMi;
    }

    public EvetHayir getOnaylandiMi() {
        return onaylandiMi;
    }

    public void setOnaylandiMi(EvetHayir onaylandiMi) {
        this.onaylandiMi = onaylandiMi;
    }

    public EvetHayir getTalepYapildiMi() {
        return talepYapildiMi;
    }

    public void setTalepYapildiMi(EvetHayir talepYapildiMi) {
        this.talepYapildiMi = talepYapildiMi;
    }

    public boolean isArizaTalebiOnayBekliyor(){
        return this.getId().equals(ENUM.ARIZA_TALEBI_ONAY_BEKLIYOR.getId());
    }

    public boolean isTalepOnayGormusMu(){
        return this.getTalepYapildiMi().isHayirMi();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TalepOnayDurumu)) return false;

        TalepOnayDurumu that = (TalepOnayDurumu) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
