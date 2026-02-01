package com.theonova.gateways;

import java.util.List;

public interface ServiceManager<D, I> {
    D saveItem(final D request);
    D updateItem(final I id, final D request);
    D findById(final I id);
    List<D> findAll();
    void removeItem(final I id);
}