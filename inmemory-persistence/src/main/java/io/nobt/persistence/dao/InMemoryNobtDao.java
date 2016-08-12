package io.nobt.persistence.dao;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import io.nobt.core.UnknownNobtException;
import io.nobt.core.domain.*;
import io.nobt.persistence.NobtDao;

/**
 * @author Matthias
 */
public class InMemoryNobtDao implements NobtDao {

    private static final AtomicLong idGenerator = new AtomicLong(1);
    private static final Map<NobtId, Nobt> nobtDatabase = new HashMap<>();

    @Override
    public Nobt create(String nobtName, Set<Person> explicitParticipants) {

        Nobt nobt = new Nobt(new NobtId(idGenerator.getAndIncrement()), nobtName, explicitParticipants);
        nobtDatabase.put(nobt.getId(), nobt);
        return nobt;
    }

    @Override
    public Expense createExpense(NobtId nobtId, String name, String splitStrategy, Person debtee, Set<Share> shares) {
        final Nobt nobt = get(nobtId);

        final Expense expense = new Expense(name, splitStrategy, debtee);
        shares.forEach(expense::addShare);

        nobt.addExpense(expense);

        return expense;
    }

    @Override
    public Expense createExpense(NobtId nobtId, String name, BigDecimal amount, Person debtee, Set<Person> debtors) {

        Nobt nobt = get(nobtId);

        Expense expense = new Expense(name, "AUTO - EQUAL", debtee);

        if (debtors.isEmpty()) {
            throw new IllegalArgumentException("Cannot save expense with no debtors!");
        }

        final BigDecimal amountPerDebtor = amount.divide(BigDecimal.valueOf(debtors.size()));

        debtors.forEach(d -> expense.addShare( new Share(d, Amount.fromBigDecimal(amountPerDebtor)) ));

        nobt.addExpense(expense);

        return expense;
    }

    @Override
    public Nobt get(NobtId id) {
        return find(id).orElseThrow(() -> new UnknownNobtException(id));
    }

    @Override
    public Optional<Nobt> find(NobtId id) {
        return Optional.ofNullable(nobtDatabase.get(id));
    }
}
