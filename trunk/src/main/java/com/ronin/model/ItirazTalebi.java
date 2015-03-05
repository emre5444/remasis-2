package com.ronin.model;

import com.ronin.model.constant.Banka;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ealtun on 09.03.2014.
 */
@Entity
@Table(name = "aidat_itiraz_talebi")
public class ItirazTalebi extends Talep{

    public ItirazTalebi() {
    }

    @JoinColumn(name = "daire_borc_id", referencedColumnName = "id")
    @ManyToOne
    private DaireBorc daireBorc;

    public List<DaireBorc> getDaireBorcList(){
        List<DaireBorc> borcList = new ArrayList<>();
        borcList.add(getDaireBorc());
        return borcList;
    }


    public DaireBorc getDaireBorc() {
        return daireBorc;
    }

    public void setDaireBorc(DaireBorc daireBorc) {
        this.daireBorc = daireBorc;
    }
}
