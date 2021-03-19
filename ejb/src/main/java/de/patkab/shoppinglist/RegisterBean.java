package de.patkab.shoppinglist;

import javax.ejb.Stateless;

@Stateless
public class RegisterBean
        implements RegisterBeanRemote, RegisterBeanLocal {

    public RegisterBean() {}

    @Override
    public String persist(String username, String password) {
        return username + ":" + password;
    }
}
