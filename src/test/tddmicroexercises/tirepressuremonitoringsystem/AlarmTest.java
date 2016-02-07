package tddmicroexercises.tirepressuremonitoringsystem;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by ruffert on 12/10/15.
 */
public class AlarmTest {

    public static final boolean EXPECTED_ALARM_IS_ON = true;
    public static final boolean EXPECTED_ALARM_IS_OFF = false;

    private SafetyRange safetyRange;

    @Before
    public void setUp() throws Exception {
        this.safetyRange = new SafetyRange(17,21);
    }

    @Test
    public void testIsAlarmOnWhenPressureOk() throws Exception {
        Alarm alarm = new Alarm(sensorThatProbesPressure(20), safetyRange);
        alarm.check();
        assertEquals(EXPECTED_ALARM_IS_OFF, alarm.isAlarmOn());
    }
    @Test
    public void testIsAlarmOnWhenPressureTooLow() throws Exception {
        Alarm alarm = new Alarm(sensorThatProbesPressure(15), safetyRange);
        alarm.check();
        assertEquals(EXPECTED_ALARM_IS_ON, alarm.isAlarmOn());
    }
    @Test 
    public void testIsAlarmOnWhenPressureTooHigh() throws Exception {
        Alarm alarm = new Alarm(sensorThatProbesPressure(25), safetyRange);
        alarm.check();
        assertEquals(EXPECTED_ALARM_IS_ON, alarm.isAlarmOn());
    }

    @Test
    public void collaborateWithAnInjectedSensor() {
        Sensor sensor = mock(Sensor.class);
        Alarm alarm = new Alarm(sensor, safetyRange);
        alarm.check();
        verify(sensor).popNextPressurePsiValue();
    }

    protected Sensor sensorThatProbesPressure(double value) {
        Sensor sensor = mock(Sensor.class);
        doReturn(value).when(sensor).popNextPressurePsiValue();
        return sensor;
    }
}