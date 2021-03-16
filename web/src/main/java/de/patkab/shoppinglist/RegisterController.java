package de.patkab.shoppinglist;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import de.patkab.shoppinglist.model.User;

@Named
@RequestScoped
public class RegisterController implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction ut;

    @Inject
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserByName(String name)
    {
        TypedQuery<User> query =
                em.createQuery(
                        "FROM " + User.class.getSimpleName() + " u WHERE u.name = :name",
                        User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public String persist() {
        try {
            ut.begin();
            FacesMessage msg = null;
            if (getUserByName(user.getName()) != null)
            {
                 msg = new FacesMessage("User already exists!");
                FacesContext
                        .getCurrentInstance()
                        .addMessage("name", msg);
            }
            else
            {
                em.persist(user);
                msg = new FacesMessage("Successfully registered!");
                FacesContext
                        .getCurrentInstance()
                        .addMessage("registerForm", msg);
            }
            ut.commit();
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext
                    .getCurrentInstance()
                    .addMessage(
                            "registerForm",
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    e.getMessage(),
                                    e.getCause().getMessage()));
        }
        return "/register.xhtml";
    }
}
