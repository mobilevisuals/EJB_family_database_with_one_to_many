/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac;

import javax.ejb.Local;

/**
 *
 * @author eyvind
 */
@Local
public interface PersonHandler2Local {

    void fillDB();
    
}
