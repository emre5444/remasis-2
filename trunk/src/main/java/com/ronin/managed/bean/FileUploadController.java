package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.service.IOrtakService;
import com.ronin.model.constant.Belge;
import com.ronin.service.IFileUploadService;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Hibernate;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Iterator;
import java.util.ResourceBundle;


@ManagedBean(name = "fileUploadMB")
@ViewScoped
public class FileUploadController implements Serializable {

    public static Logger logger = Logger.getLogger(FileUploadController.class);

    @ManagedProperty("#{fileUploadService}")
    private IFileUploadService fileUploadService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    private Belge yeniBelge = new Belge();

    @PostConstruct
    public void init() {

    }

    public void belgeUpload(FileUploadEvent event) {
        if (event.getFile().equals(null)) {
            JsfUtil.addSuccessMessage("file is null");
        }
        try {
            UploadedFile uploadedFile = event.getFile();
            fileUploadService.belgeEkle(sessionInfo ,uploadedFile , yeniBelge);

        } catch (Exception e) {
            JsfUtil.addSuccessMessage("error reading file " + e);
        }

    }


    public void handleFileUpload(FileUploadEvent event) {
        if (event.getFile().equals(null)) {
            JsfUtil.addSuccessMessage("file is null");
        }
        InputStream file = null;
        try {
            file = event.getFile().getInputstream();
        } catch (IOException e) {
            JsfUtil.addSuccessMessage("error reading file " + e);
        }


        try {

            //Create Workbook instance holding reference to .xlsx file
            // XSSFWorkbook workbook = new XSSFWorkbook(file);

            org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);

            //Get first/desired sheet from the workbook
            // XSSFSheet sheet = workbook.getSheetAt(0);
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                    }
                }
                System.out.println("");
            }
            file.close();
        } catch (Exception e) {
            System.out.println(e);
            JsfUtil.addSuccessMessage("excel den okuren hata meydana geldi " + e);
        }


    }


    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        FileUploadController.logger = logger;
    }

    public IFileUploadService getFileUploadService() {
        return fileUploadService;
    }

    public void setFileUploadService(IFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    public ResourceBundle getMessage() {
        return message;
    }

    public void setMessage(ResourceBundle message) {
        this.message = message;
    }

    public ResourceBundle getLabel() {
        return label;
    }

    public void setLabel(ResourceBundle label) {
        this.label = label;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public Belge getYeniBelge() {
        return yeniBelge;
    }

    public void setYeniBelge(Belge yeniBelge) {
        this.yeniBelge = yeniBelge;
    }
}
