/**
 * @author Copyright (c) 2008,2013, Oracle and/or its affiliates. All rights reserved.
 *  
 */
package lk.anan.ri.service;

import lk.anan.ri.entity.Account;

/**
 * EJB Business Interface
 */
public interface AccountManager {

  public void depositOnAccount(String name, float amount);
  
  public Account findAccount(String name);
  
}
