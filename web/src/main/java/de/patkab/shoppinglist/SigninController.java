package de.patkab.shoppinglist;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.patkab.shoppinglist.model.User;

@Named
@SessionScoped
public class SigninController implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private User user;

    private String name;

    private String password;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String find() {
        try {
            TypedQuery<User> query = em.createQuery(
                    "FROM " + User.class.getSimpleName()
                            + " u WHERE u.name = :name"
                            + " AND u.password= :password",
                    User.class);
            query.setParameter("name", name);
            query.setParameter("password", password);
            List<User> users = query.getResultList();
            if(users.isEmpty()) {
                FacesMessage m = new FacesMessage(
                        "Signing in was not successful!",
                        "Sorry, try again!");
                FacesContext
                        .getCurrentInstance()
                        .addMessage("signinForm", m);
            } else {
                user = users.get(0);
                FacesMessage m = new FacesMessage(
                        "Succesfully signed in!",
                        "You signed in under id " +
                                user.getId());
                FacesContext
                        .getCurrentInstance()
                        .addMessage("signinForm", m);
            }
        } catch (Exception e) {
            FacesMessage fm = new FacesMessage(
                    FacesMessage.SEVERITY_WARN,
                    e.getMessage(),
                    e.getCause().getMessage());
            FacesContext
                    .getCurrentInstance()
                    .addMessage(
                            "signinForm",
                            fm);
        }
        return "/signin.jsf";
    }
}
