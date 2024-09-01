package dev.ElifnazArc.MT940_to_Excel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "account_identification", nullable = false, length = 50)
    private String accountIdentification;

    @Column(name = "bank_code", nullable = false, length = 20)
    private String bankCode;

    @Column(name = "closing_available_balance")
    private String closingAvailableBalance;

    @Column(name = "closing_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal closingBalance;

    @Column(name = "forward_available_balance")
    private String forwardAvailableBalance;

    @Column(name = "opening_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal openingBalance;

    @Column(name = "statement_number", nullable = false, length = 10)
    private String statementNumber;

    @Column(name = "transaction_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal transactionAmount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "transaction_details")
    private String transactionDetails;

    @Column(name = "transaction_reference_number")
    private String transactionReferenceNumber;

    @Column(name = "transaction_type", nullable = false)
    private Character transactionType;

    @Column(name = "sender", nullable = false, length = 50)
    private String sender;

}