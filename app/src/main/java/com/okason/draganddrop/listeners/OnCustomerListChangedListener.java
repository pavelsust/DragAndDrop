package com.okason.draganddrop.listeners;

import com.okason.draganddrop.Customer;

import java.util.List;

/**
 * Created by Valentine on 9/5/2015.
 */
public interface OnCustomerListChangedListener {
    void onNoteListChanged(List<Customer> customers);
}
