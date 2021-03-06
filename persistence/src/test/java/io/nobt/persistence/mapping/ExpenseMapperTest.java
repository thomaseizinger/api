package io.nobt.persistence.mapping;

import io.nobt.persistence.cashflow.expense.ExpenseEntity;
import io.nobt.persistence.cashflow.expense.ExpenseMapper;
import io.nobt.persistence.share.ShareEntity;
import io.nobt.persistence.share.ShareMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseMapperTest {

    private ExpenseMapper sut;

    @Mock
    private ShareMapper shareMapperMock;

    @Before
    public void setUp() throws Exception {
        sut = new ExpenseMapper(shareMapperMock);
    }

    @Test
    public void shouldUseShareMapperToMapShares() throws Exception {

        final ExpenseEntity expenseEntity = new ExpenseEntity();
        expenseEntity.setCurrency("EUR");

        final ShareEntity share = new ShareEntity();
        expenseEntity.setShares(Collections.singletonList(share));


        sut.mapToDomainModel(expenseEntity);


        verify(shareMapperMock).mapToDomainModel(share);
    }
}