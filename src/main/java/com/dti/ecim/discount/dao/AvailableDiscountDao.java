package com.dti.ecim.discount.dao;

public interface AvailableDiscountDao {
    Long getId();
    String getName();
    String getDescription();
    int getAmountFlat();
    int getAmountPercent();
}
