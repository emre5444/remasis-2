package com.ronin.model.constant;

import com.ronin.model.Interfaces.IAbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ealtun on 08.03.2014.
 */
@Entity
@Table(name = "evet_hayir")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "EvetHayir.findAll", query = "SELECT r FROM EvetHayir r")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EvetHayir implements IAbstractEntity {

    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        EVET_VAR(2L,"durum.aktif"),//1
        HAYIR_YOK(1L,"durum.pasif");//2

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

    public EvetHayir(ENUM e) {
        this.id = e.getId();
    }

    public EvetHayir() {
    }

    @Id
    @GeneratedValue
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

    public static EvetHayir getEvetObject() {
        return new EvetHayir(ENUM.EVET_VAR);
    }

    public static EvetHayir getHayirObject() {
        return new EvetHayir(ENUM.HAYIR_YOK);
    }

    public boolean isEvetMi(){
        return this.getId().equals(ENUM.EVET_VAR.getId());
    }

    public boolean isHayirMi(){
        return this.getId().equals(ENUM.HAYIR_YOK.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EvetHayir)) return false;

        EvetHayir evetHayir = (EvetHayir) o;

        if (id != null ? !id.equals(evetHayir.id) : evetHayir.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
