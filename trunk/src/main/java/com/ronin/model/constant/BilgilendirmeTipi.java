package com.ronin.model.constant;

import com.ronin.model.Interfaces.IAbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "BilgilendirmeTipi.findAll", query = "SELECT d FROM BilgilendirmeTipi d")})
@Table(name = "durum")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BilgilendirmeTipi implements IAbstractEntity {

    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        Email(1L,"durum.aktif"),//1
        Notification(2L,"durum.pasif");//2

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

    public BilgilendirmeTipi(ENUM e) {
        this.id = e.getId();
    }

    public BilgilendirmeTipi() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "kisa_aciklama")
    private String kisaAciklama;

    @Column(name = "aciklama")
    private String aciklama;

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

    public boolean isEmail(){
        return this.id.equals(ENUM.Email.getId());
    }

    public boolean isNotification(){
        return this.id.equals(ENUM.Notification.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BilgilendirmeTipi)) return false;

        BilgilendirmeTipi durum = (BilgilendirmeTipi) o;

        if (id != null ? !id.equals(durum.id) : durum.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
