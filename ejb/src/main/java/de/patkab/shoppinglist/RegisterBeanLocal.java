package de.patkab.shoppinglist;

import javax.ejb.Local;

@Local
public interface RegisterBeanLocal {
    String persist(String username, String password);
}
