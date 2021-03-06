package io.nobt.core.domain.debt.combination;

import io.nobt.core.domain.debt.Debt;

import java.util.Collection;

public class NotCombinable implements CombinationResult {

    @Override
    public boolean hasChanges() {
        return false;
    }

    @Override
    public void applyTo(Collection<Debt> debts) {

    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass().equals(obj.getClass());
    }
}
