/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author centricgateway
 */
@Entity
@Table(name = "Transaction")
public class Transaction implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; 

    @Column(name="ref")
    String ref;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userinfo_fk")
    UserInfo userinfo;
    
    @Column(name="settlementCode")
    String settlementCode;
    
    @Column(name="settlementMessage")
    String settlementMessage;
    
    @Column(name="time_to_settlement")
    String time_to_settlement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }

    public String getSettlementCode() {
        return settlementCode;
    }

    public void setSettlementCode(String settlementCode) {
        this.settlementCode = settlementCode;
    }

    public String getSettlementMessage() {
        return settlementMessage;
    }

    public void setSettlementMessage(String settlementMessage) {
        this.settlementMessage = settlementMessage;
    }

    public String getTime_to_settlement() {
        return time_to_settlement;
    }

    public void setTime_to_settlement(String time_to_settlement) {
        this.time_to_settlement = time_to_settlement;
    }

    
    
    
}
