
package org.usfirst.frc.team1923.robot;

import org.usfirst.frc.team1923.robot.commands.auton.DoNothingAuton;
import org.usfirst.frc.team1923.robot.commands.auton.VisionAutonCenter;
import org.usfirst.frc.team1923.robot.commands.auton.VisionAutonLeft;
import org.usfirst.frc.team1923.robot.commands.auton.VisionAutonRight;
import org.usfirst.frc.team1923.robot.commands.drive.DriveTimeCommand;
import org.usfirst.frc.team1923.robot.subsystems.ClimberSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.DebugSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.GearSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.ShooterSubsystem;
import org.usfirst.frc.team1923.robot.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    public static ClimberSubsystem climbSubSys;
    public static DebugSubsystem debugSubSys;
    public static DrivetrainSubsystem driveSubSys;
    public static GearSubsystem gearSubSys;
    public static VisionSubsystem visionSubSys;
    public static ShooterSubsystem shooterSubSys;
    public static OI oi;

    private Command autonomousCommand;
    private SendableChooser<Command> autonChooser = new SendableChooser<Command>();

    /**
     * Called once when the robot first starts up.
     */
    @Override
    public void robotInit() {
        gearSubSys = new GearSubsystem();
        driveSubSys = new DrivetrainSubsystem();
        climbSubSys = new ClimberSubsystem();
        visionSubSys = new VisionSubsystem();
        debugSubSys = new DebugSubsystem();
        shooterSubSys = new ShooterSubsystem();

        oi = new OI();

        this.autonChooser.addDefault("Do Nothing Auto", new DoNothingAuton());
        // this.autonChooser.addObject("Log", new LogDataCommand("LOGGED"));
        this.autonChooser.addObject("Drive 2 seconds", new DriveTimeCommand(1.0, 2, true));
        this.autonChooser.addObject("Vision Auton Right", new VisionAutonRight());
        this.autonChooser.addObject("Vision Auton Center", new VisionAutonCenter());
        this.autonChooser.addObject("Vision Auton Left", new VisionAutonLeft());
        // this.autonChooser.addObject("Drive 100 inches", new
        // DriveDistanceCommand(100));

        // SmartDashboard.putData("Motion Magic SRX", new
        // DriveMotionMagicCommand(100));

        SmartDashboard.putData("Auto Mode", this.autonChooser);
    }

    /**
     * Called once when the robot is disabled.
     */
    @Override
    public void disabledInit() {
        debugSubSys.stopLog();
    }

    /**
     * Called every 20ms when the robot is disabled.
     */
    @Override
    public void disabledPeriodic() {

    }

    /**
     * Called once at the start of autonomous mode.
     */
    @Override
    public void autonomousInit() {
        visionSubSys.refresh();
        this.autonomousCommand = this.autonChooser.getSelected();

        if (this.autonomousCommand != null) {
            this.autonomousCommand.start();
        }
    }

    /**
     * Called every 20ms during autonomous mode.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * Called once at the start of tele-op mode.
     */
    @Override
    public void teleopInit() {
        if (this.autonomousCommand != null) {
            this.autonomousCommand.cancel();
        }
    }

    /**
     * Called every 20ms during tele-op mode.
     */
    @Override
    public void teleopPeriodic() {
        Robot.shooterSubSys.set((int) (12000 * Robot.oi.op.getY()));
        System.out.println(Robot.shooterSubSys.getRPM());
        Scheduler.getInstance().run();
    }

    /**
     * Called every 20ms during testing mode.
     */
    @Override
    public void testPeriodic() {
        LiveWindow.run();
    }

}
