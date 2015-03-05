package com.ronin.managed.bean;

import com.ronin.commmon.beans.SessionInfo;
import com.ronin.commmon.beans.util.JsfUtil;
import com.ronin.common.model.Rol;
import com.ronin.common.model.RolYetki;
import com.ronin.common.model.Yetki;
import com.ronin.common.service.IOrtakService;
import com.ronin.common.service.IRolService;
import com.ronin.managed.bean.lazydatamodel.RolDataModel;
import com.ronin.model.Duyuru;
import com.ronin.model.Interfaces.IAbstractEntity;
import com.ronin.model.constant.Durum;
import com.ronin.model.kriter.RolSorguKriteri;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.*;

@ManagedBean(name = "rolController")
@ViewScoped
public class RolMB implements Serializable {

    public static Logger logger = Logger.getLogger(DaireMB.class);

    @ManagedProperty("#{rolService}")
    private IRolService rolService;

    @ManagedProperty("#{sessionInfo}")
    private SessionInfo sessionInfo;

    @ManagedProperty("#{ortakService}")
    private IOrtakService ortakService;

    @ManagedProperty("#{msg}")
    private ResourceBundle message;

    @ManagedProperty("#{lbl}")
    private ResourceBundle label;

    private RolSorguKriteri sorguKriteri = new RolSorguKriteri();

    private Rol selected;

    private RolDataModel dataModel;

    private TreeNode yetkiList;

    private TreeNode[] selectedyetkiList;

    private TreeNode currentRoot;

    private List<Yetki> allYetkiList;

    private HashMap<Long , TreeNode> currentNodeMap = new HashMap<>();

    private Rol yeniRol = new Rol();

    private List<IAbstractEntity> rolTipiList;

    @PostConstruct
    public void init() {
        rolTipiList = ortakService.getListByNamedQuery("EvetHayir.findAll");
        allYetkiList = rolService.getAllYetkiList();
    }

    public void updateYetkiListByRole() {
        List<Yetki> selectedYetkiList = new ArrayList<>();
        Collection<TreeNode> selectedyetkiListWithParent =  getSelectedLisyByParent();

        if (selectedyetkiListWithParent != null && selectedyetkiListWithParent.size() > 0) {
            for (TreeNode node : selectedyetkiListWithParent) {
                 selectedYetkiList.add(((Yetki) node.getData()));
            }
           rolService.updateRolYetki(selectedYetkiList , selected);
        }
        JsfUtil.addSuccessMessage(message.getString("yetki_guncelleme_basarili"));
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('yetkiGuncellemeIslemPopup').hide()");
    }

    public Collection<TreeNode> getSelectedLisyByParent(){
        Collection<TreeNode> selectedNodes2 = new ArrayList<>();
        for(TreeNode node : selectedyetkiList) {
            selectedNodes2.add(node);
            while (node.getParent()!=null){
                selectedNodes2.add(node.getParent());
                node = node.getParent();
            }
        }
       return  selectedNodes2;
    }

    public void getYetkiListByRole() {
        currentNodeMap.clear();
        Yetki rootYetki = getRootYetki();
        getYetkiList(rootYetki);
    }

    public void getYetkiList(Yetki yetki) {
        if (yetki.getBagliOlduguYetki() == null) {
            yetkiList = new CheckboxTreeNode(yetki, null);
            currentNodeMap.put(yetki.getId() , yetkiList);
            if (isYetkili(yetki.getLink())){
                yetkiList.setSelected(true);
                expand(yetkiList);
            }
            List<Yetki> bagliYetkiler = findSubYetkiList(yetki);
            for (Yetki y : bagliYetkiler) {
                TreeNode node0 = new CheckboxTreeNode(y, yetkiList);
                if (isYetkili(y.getLink())){
                    node0.setSelected(true);
                    expand(node0);
                }
                currentNodeMap.put(y.getId() , node0);
                getYetkiList(y);
            }
        } else {
            List<Yetki> bagliYetkiler = findSubYetkiList(yetki);
            for (Yetki y : bagliYetkiler) {
                TreeNode node1 = new CheckboxTreeNode(y, currentNodeMap.get(yetki.getId()));
                currentNodeMap.put(y.getId() , node1);
                if (isYetkili(y.getLink())){
                    node1.setSelected(true);
                    expand(node1);
                }
                getYetkiList(y);
            }
        }
    }

    List<Yetki> findSubYetkiList(Yetki yetki) {
        List<Yetki> selectedYetki = new ArrayList<>();
        for (Yetki y : allYetkiList) {
            if (y.getBagliOlduguYetki() != null && y.getBagliOlduguYetki().getId().equals(yetki.getId())) {
                selectedYetki.add(y);
            }
        }
        return selectedYetki;
    }

    private void expand(TreeNode treeNode) {
        if (treeNode.getParent() != null) {
            treeNode.getParent().setExpanded(true);
            expand(treeNode.getParent());
        }
    }

    public Yetki getRootYetki() {
        Yetki yetki = null;
        for (Yetki y : allYetkiList) {
            if (y.getBagliOlduguYetki() == null) {
                yetki = y;
                break;
            }
        }
        return yetki;
    }

    public List<Yetki> getSelectedYetkiList() {
        List<Yetki> yetkiList = new ArrayList<Yetki>();
        List<RolYetki> ry = selected.getRolYetkiList();
        for (RolYetki rolYetki : ry) {
            yetkiList.add(rolYetki.getYetki());
        }
        Set hashset = new HashSet(yetkiList);
        yetkiList = new ArrayList(hashset);
        return yetkiList;
    }

    public boolean isYetkili(String yetkiLink) {
        List<Yetki> yetkiList = getSelectedYetkiList();
        for (Yetki y : yetkiList) {
            if (y.getLink().equals(yetkiLink)) {
                return true;
            }
        }
        return false;
    }


    public void getRolListBySorguKriteri() {

        List<Rol> dataList = rolService.getListCriteriaForPaging(0, 100, sorguKriteri , sessionInfo);

        dataModel = new RolDataModel(dataList);
    }

    public void yeniRolEkleme() {
        try {
            yeniRol.setDurum(Durum.getAktifObject());
            yeniRol.setSirket(sessionInfo.getSirket());
            rolService.add(yeniRol);
            getRolListBySorguKriteri();
            JsfUtil.addSuccessMessage(message.getString("rol_ekleme_basarili"));
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('rolEklePopup').hide()");
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addFatalMessage(e.toString());
        }
    }

    public void update(Rol rol) {
        try {
            rolService.update(rol);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }

    }

    public void delete(Rol rol) {
        try {
            rolService.update(rol);
        } catch (Exception e) {
            logger.error(e.getStackTrace());
            JsfUtil.addSuccessMessage("Hata!");
        }

    }

    public Rol getSelected() {
        return selected;
    }

    public void setSelected(Rol selected) {
        this.selected = selected;
    }

    public RolSorguKriteri getSorguKriteri() {
        return sorguKriteri;
    }

    public void setSorguKriteri(RolSorguKriteri sorguKriteri) {
        this.sorguKriteri = sorguKriteri;
    }

    public IRolService getRolService() {
        return rolService;
    }

    public void setRolService(IRolService rolService) {
        this.rolService = rolService;
    }

    public RolDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(RolDataModel dataModel) {
        this.dataModel = dataModel;
    }

    public TreeNode getYetkiList() {
        return yetkiList;
    }

    public void setYetkiList(TreeNode yetkiList) {
        this.yetkiList = yetkiList;
    }

    public TreeNode[] getSelectedyetkiList() {
        return selectedyetkiList;
    }

    public void setSelectedyetkiList(TreeNode[] selectedyetkiList) {
        this.selectedyetkiList = selectedyetkiList;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public TreeNode getCurrentRoot() {
        return currentRoot;
    }

    public void setCurrentRoot(TreeNode currentRoot) {
        this.currentRoot = currentRoot;
    }

    public List<Yetki> getAllYetkiList() {
        return allYetkiList;
    }

    public void setAllYetkiList(List<Yetki> allYetkiList) {
        this.allYetkiList = allYetkiList;
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

    public List<IAbstractEntity> getRolTipiList() {
        return rolTipiList;
    }

    public void setRolTipiList(List<IAbstractEntity> rolTipiList) {
        this.rolTipiList = rolTipiList;
    }

    public HashMap<Long, TreeNode> getCurrentNodeMap() {
        return currentNodeMap;
    }

    public void setCurrentNodeMap(HashMap<Long, TreeNode> currentNodeMap) {
        this.currentNodeMap = currentNodeMap;
    }

    public IOrtakService getOrtakService() {
        return ortakService;
    }

    public void setOrtakService(IOrtakService ortakService) {
        this.ortakService = ortakService;
    }

    public Rol getYeniRol() {
        return yeniRol;
    }

    public void setYeniRol(Rol yeniRol) {
        this.yeniRol = yeniRol;
    }
}
