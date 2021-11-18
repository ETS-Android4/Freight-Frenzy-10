package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;

public interface Subsystem {
    void init(HardwareMap hardwareMap);

    void initNoReset(HardwareMap hardwareMap);

    void update();

    Object subsystemState();
}
