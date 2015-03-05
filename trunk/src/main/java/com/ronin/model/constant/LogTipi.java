package com.ronin.model.constant;

import com.ronin.model.BildirimTipiRol;
import com.ronin.model.Interfaces.IAbstractEntity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by fcabi on 09.03.2014.
 */

@Entity
@Table(name = "log_tipi")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "LogTipi.findAll", query = "SELECT b FROM LogTipi b")})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LogTipi implements IAbstractEntity {
    public enum ENUM {
        ////Buradaki siralama veri tabanindaki siralamadir.

        LOGIN(1L,"durum.aktif"),
        DAIRE_GORUNTULEME(2L,"durum.aktif");
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

    public LogTipi(ENUM e) {
        this.id = e.getId();
    }

    public LogTipi() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "kisa_aciklama")
    private String kisaAciklama;
    @Column(name = "aciklama")
    private String aciklama;

    public static LogTipi getLoginObject() {
        return new LogTipi(ENUM.LOGIN);
    }

    public static LogTipi getDaireGoruntuleObject() {
        return new LogTipi(ENUM.DAIRE_GORUNTULEME);
    }

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
        if (!(o instanceof LogTipi)) return false;

        LogTipi bildirimTipi = (LogTipi) o;

        if (id != null ? !id.equals(bildirimTipi.id) : bildirimTipi.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
