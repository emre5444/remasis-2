package com.ronin.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class messageUtils {

    static public void showSuccessMessage(String messageText) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageText, "Ýþlem Baþarýlý");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
