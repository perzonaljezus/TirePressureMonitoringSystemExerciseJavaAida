import org.junit.Test;
import tddmicroexercises.tirepressuremonitoringsystem.Alarm;
import tddmicroexercises.tirepressuremonitoringsystem.Sensor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AlarmShould {

    @Test
    public void be_on_when_pressure_value_is_too_low() {
        Alarm alarm = new Alarm(sensorThatProbes(5.0));

        alarm.check();

        assertThat(alarm.isAlarmOn(), is(true));
    }

    @Test
    public void be_on_when_pressure_value_is_too_high() {
        Alarm alarm = new Alarm(sensorThatProbes(25.0));

        alarm.check();

        assertThat(alarm.isAlarmOn(), is(true));
    }

    @Test
    public void be_off_when_pressure_value_is_within_safety_range() {
        Alarm alarm = new Alarm(sensorThatProbes(20.0));

        alarm.check();

        assertThat(alarm.isAlarmOn(), is(false));
    }

    @Test
    public void collaborate_with_an_injected_sensor() {
        Sensor sensor = mock(Sensor.class);
        Alarm alarm = new Alarm(sensor);

        alarm.check();

        verify(sensor).popNextPressurePsiValue();
    }

    protected Sensor sensorThatProbes(double value) {
        Sensor sensor = mock(Sensor.class);
        doReturn(value).when(sensor).popNextPressurePsiValue();
        return sensor;
    }

    private class FakeAlarm extends Alarm {
        private final double samplePressure;

        public FakeAlarm(double samplePressure) {
            super();
            this.samplePressure = samplePressure;
        }

        @Override
        protected double probeValue() {
            return samplePressure;
        }
    }
}
