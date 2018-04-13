/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Jakob
 */
@ManagedBean
@SessionScoped
public class NavigationBean {
    
    @ManagedProperty(value="#{userBean}")
    private UserBean userBean;
    
    
    public void setUserBean(UserBean userBean){
        this.userBean = userBean;
    }
    
    public String goToLogin(){
        userBean.setStatusMessage("");
        return "login.xhtml";
    }
    
    public String goToCreateAccount(){
        userBean.setStatusMessage("");
        userBean.setUsername("");
        return "createaccount.xhtml";
    }
    
    public String goToInbox(){
        return "inbox?faces-redirect=true";
    }
}