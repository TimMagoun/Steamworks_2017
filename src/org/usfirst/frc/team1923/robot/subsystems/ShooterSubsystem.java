package org.usfirst.frc.team1923.robot.subsystems;

import org.usfirst.frc.team1923.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {

    private final double P_CONSTANT = 0.4;
    private final double I_CONSTANT = 0.0001;
    private final double D_CONSTANT = 0;
    private final double F_CONSTANT = 0;
    private final boolean REVERSED = false;

    private CANTalon shooter;
    private CANTalon index;

    public ShooterSubsystem() {
        this.shooter = new CANTalon(RobotMap.SHOOTER_PORT);
        this.index = new CANTalon(RobotMap.INDEXER_PORT);
        configPID();
    }

    private void configPID() {
        this.shooter.setPID(this.P_CONSTANT, this.I_CONSTANT, this.D_CONSTANT, this.F_CONSTANT, 0, 400, 0);
        this.shooter.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        this.shooter.reverseSensor(REVERSED);
        this.shooter.configNominalOutputVoltage(0, 0);
        this.shooter.configPeakOutputVoltage(0, 12);
        // TODO: Is this the right direction?

        this.index.configNominalOutputVoltage(0, 0);
        this.index.configPeakOutputVoltage(-3, 3);
    }

    /**
     * Sets the shooter to a specific rpm using PID
     * 
     * @param rpm
     */
    public void set(int rpm) {
        this.shooter.set(rpm);
    }

    public int getRPM() {
        return (int) this.shooter.getSpeed();
    }

    public void index(double voltage) {
        this.index.set(voltage);
    }

    @Override
    public void initDefaultCommand() {
    }
}
