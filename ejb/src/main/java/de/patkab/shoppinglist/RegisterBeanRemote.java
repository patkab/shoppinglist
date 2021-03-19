package de.patkab.shoppinglist;

import javax.ejb.Remote;

@Remote
public interface RegisterBeanRemote {
    String persist(String username, String password);
}
