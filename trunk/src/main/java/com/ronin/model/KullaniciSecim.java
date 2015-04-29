package com.ronin.model;


import javax.persistence.*;

@Entity
@Table(name = "kullanici_secim")
public class KullaniciSecim {

    @Id
    @GeneratedValue
    @Column(name = "id",length = 5)
    private Long id;

    @Column(name = "kullanici_id",length = 6)
    private Long kullaniciId;

    @Column(name = "secim_key",length = 100)
    private String key;

    @Column(name = "secim_value")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(Long kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
