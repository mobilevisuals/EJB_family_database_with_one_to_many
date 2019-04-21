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
/*insert code -- call EJB 
    */
    @EJB
    private PersonHandler2Local personHandler2;



    private String firstName;
    private String lastName;
    private String oldname,newname;
    private PersonReal user;
    private boolean deleted=false;
  private boolean updated=false;

    public String getOldname() {
        return oldname;
    }

    public void setOldname(String oldname) {
        this.oldname = oldname;
    }

    public String getNewname() {
        return newname;
    }

    public void setNewname(String newname) {
        this.newname = newname;
    }
  
  

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
  
  
    
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    
    
    public void remove()
    {
    deleted=personHandler2.deletePerson(firstName, lastName);
    }
    
    public void searchName()
    {
   user=personHandler2.findByfirstName(firstName);
    }

    public PersonHandler2Local getPersonHandler2() {
        return personHandler2;
    }

    public void setPersonHandler2(PersonHandler2Local personHandler2) {
        this.personHandler2 = personHandler2;
    }

    public PersonReal getUser() {
        return user;
    }

    public void setUser(PersonReal user) {
        this.user = user;
    }
    
    public void updateUser()
    {
      updated=personHandler2.changeName(newname, oldname);
    }
    
    /**
     * Creates a new instance of Controller
     */
    public Controller() {
    }
    
}
