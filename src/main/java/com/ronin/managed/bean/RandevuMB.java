package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.service.IOrtakService;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.KullaniciSecim;
import com.ronin.model.Randevu;
import com.ronin.model.constant.Durum;
import com.ronin.model.constant.OnayDurumu;
import com.ronin.model.constant.RandevuTipi;
import com.ronin.model.kriter.RandevuSorguKriteri;
import com.ronin.service.IRandevuService;
import com.ronin.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean(name = "randevuMB")
@ViewScoped
public class RandevuMB extends AbstractMB implements Serializable {

    public static Logger logger = Logger.getLogger(RandevuMB.class);
    public static String kullaniciSecimKey = "RANDEVU_SPOTLIGTH";

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{randevuService}")
    private IRandevuService randevuService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    private ScheduleModel lazyEventModel;

    private ScheduleEvent event = new DefaultScheduleEvent();

    //combolar
    private List<IAbstractEntity> randevuTipiList;

    //sorgu kriterleri
    private RandevuSorguKriteri sorguKriteri = new RandevuSorguKriteri();

    private Randevu selectedRandevu;
    private KullaniciSecim kullaniciSecim;
    private boolean tekrarGosterme;
    private boolean onaylandi;
    private boolean yeniKayit;
    private String onayRedButtonSecim;

    @PostConstruct
    public void init() {
        prepareCombos();
        lazyEventModel = new LazyScheduleModel() {

            @Override
            public void loadEvents(Date start, Date end) {
                List<Randevu> randevuList = randevuService.getRandevuList(sorguKriteri.getRandevuTipi() != null ? sorguKriteri.getRandevuTipi() : (RandevuTipi) randevuTipiList.get(0), start, end, sessionInfo);
                for (Randevu randevu : randevuList) {
                    DefaultScheduleEvent defaultScheduleEvent = new DefaultScheduleEvent(randevu.getAciklama(), randevu.getBaslangicZamani(), randevu.getBitisZamani(), randevu);
                    if (randevu.getKullanici().equals(sessionInfo.getKullanici())) {
                        if (randevu.isOnayBekliyor())
                            defaultScheduleEvent.setStyleClass("myEvent");
                        else if (randevu.isOnaylandi())
                            defaultScheduleEvent.setStyleClass("myEventOnayli");
                        else if (randevu.isOnaylanmadi())
                            defaultScheduleEvent.setStyleClass("myEventRed");
                    } else {
                        if (randevu.isOnayBekliyor())
                            defaultScheduleEvent.setStyleClass("myEventOthers");
                        else if (randevu.isOnaylandi())
                            defaultScheduleEvent.setStyleClass("myEventOthersOnayli");
                        else if (randevu.isOnaylanmadi())
                            continue; // kisiler sadece kendi reddelien kayitlarini gorecekler
                    }
                    addEvent(defaultScheduleEvent);
                }
            }
        };
    }

    public void prepareCombos() {
        randevuTipiList = ortakService.getListByNamedQueryWithSirket("RandevuTipi.findAllWithSirket", sessionInfo);
        kullaniciSecim = ortakService.getKullaniciSecimByKey(sessionInfo.getKullanici().getId(), kullaniciSecimKey);
        tekrarGosterme = kullaniciSecim != null && kullaniciSecim.getValue().equals("1");
        yeniKayit = true;
    }


    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }


    public void onEventSelect(SelectEvent selectEvent) {
        if (!sessionInfo.getKullanici().equals(((Randevu) ((ScheduleEvent) selectEvent.getObject()).getData()).getKullanici()) && !sessionInfo.isYetkili("tenis_randevu_onayi")) {
            JsfUtil.addWarnMessage(message.getString("warning_yetki_yok"));
            event = new DefaultScheduleEvent();
            yeniKayit = true;
            return;
        }
        event = (ScheduleEvent) selectEvent.getObject();
        selectedRandevu = (Randevu) event.getData();
        onaylandi = selectedRandevu.isOnaylandi();
        if (selectedRandevu.isOnaylandi()) {
            onayRedButtonSecim = "Onay";
        } else if (selectedRandevu.isOnaylanmadi()) {
            onayRedButtonSecim = "Red";
        } else {
            onayRedButtonSecim = "";
        }
        yeniKayit = false;
    }

    public void onDateSelect(SelectEvent selectEvent) {
        selectedRandevu = null;
        event = new DefaultScheduleEvent(sessionInfo.getKullanici().getAdSoyad(), (Date) selectEvent.getObject(), DateUtils.addHours((Date) selectEvent.getObject(), 1));
        yeniKayit = true;
        onaylandi = false;
        onayRedButtonSecim = "";
    }

    public void addEvent() {
        if (!verify()) {
            return;
        }
        if (event.getId() == null) {
            Randevu randevu = new Randevu();
            randevu.setAciklama(event.getTitle());
            randevu.setBaslangicZamani(event.getStartDate());
            randevu.setBitisZamani(event.getEndDate());
            randevu.setKullanici(sessionInfo.getKullanici());
            randevu.setRandevuTipi(sorguKriteri.getRandevuTipi());
            randevu.setDurum(Durum.getAktifObject());
            if (onaylandi) {
                randevu.setOnayDurumu(OnayDurumu.getOnaylandiObject());
            } else {
                randevu.setOnayDurumu(OnayDurumu.getOnayBekliyorObject());
            }
            randevu.setSirket(sessionInfo.getSirket());
            ortakService.save(randevu);
        } else {
            Randevu randevu = (Randevu) event.getData();
            randevu.setAciklama(event.getTitle());
            randevu.setBaslangicZamani(event.getStartDate());
            randevu.setBitisZamani(event.getEndDate());
            if (onaylandi) {
                randevu.setOnayDurumu(OnayDurumu.getOnaylandiObject());
                randevu.setOnaylayanKullaniciId(sessionInfo.getKullanici().getId());
                randevu.setOnayZamani(DateUtils.getNow());
            } else {
                randevu.setOnayDurumu(OnayDurumu.getOnayBekliyorObject());
            }
            ortakService.update(randevu);
        }
    }

    public void randevuOnayi() {
        Randevu randevu = selectedRandevu;
        randevu.setOnayDurumu(onaylandi ? OnayDurumu.getOnaylandiObject() : OnayDurumu.getOnaylanmadiObject());
        randevu.setOnaylayanKullaniciId(sessionInfo.getKullanici().getId());
        randevu.setOnayZamani(DateUtils.getNow());
        ortakService.update(randevu);
    }

    public void randevuIptali() {
        Randevu randevu = selectedRandevu;
        randevu.setDurum(Durum.getPasifObject());
        randevu.setIptalEdenKullaniciId(sessionInfo.getKullanici().getId());
        randevu.setIptalZamani(DateUtils.getNow());
        ortakService.update(randevu);
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }

    public List<IAbstractEntity> getRandevuTipiList() {
        return randevuTipiList;
    }

    public void setRandevuTipiList(List<IAbstractEntity> randevuTipiList) {
        this.randevuTipiList = randevuTipiList;
    }

    public RandevuSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(RandevuSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public IRandevuService getRandevuService() {
        return randevuService;
    }

    public void setRandevuService(IRandevuService randevuService) {
        this.randevuService = randevuService;
    }

    public void resetEventDetails() {
        event = null;
    }

    public ResourceBundle getMessage() {
        return message;
    }

    public void setMessage(ResourceBundle message) {
        this.message = message;
    }

    public boolean isTekrarGosterme() {
        return tekrarGosterme;
    }

    public void setTekrarGosterme(boolean tekrarGosterme) {
        this.tekrarGosterme = tekrarGosterme;
    }

    public boolean isOnaylandi() {
        return onaylandi;
    }

    public void setOnaylandi(boolean onaylandi) {
        this.onaylandi = onaylandi;
    }

    public Randevu getSelectedRandevu() {
        return selectedRandevu;
    }

    public void setSelectedRandevu(Randevu selectedRandevu) {
        this.selectedRandevu = selectedRandevu;
    }

    public boolean isYeniKayit() {
        return yeniKayit;
    }

    public void setYeniKayit(boolean yeniKayit) {
        this.yeniKayit = yeniKayit;
    }

    public String getOnayRedButtonSecim() {
        return onayRedButtonSecim;
    }

    public void setOnayRedButtonSecim(String onayRedButtonSecim) {
        this.onayRedButtonSecim = onayRedButtonSecim;
        if (StringUtils.isNotEmpty(onayRedButtonSecim)) {
            onaylandi = onayRedButtonSecim.equals("Onay");
        }
    }

    public boolean verify() {
        if (event.getStartDate().after(event.getEndDate()) || event.getStartDate().equals(event.getEndDate())) {
            JsfUtil.addWarnMessage(message.getString("warning_tarih_kontrol"));
            return false;
        }

        Long aktifRandevuVarMý = randevuService.hasAktifRandevu(sorguKriteri.getRandevuTipi(), event.getStartDate(), event.getEndDate(), sessionInfo,selectedRandevu);
        if (aktifRandevuVarMý > 0) {
            JsfUtil.addWarnMessage(message.getString("warning_aktif_randevu_var"));
            return false;
        }
        return true;
    }

    public KullaniciSecim getKullaniciSecim() {
        return kullaniciSecim;
    }

    public void setKullaniciSecim(KullaniciSecim kullaniciSecim) {
        this.kullaniciSecim = kullaniciSecim;
    }

    public void spotLightSelectionSetter() {
        if(!tekrarGosterme){
            JsfUtil.addWarnMessage(message.getString("warning_secim_yapilmali"));
            return;
        }
        if (kullaniciSecim == null) {
            KullaniciSecim kullaniciSecim = new KullaniciSecim();
            kullaniciSecim.setKullaniciId(sessionInfo.getKullanici().getId());
            kullaniciSecim.setKey(kullaniciSecimKey);
            kullaniciSecim.setValue(tekrarGosterme ? "1" : "0");
            ortakService.save(kullaniciSecim);
            JsfUtil.addSuccessMessage(message.getString("success_kulanici_secim_basarili"));
        }
    }

}
