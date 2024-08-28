package dev.ElifnazArc.MT940_to_Excel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    private String transactionReferenceNumber;
    private String bankCode;
    private String accountIdentification;
    private String statementNumber;
    private String openingBalance;
    private String transactionDate;
    private String transactionAmount;
    private String transactionType;
    private String transactionDetails;
    private String closingBalance;
    private String closingAvailableBalance;
    private String forwardAvailableBalance;

    // Getters and Setters
    public String getTransactionReferenceNumber() {
        return transactionReferenceNumber;
    }

    public void setTransactionReferenceNumber(String transactionReferenceNumber) {
        this.transactionReferenceNumber = transactionReferenceNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAccountIdentification() {
        return accountIdentification;
    }

    public void setAccountIdentification(String accountIdentification) {
        this.accountIdentification = accountIdentification;
    }

    public String getStatementNumber() {
        return statementNumber;
    }

    public void setStatementNumber(String statementNumber) {
        this.statementNumber = statementNumber;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public String getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(String closingBalance) {
        this.closingBalance = closingBalance;
    }

    public String getClosingAvailableBalance() {
        return closingAvailableBalance;
    }

    public void setClosingAvailableBalance(String closingAvailableBalance) {
        this.closingAvailableBalance = closingAvailableBalance;
    }

    public String getForwardAvailableBalance() {
        return forwardAvailableBalance;
    }

    public void setForwardAvailableBalance(String forwardAvailableBalance) {
        this.forwardAvailableBalance = forwardAvailableBalance;
    }
}
