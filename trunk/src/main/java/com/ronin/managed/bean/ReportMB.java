package com.ronin.managed.bean;

import com.lowagie.text.*;
import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Kullanici;
import com.ronin.common.service.IOrtakService;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.kriter.RaporSorguKriteri;
import com.ronin.model.sorguSonucu.R101SorguSonucu;
import com.ronin.model.sorguSonucu.R201SorguSonucu;
import com.ronin.service.IRaporService;
import net.sf.jasperreports.engine.JRException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 14.04.2014
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean(name = "raporMB")
@ViewScoped
public class ReportMB extends AbstractReportMB{

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{raporService}")
    private IRaporService iRaporService;

    //sorgu kriterleri
    private RaporSorguKriteri sorguKriteri = new RaporSorguKriteri();

    //sorguSonuclari
    List<R101SorguSonucu> r101DataMoel;
    List<R201SorguSonucu> r201DataMoel;
    private List<IAbstractEntity> blokList;

    @PostConstruct
    public void init() {
        setUserRolInfos();
    }

    public void getR101ResultList() throws IOException, JRException {
        r101DataMoel = iRaporService.getR101ListCriteriaForPaging(sorguKriteri , sessionInfo);
        if (r101DataMoel == null || r101DataMoel.size() <= 0) {
            JsfUtil.addWarnMessage(message.getString("error.sonuc.yok"));
        } else {
            initReportObjects(r101DataMoel , "r101.jasper");
            handleRaporIslem();
        }
    }

    public void getR201ResultList() throws IOException, JRException {
        r201DataMoel = iRaporService.getR201ListCriteriaForPaging(sorguKriteri , sessionInfo);
        if (r201DataMoel == null || r201DataMoel.size() <= 0) {
            JsfUtil.addWarnMessage(message.getString("error.sonuc.yok"));
        }  else {
            initReportObjects(r201DataMoel, "r201.jasper");
            handleRaporIslem();
        }
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.setPageSize(PageSize.A4.rotate());
        

        pdf.open();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "park-oran-logo.png";
        try {
            pdf.add(Image.getInstance(logo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Kullanici> completePlayer(String query) {
        List<Kullanici> suggestions = ortakService.getKullaniciByName(query , sessionInfo);
        return suggestions;
    }

    public void setUserRolInfos() {
        if (!sessionInfo.isAdminMi()) {
            sorguKriteri.setKullanici(sessionInfo.getKullanici());
        }
        blokList = ortakService.getListByNamedQueryWithSirket("Blok.findAllWithSirket",sessionInfo);
    }

    public ResourceBundle getMessage() {
        return message;
    }

    public void setMessage(ResourceBundle message) {
        this.message = message;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public RaporSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(RaporSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }

    public IRaporService getiRaporService() {
        return iRaporService;
    }

    public void setiRaporService(IRaporService iRaporService) {
        this.iRaporService = iRaporService;
    }

    public List<R101SorguSonucu> getR101DataMoel() {
        return r101DataMoel;
    }

    public void setR101DataMoel(List<R101SorguSonucu> r101DataMoel) {
        this.r101DataMoel = r101DataMoel;
    }

    public List<R201SorguSonucu> getR201DataMoel() {
        return r201DataMoel;
    }

    public void setR201DataMoel(List<R201SorguSonucu> r201DataMoel) {
        this.r201DataMoel = r201DataMoel;
    }

    public List<IAbstractEntity> getBlokList() {
        return blokList;
    }

    public void setBlokList(List<IAbstractEntity> blokList) {
        this.blokList = blokList;
    }
}
