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
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

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
        Query q = em.createQuery("select o from Family o");
        int size = q.getResultList().size();
        if (size < 1) {

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
            persist(f);//används för 
            //containern hanterar sina egna transaktioner, så
            //vi får inte själva hantera dem:
            //¨  em.getTransaction().commit();
        }
        // em.close();app-servern stänger resurserna, så vi ska inte stänga dem
    }

//automatgenererad metod
    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public PersonReal findByfirstName(String fname) {
        fillDB();
        PersonReal user = new PersonReal();
        try {
            Query q = em
                    .createQuery("SELECT p FROM PersonReal p WHERE p.fname =:fname");
            q.setParameter("fname", fname);
            user = (PersonReal) q.getSingleResult();

        } catch (NoResultException | NonUniqueResultException e) {
            e.printStackTrace();
        }
        return user;

    }

    @Override
    public boolean deletePerson(String fname, String lname) {
        boolean status = true;
        
        try {
            Query q = em
                    .createQuery("SELECT p FROM PersonReal p WHERE p.fname =:fname"
                            + " and p.lname =:lname");
            q.setParameter("fname", fname);
            q.setParameter("lname", lname);
            PersonReal user = (PersonReal) q.getSingleResult();
            em.remove(user);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            status = false;
        }
        catch (TransactionRequiredException e1) {
            e1.printStackTrace();
            status = false;
        }
        return status;
    }
    
    
    
//metoder för affärslogik ska också vara i EJB;
    @Override
    public double calculation1() {
        //ej implementerad än
        return 0.0;
    }

    @Override
    public double research1() {
         //ej implementerad än
        return 0.0;
    }

    //this is how to update with JPQL:
    @Override
    public boolean changeName(String fname,String newFirstName) {
        boolean status = true;
        
     PersonReal user = new PersonReal();
        try {
            Query q = em
                    .createQuery("SELECT p FROM PersonReal p WHERE p.fname =:fname");
            q.setParameter("fname", fname);
            user = (PersonReal) q.getSingleResult();
            user.setFname(newFirstName);
            em.merge(user);

        } catch (NoResultException | NonUniqueResultException e) {
            e.printStackTrace();
        }
     //bra att returnera status ifrån metoder istället för att bara göra dem till void
        return status;
    }


    
    
    
    

}
