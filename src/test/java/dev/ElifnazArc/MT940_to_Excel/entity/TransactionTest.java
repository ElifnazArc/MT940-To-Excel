package dev.ElifnazArc.MT940_to_Excel.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


class TransactionTest {

    private Transaction transaction;
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        transaction = new Transaction();

        // Random Integer ID
        transaction.setId(random.nextInt(10000)); // 0 ile 9999 arasında rastgele bir tam sayı

        // Random UUID as String for account identification
        transaction.setAccountIdentification(UUID.randomUUID().toString());

        // Current date for actionDate and transactionDate
        transaction.setActionDate(LocalDate.now());
        transaction.setTransactionDate(LocalDate.now());

        // Random bank code
        transaction.setBankCode("BANK" + random.nextInt(1000));

        // Random values for balances (BigDecimal)
        transaction.setClosingAvailableBalance(BigDecimal.valueOf(Math.random() * 10000).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        transaction.setClosingBalance(BigDecimal.valueOf(Math.random() * 10000).setScale(2, BigDecimal.ROUND_HALF_UP));
        transaction.setForwardAvailableBalance(BigDecimal.valueOf(Math.random() * 10000).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        transaction.setOpeningBalance(BigDecimal.valueOf(Math.random() * 10000).setScale(2, BigDecimal.ROUND_HALF_UP));

        // Random statement number
        transaction.setStatementNumber("STATEMENT" + random.nextInt(10000)); // 0 ile 9999 arasında rastgele bir statement number

        // Random transaction amount
        transaction.setTransactionAmount(BigDecimal.valueOf(Math.random() * 10000).setScale(2, BigDecimal.ROUND_HALF_UP));

        // Random transaction details
        transaction.setTransactionDetails("Payment for services");

        // Random transaction reference number
        transaction.setTransactionReferenceNumber(UUID.randomUUID().toString());

        // Random transaction type (Character)
        transaction.setTransactionType(random.nextBoolean() ? 'D' : 'C'); // 'D' veya 'C' değerlerinden biri

        // Random sender
        transaction.setSender("User" + random.nextInt(100)); // "User" + 0 ile 99 arasında rastgele bir tam sayı

        // Random batch ID
        transaction.setBatchId("BATCH" + random.nextInt(10000));

        // Randomly select currencies
        Currency[] currencies = Currency.values();
        transaction.setClosingCurrency(currencies[random.nextInt(currencies.length)].toString()); // Random closing currency
        transaction.setOpeningCurrency(currencies[random.nextInt(currencies.length)].toString()); // Random opening currency
    }

    @Test
    void getId_ShouldReturnCorrectId_WhenIdIsSet() {
        assertNotNull(transaction.getId());
    }

    @Test
    void getAccountIdentification_ShouldReturnCorrectAccountIdentification_WhenAccountIsSet() {
        assertNotNull(transaction.getAccountIdentification());
    }

    @Test
    void getActionDate_ShouldReturnCorrectActionDate_WhenActionDateIsSet() {
        assertEquals(LocalDate.now(), transaction.getActionDate());
    }

    @Test
    void getBankCode_ShouldReturnCorrectBankCode_WhenBankCodeIsSet() {
        assertNotNull(transaction.getBankCode());
    }

    @Test
    void getClosingAvailableBalance_ShouldReturnCorrectClosingAvailableBalance_WhenClosingAvailableBalanceIsSet() {
        assertNotNull(transaction.getClosingAvailableBalance());
    }

    @Test
    void getClosingBalance_ShouldReturnCorrectClosingBalance_WhenClosingBalanceIsSet() {
        assertNotNull(transaction.getClosingBalance());
    }

    @Test
    void getForwardAvailableBalance_ShouldReturnCorrectForwardAvailableBalance_WhenForwardAvailableBalanceIsSet() {
        assertNotNull(transaction.getForwardAvailableBalance());
    }

    @Test
    void getOpeningBalance_ShouldReturnCorrectOpeningBalance_WhenOpeningBalanceIsSet() {
        assertNotNull(transaction.getOpeningBalance());
    }

    @Test
    void getStatementNumber_ShouldReturnCorrectStatementNumber_WhenStatementNumberIsSet() {
        assertNotNull(transaction.getStatementNumber());
    }

    @Test
    void getTransactionAmount_ShouldReturnCorrectTransactionAmount_WhenTransactionAmountIsSet() {
        assertNotNull(transaction.getTransactionAmount());
    }

    @Test
    void getTransactionDate_ShouldReturnCorrectTransactionDate_WhenTransactionDateIsSet() {
        assertEquals(LocalDate.now(), transaction.getTransactionDate());
    }

    @Test
    void getTransactionDetails_ShouldReturnCorrectTransactionDetails_WhenTransactionDetailsIsSet() {
        assertEquals("Payment for services", transaction.getTransactionDetails());
    }

    @Test
    void getTransactionReferenceNumber_ShouldReturnCorrectTransactionReferenceNumber_WhenTransactionReferenceNumberIsSet() {
        assertNotNull(transaction.getTransactionReferenceNumber());
    }

    @Test
    void getTransactionType_ShouldReturnCorrectTransactionType_WhenTransactionTypeIsSet() {
        assertTrue(transaction.getTransactionType() == 'D' || transaction.getTransactionType() == 'C');
    }

    @Test
    void getSender_ShouldReturnCorrectSender_WhenSenderIsSet() {
        assertNotNull(transaction.getSender());
    }

    @Test
    void getBatchId_ShouldReturnCorrectBatchId_WhenBatchIdIsSet() {
        assertNotNull(transaction.getBatchId());
    }

    @Test
    void getClosingCurrency_ShouldReturnCorrectClosingCurrency_WhenClosingCurrencyIsSet() {
        assertNotNull(transaction.getClosingCurrency());
    }

    @Test
    void getOpeningCurrency_ShouldReturnCorrectOpeningCurrency_WhenOpeningCurrencyIsSet() {
        assertNotNull(transaction.getOpeningCurrency());
    }

    @Test
    void setClosingBalance_ShouldUpdateClosingBalance_WhenNewClosingBalanceIsProvided() {
        BigDecimal newClosingBalance = BigDecimal.valueOf(Math.random() * 10000).setScale(2, BigDecimal.ROUND_HALF_UP);
        transaction.setClosingBalance(newClosingBalance);
        assertEquals(newClosingBalance, transaction.getClosingBalance());
    }

    @Test
    void setTransactionAmount_ShouldUpdateTransactionAmount_WhenNewTransactionAmountIsProvided() {
        BigDecimal newTransactionAmount = BigDecimal.valueOf(Math.random() * 10000).setScale(2, BigDecimal.ROUND_HALF_UP);
        transaction.setTransactionAmount(newTransactionAmount);
        assertEquals(newTransactionAmount, transaction.getTransactionAmount());
    }

}
