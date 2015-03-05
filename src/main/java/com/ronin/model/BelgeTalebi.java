package com.ronin.model;

import com.ronin.model.constant.BelgeTipi;

import javax.persistence.*;

/**
 * Created by ealtun on 09.03.2014.
 */
@Entity
@Table(name = "belge_talebi")
public class BelgeTalebi extends Talep{

    public BelgeTalebi() {
    }

    @JoinColumn(name = "belge_tipi_id", referencedColumnName = "id")
    @ManyToOne
    private BelgeTipi belgeTipi;


    public BelgeTipi getBelgeTipi() {
        return belgeTipi;
    }

    public void setBelgeTipi(BelgeTipi belgeTipi) {
        this.belgeTipi = belgeTipi;
    }
}
