package de.patkab.shoppinglist.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ShoppingListEntry {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String name;

    public ShoppingListEntry() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "[" + getId() + "," + getName() + "]";
    }
}
