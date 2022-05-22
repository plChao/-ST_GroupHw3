import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    @Test
    void test_1() {
        ProgrammedSettings FakeSt = mock(ProgrammedSettings.class);
        when(FakeSt.getSetting(any(Period.class), any(DayType.class))).thenReturn(26);
        assertEquals(true, testHeat.turnHeaterOn(FakeSt));
        assertEquals(true, testHeat.getHeaterOn());
        assertEquals(28-26, testHeat.getRunTime());
        // overTemp - Current temp
    }
    @Test
    void test_2() {
        ProgrammedSettings FakeSt = mock(ProgrammedSettings.class);
        when(FakeSt.getSetting(any(Period.class), any(DayType.class))).thenReturn(26);
        // d = false
        testHeat.setTimeSinceLastRun(3);
        assertEquals(false, testHeat.turnHeaterOn(FakeSt));
        assertEquals(false, testHeat.getHeaterOn());
        // overTemp - Current temp
    }
    @Test
    void test_3() {
        ProgrammedSettings FakeSt = mock(ProgrammedSettings.class);
        when(FakeSt.getSetting(any(Period.class), any(DayType.class))).thenReturn(28);
        assertEquals(true, testHeat.turnHeaterOn(FakeSt));
        assertEquals(true, testHeat.getHeaterOn());
        assertEquals(28-26, testHeat.getRunTime());
        // overTemp - Current temp
    }
    @Test
    void test_4() {
        ProgrammedSettings FakeSt = mock(ProgrammedSettings.class);
        when(FakeSt.getSetting(any(Period.class), any(DayType.class))).thenReturn(26);
        // c = true
        testHeat.setOverTemp(26);
        // d = false
        testHeat.setTimeSinceLastRun(3);
        assertEquals(false, testHeat.turnHeaterOn(FakeSt));
        assertEquals(false, testHeat.getHeaterOn());
        // overTemp - Current temp
    }
    @org.junit.jupiter.api.Test
    void test_5(){
        ProgrammedSettings FakeSt = mock(ProgrammedSettings.class);
        when(FakeSt.getSetting(any(Period.class), any(DayType.class))).thenReturn(28); //a-dtemp
        testHeat.setOverTemp(26);
        assertEquals(true, testHeat.turnHeaterOn(FakeSt)); //p
        assertEquals(true, testHeat.getHeaterOn());
        assertEquals(26-26, testHeat.getRunTime());
    }
    @Test
    void test_6(){
        ProgrammedSettings FakeSt = mock(ProgrammedSettings.class);
        when(FakeSt.getSetting(any(Period.class), any(DayType.class))).thenReturn(26);
        testHeat.setOverTemp(26);
        assertEquals(false, testHeat.turnHeaterOn(FakeSt)); //p
        assertEquals(false, testHeat.getHeaterOn());
        assertEquals(26-26, testHeat.getRunTime());
    }
    @Test
    void test_7(){
        ProgrammedSettings FakeSt = mock(ProgrammedSettings.class);
        when(FakeSt.getSetting(any(Period.class), any(DayType.class))).thenReturn(26);
        testHeat.setOverride(false);
        assertEquals(false, testHeat.turnHeaterOn(FakeSt)); //p
        assertEquals(false, testHeat.getHeaterOn());
        assertEquals(26-26, testHeat.getRunTime());
    }
    @Test
    void test_8(){
        ProgrammedSettings FakeSt = mock(ProgrammedSettings.class);
        when(FakeSt.getSetting(any(Period.class), any(DayType.class))).thenReturn(28);
        assertEquals(true, testHeat.turnHeaterOn(FakeSt)); //p
        assertEquals(true, testHeat.getHeaterOn());
        assertEquals(28-26, testHeat.getRunTime());
    }
    @Test
    void test_9(){
        ProgrammedSettings FakeSt = mock(ProgrammedSettings.class);
        when(FakeSt.getSetting(any(Period.class), any(DayType.class))).thenReturn(28);
        testHeat.setTimeSinceLastRun(3);
        assertEquals(false, testHeat.turnHeaterOn(FakeSt)); //p
        assertEquals(false, testHeat.getHeaterOn());
        assertEquals(26-26, testHeat.getRunTime());
    }
}