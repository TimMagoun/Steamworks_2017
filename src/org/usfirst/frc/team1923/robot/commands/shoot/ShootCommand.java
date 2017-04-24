package org.usfirst.frc.team1923.robot.commands.shoot;

import org.usfirst.frc.team1923.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShootCommand extends Command {

    public ShootCommand() {
        requires(Robot.shooterSubSys);
        this.setTimeout(5);
    }

    @Override
    protected void initialize() {
        Robot.shooterSubSys.set(12000);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        SmartDashboard.putNumber("Shooter RPM", Robot.shooterSubSys.getRPM());
        if (Robot.shooterSubSys.getRPM() > 10000) {
            SmartDashboard.putBoolean("Indexing", true);
            Robot.shooterSubSys.index(3); // Move at full speed forward
        } else {
            Robot.shooterSubSys.index(0);
            SmartDashboard.putBoolean("Indexing", true);
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.shooterSubSys.set(0);
        Robot.shooterSubSys.index(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
