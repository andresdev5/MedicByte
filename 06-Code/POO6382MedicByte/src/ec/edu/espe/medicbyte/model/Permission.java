/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.medicbyte.model;

/**
 *
 * @author Michael Cobacango -ESPE -DCCO
 */
public class Permission {
    private String name;
    private boolean allowed;

    public Permission(String name, boolean allowed) {
        this.name = name;
        this.allowed = allowed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
    
}
