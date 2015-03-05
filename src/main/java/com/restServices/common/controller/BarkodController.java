package com.restServices.common.controller;

import com.ronin.model.Envanter;
import com.ronin.service.IEnvanterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by fcabi on 06.01.2015.
 */

@RestController
@RequestMapping("/barkod")
public class BarkodController {

    @Autowired
    private IEnvanterService envanterService;

    @RequestMapping(value = "/{barkodNo}", method = RequestMethod.GET)
    public ModelAndView getEnvanterByBarkodNo(@PathVariable String barkodNo) {
        Envanter envanter = envanterService.getEnvanterByBarkodNo(barkodNo);

        ModelAndView model = new ModelAndView("envanterSorgula");
        model.addObject("envanter", envanter);

        return model;
    }

}
