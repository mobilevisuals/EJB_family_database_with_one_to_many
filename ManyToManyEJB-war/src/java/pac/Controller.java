/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author eyvind
 */
@Named(value = "controller")
@SessionScoped
public class Controller implements Serializable {

    @EJB
    private PersonHandler2Local personHandler2;



    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void searchName()
    {
    personHandler2.fillDB();
    }
    
    /**
     * Creates a new instance of Controller
     */
    public Controller() {
    }
    
}
