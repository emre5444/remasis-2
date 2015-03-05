/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.common.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author esimsek
 */
@Entity
@Table(name = "rol_yetki")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolYetki.findAll", query = "SELECT r FROM RolYetki r"),
    @NamedQuery(name = "RolYetki.findById", query = "SELECT r FROM RolYetki r WHERE r.id = :id")})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RolYetki implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "yetki_id", referencedColumnName = "id")
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Yetki yetki;
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Rol rol;

    public RolYetki() {
    }

    public RolYetki(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Yetki getYetki() {
        return yetki;
    }

    public void setYetki(Yetki yetki) {
        this.yetki = yetki;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolYetki)) {
            return false;
        }
        RolYetki other = (RolYetki) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ronin.model.RolYetki[ id=" + id + " ]";
    }
    
}
