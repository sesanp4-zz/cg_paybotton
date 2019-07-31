/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import com.card.operations.Card;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author centricgateway
 */
@Entity
@Table(name = "Transactions")
public class Transactions{
    
   // private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; 
    
    @Column(name="fullname")
    String fullname;
    
    @Column(name="mobile")
    String mobile;
    
    @Column(name="email")
    String email;
    
    @Column(name="mid")
    String mid;
    
    @Column(name="amount")
    String amount;
    
    @Column(name="reference",unique = true)
    String reference;
    
    @Transient
    Card card;
    
    @Column(name="type")
    String type;
    
    @Column(name="callbackurl")
    String callbackurl;
    
    @Column(name="redirecturl")
    String redirecturl;
    
    @Column(name="linkingref")
    String linkingref;
    
    @Column(name="grossAmount")
    String grossAmount;
    
    @Column(name="gatewayMessage")
    String gatewayMessage;
    
    @Column(name="gatewayCode")
    String gatewayCode;
    
    @Column(name="clientAppCode")
    String clientAppCode;
    
    @Column(name="country")
    String country;
    
    @Column(name="currency")
    String currency;
    
    @Column(name="datetime")
    String datetime;
    
    @Column(name="origin")
    String origin;
    
    @Column(name="description")
    String description;
    
    @Column(name="internalref")
    String internalref;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCallbackurl() {
        return callbackurl;
    }

    public void setCallbackurl(String callbackurl) {
        this.callbackurl = callbackurl;
    }

    public String getRedirecturl() {
        return redirecturl;
    }

    public void setRedirecturl(String redirecturl) {
        this.redirecturl = redirecturl;
    }

    public String getLinkingref() {
        return linkingref;
    }

    public void setLinkingref(String linkingref) {
        this.linkingref = linkingref;
    }

    public String getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(String grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getGatewayMessage() {
        return gatewayMessage;
    }

    public void setGatewayMessage(String gatewayMessage) {
        this.gatewayMessage = gatewayMessage;
    }

    public String getGatewayCode() {
        return gatewayCode;
    }

    public void setGatewayCode(String gatewayCode) {
        this.gatewayCode = gatewayCode;
    }

    public String getClientAppCode() {
        return clientAppCode;
    }

    public void setClientAppCode(String clientAppCode) {
        this.clientAppCode = clientAppCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInternalref() {
        return internalref;
    }

    public void setInternalref(String internalref) {
        this.internalref = internalref;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
    
    
    
    
    
    
    
}
