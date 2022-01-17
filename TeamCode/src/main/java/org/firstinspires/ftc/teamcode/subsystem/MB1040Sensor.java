package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MB1040Sensor {
    AnalogInput sensor;
    public MB1040Sensor(String name, HardwareMap hardwareMap) {
        sensor = hardwareMap.get(AnalogInput.class, name);
    }

    public double getDistance() {
        return sensor.getVoltage() / (3.3 / 1024.);
    }
}
