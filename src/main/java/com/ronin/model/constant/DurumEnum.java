/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ronin.model.constant;

/**
 *
 * @author msevim
 */
public enum DurumEnum {
    ////Buradaki siralama veri tabanindaki siralamadir. // buna ordinal denir
    
    Aktif(1L,"durum.aktif"),//1
    Pasif(2L,"durum.pasif"),//2
    Silinmis(3L,"durum.silinmis");//3
    
    private Long id;
    private String label;

    private DurumEnum(Long id,String label){
        this.id=id;
        this.label=label;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getLabel() {
        return label;
    }
   
    
}
