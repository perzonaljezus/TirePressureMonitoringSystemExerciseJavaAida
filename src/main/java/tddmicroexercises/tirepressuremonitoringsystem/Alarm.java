package tddmicroexercises.tirepressuremonitoringsystem;


/**
 * Created by ruffert on 12/10/15.
 */
public class Alarm {
    private Sensor sensor;

    private boolean alarmOn;

    private SafetyRange safetyRange;

    public void check() {
        double psiPressureValue = probe();

        if (isNotWithinSafetyRange(psiPressureValue)) {
            alarmOn = true;
        }
    }

    private boolean isNotWithinSafetyRange(double psiPressureValue) {
        return safetyRange.isNotWithin(psiPressureValue);
    }

    protected double probe() {
        return sensor.popNextPressurePsiValue();
    }

    public boolean isAlarmOn() {
        return alarmOn;
    }

    public Alarm(Sensor sensor, SafetyRange safetyRange) {
        this.sensor = sensor;
        this.alarmOn = false;
        this.safetyRange = safetyRange;
    }

}
