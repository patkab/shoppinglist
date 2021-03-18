package de.patkab.shoppinglist;

import java.io.Serializable;
import java.util.List;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.patkab.shoppinglist.model.User;

@Named
@RequestScoped
public class RegisterController implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(RegisterController.class);

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
        List<User> users = query.getResultList();
        if (users.isEmpty())
        {
            return null;
        }
        else
        {
            return users.get(1);
        }
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
            //TODO check if logging is displayed
            logger.error(e.getMessage());
            FacesContext
                    .getCurrentInstance()
                    .addMessage(
                            "registerForm",
                            new FacesMessage(
                                    FacesMessage.SEVERITY_ERROR,
                                    e.getMessage(),
                                    e.getCause().getMessage()));
        }
        return "register";
    }
}
