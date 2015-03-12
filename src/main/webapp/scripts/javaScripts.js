/**
 * Created with IntelliJ IDEA.
 * User: ealtun
 * Date: 02.04.2014
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */

function handleDaireIslemCombo() {
    var val = $("select[name$='daireIslemler_input'] option:selected").val();
    if (val === 'arizaTalebiGiris') {
        talepEklePopup.show();
    }
    if (val === 'sikayetTalebiGiris') {
        sikayetTalepEklePopup.show();
    }
}


function closePopup(popupName, islemTamamlandiMi) {
    if (islemTamamlandiMi == true) {
        popupName.hide();
    }
}

function openPopup(popupName, islemTamamlandiMi) {
    if (islemTamamlandiMi == true) {
        if (popupName == 'itirazTalepEklePopup') {
            itirazTalepEklePopup.show();
        }
    }
    alert(islemTamamlandiMi);
    alert(popupName);
}
/*
$(document).ready(function(){
    $(".ui-panelmenu-content").css("display","block"); //shows the menuitems
    $(".ui-panelmenu-header").addClass("ui-state-active"); //sets the submenu header to active state
    $(".ui-icon-triangle-1-e").removeClass("ui-icon-triangle-1-e").addClass("ui-icon-triangle-1-s"); //sets the triangle icon to "expaned" state
});
*/
$(document).ready(function() {
    $('.ui-menuitem-link').each(function(){
        if(window.location.pathname.indexOf($(this).attr('href')) != -1) {
            $(this).css('background', ' #aeaeae');//or add class
            $(this).css('color', ' white');//or add class
        }
    });

})

function chartJS() {
    this.cfg.grid = {
        drawBorder: false,
        drawGridlines: false,
        background: '#ffffff',
        shadow: false
    };
    this.cfg.seriesDefaults = {
        renderer: $.jqplot.PieRenderer,
        shadow: false,
        rendererOptions: {
            startAngle: 180,
            sliceMargin: 3,
            showDataLabels: true
        }
    };
    this.cfg.legend = {
        show: true,
        rendererOptions: {
            numberColumns: 1
        },
        location: 'e'
    };
}