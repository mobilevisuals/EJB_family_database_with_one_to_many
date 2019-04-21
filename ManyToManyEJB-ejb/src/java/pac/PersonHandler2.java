/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author eyvind
 */
@Stateless
//det blir deploy-fel om vi inte implementerar local eller remote interface:
public class PersonHandler2 implements PersonHandler2Local {
//vi ska injicera EntityManager när vår app ligger på en app-server
    //det är bästa sättet att initiera den då
    @PersistenceContext(unitName = "ManyToManyEJB-ejbPU")
    private EntityManager em;
    
    @Override
    public void fillDB() {
        //containern hanterar sina egna transaktioner
        //vi har ju transaction-type="JTA" i vår persistence.xml
        //och detta är ju en EJB
        //så vi får inte själva hantera transaktioner:
 //¨ em.getTransaction().begin();  
        Query q=em.createQuery("select o from Family o");
        int size=q.getResultList().size();
        if(size<1)
        {
        
        Family f = new Family();
        f.setDescription("testers");
        List<PersonReal> members = new ArrayList();
        for (int i = 0; i < 40; i++) {
            PersonReal person = new PersonReal();
       
            person.setFname("Jim " + i);
            person.setLname("Jones " + i);
            person.setFamily(f);
            members.add(person);
        }
        f.setPersons(members);
       persist(f);
          //containern hanterar sina egna transaktioner, så
        //vi får inte själva hantera dem:
       //¨  em.getTransaction().commit();
        }
       // em.close();
    }

//automatgenererad metod

    public void persist(Object object) {
        em.persist(object);
    }

 
    
    
}
