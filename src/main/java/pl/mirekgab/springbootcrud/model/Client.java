/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mirekgab.springbootcrud.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author mirek
 */
@Entity
public class Client implements Serializable  {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long clientId;
    
    
    private String clientName;

    public Client() {
    }

    public Client(String clientName) {
        this.clientName = clientName;
    }

    
    
    public Client(Long clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
    }
    
    

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "Client{" + "clientId=" + clientId + ", clientName=" + clientName + '}';
    }
    
    
}
