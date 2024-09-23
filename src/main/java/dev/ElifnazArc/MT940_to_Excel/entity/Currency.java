package dev.ElifnazArc.MT940_to_Excel.entity;

public enum Currency {
    USD("United States Dollar"),
    EUR("Euro"),
    GBP("British Pound Sterling"),
    JPY("Japanese Yen"),
    AUD("Australian Dollar"),
    CAD("Canadian Dollar"),
    CHF("Swiss Franc"),
    CNY("Chinese Yuan"),
    TRY("Turkish Lira"),
    INR("Indian Rupee"),
    RUB("Russian Ruble"),
    ZAR("South African Rand"),
    BRL("Brazilian Real"),
    MXN("Mexican Peso");

    private final String description;

    Currency(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name();
    }
}
