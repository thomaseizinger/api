package io.nobt.persistence.entity;

import io.nobt.util.Sets;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "nobts")
@Entity
public class NobtEntity {

    @Id
    @SequenceGenerator(name = "nobts_seq", sequenceName = "nobts_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Type(type = "pg-uuid")
    @Column(name = "uuid", nullable = false, unique = true, length = 50)
    private UUID uuid;

    @Column(name = "nobtName", nullable = false, length = 50)
    private String name;

    @Column(name = "explicitParticipants")
    private String explicitParticipants;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nobt", cascade = CascadeType.ALL)
    private Set<ExpenseEntity> expenses = new HashSet<>();

    public NobtEntity(String name, UUID uuid, Set<String> explicitParticipants) {
        this.name = name;
        this.uuid = uuid;
        this.explicitParticipants = String.join(";", explicitParticipants);
    }

    public NobtEntity() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ExpenseEntity> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<ExpenseEntity> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(ExpenseEntity expense) {
        if (expenses == null) {
            expenses = new HashSet<>();
        }
        expenses.add(expense);
        expense.setNobt(this);
    }

    public Set<String> getExplicitParticipants() {
        return explicitParticipants != null ? Sets.newHashSet(explicitParticipants.split(";")) : Collections.emptySet();
    }

    public long getId() {
        return id;
    }
}
