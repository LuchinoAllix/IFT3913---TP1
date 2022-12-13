package ua.karatnyk;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ua.karatnyk.impl.CurrencyConversion;
import ua.karatnyk.impl.ExpensesProgramAPI;
import ua.karatnyk.impl.JsonWorker;
import ua.karatnyk.impl.OfflineJsonWorker;

public class TestCurrencyConvertor {

    @Test
    public void testCheckDate() {
        boolean output = test.checkDate("2017-03-25");
        assertTrue(output);
    }

}