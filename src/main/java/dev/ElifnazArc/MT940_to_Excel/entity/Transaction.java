package dev.ElifnazArc.MT940_to_Excel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "account_identification")
    private String accountIdentification;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "closing_available_balance")
    private String closingAvailableBalance;

    @Column(name = "closing_balance")
    private String closingBalance;

    @Column(name = "forward_available_balance")
    private String forwardAvailableBalance;

    @Column(name = "opening_balance")
    private String openingBalance;

    @Column(name = "statement_number")
    private String statementNumber;

    @Column(name = "transaction_amount")
    private String transactionAmount;

    @Column(name = "transaction_date")
    private String transactionDate;

    @Column(name = "transaction_details")
    private String transactionDetails;

    @Column(name = "transaction_reference_number", nullable = false)
    private String transactionReferenceNumber;

    @Column(name = "transaction_type")
    private String transactionType;

}