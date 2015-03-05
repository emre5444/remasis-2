package com.ronin.model.constant;

import com.ronin.model.Interfaces.IAbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ealtun on 08.03.2014.
 */
@Entity
@Table(name = "daire_tipi")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "DaireTipi.findAll", query = "SELECT r FROM DaireTipi r")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DaireTipi implements IAbstractEntity {

    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        BIR_ARTI_BIR(1L,"durum.aktif"),//1
        IKI_ARTI_BIR(2L,"durum.pasif"),//2
        UC_ARTI_BIR(3L,"durum.silinmis"),//3
        DORT_ARTI_BIR(4L,"durum.silinmis");//3

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DaireTipi)) return false;

        DaireTipi daireTipi = (DaireTipi) o;

        if (id != null ? !id.equals(daireTipi.id) : daireTipi.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
