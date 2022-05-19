import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ThermostatTest {
    Thermostat testHeat;
    @BeforeEach
    void setup(){
        testHeat = new Thermostat();
        testHeat.setCurrentTemp(26);
        // b = true
        testHeat.setOverride(true);
        // c = true
        testHeat.setOverTemp(28);
        testHeat.setThresholdDiff(1);
        // d = true
        testHeat.setTimeSinceLastRun(10);
        testHeat.setMinLag(5);
        testHeat.setPeriod(Period.MORNING);
        testHeat.setDay(DayType.WEEKDAY);
    }
    @org.junit.jupiter.api.Test
    void test_1() {
        ProgrammedSettings FakeSt = mock(ProgrammedSettings.class);
        when(FakeSt.getSetting(any(Period.class), any(DayType.class))).thenReturn(26);
        assertEquals(true, testHeat.turnHeaterOn(FakeSt));
        assertEquals(true, testHeat.getHeaterOn());
        assertEquals(28-26, testHeat.getRunTime());
        // overTemp - Current temp
    }
}