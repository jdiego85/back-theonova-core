package com.theonova.gateways;

import java.util.List;
import java.util.Optional;

public interface ServiceManager<D, I> {
    D saveItem(final D item);
    D updateItem(final I id, final D request);
    Optional<D> findById(final I id);
    List<D> findAll();
    void removeItem(final I id);
}