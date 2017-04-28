package com.kinicky.finances;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Transaction {

    @Id 
    Long id;
    private String bank;
    private String account;
    private String code;
    @Index
    private Date date;
    private Integer lineNumber;
    @Index
    private String description;
    private String blank1;
    @Index
    private String withdrawal;
    @Index
    private String deposit;
    private String blank2;
    private String blank3;
    private String blank4;
    private String blank5;
    private String balance;
    @Index
    private String category;
    @Index
    private String yearMonth;

        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBlank1() {
        return blank1;
    }

    public void setBlank1(String blank1) {
        this.blank1 = blank1;
    }

    public String getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(String withdrawal) {
        this.withdrawal = withdrawal;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getBlank2() {
        return blank2;
    }

    public void setBlank2(String blank2) {
        this.blank2 = blank2;
    }

    public String getBlank3() {
        return blank3;
    }

    public void setBlank3(String blank3) {
        this.blank3 = blank3;
    }

    public String getBlank4() {
        return blank4;
    }

    public void setBlank4(String blank4) {
        this.blank4 = blank4;
    }

    public String getBlank5() {
        return blank5;
    }

    public void setBlank5(String blank5) {
        this.blank5 = blank5;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
}
