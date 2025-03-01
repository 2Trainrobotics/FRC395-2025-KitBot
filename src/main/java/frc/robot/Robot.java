// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.drive.RobotDriveBase.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  // private static final String kDefaultAuto = "Default";
  // private static final String kCustomAuto = "My Auto";
  // private String m_autoSelected;
  // private final SendableChooser<String> m_chooser = new SendableChooser<>();

  // Motor Controller ID map begins here
  private SparkMax driveLeftLeader     = new SparkMax(4, MotorType.kBrushed);
  private SparkMax driveLeftFollower   = new SparkMax(8, MotorType.kBrushed);
  private SparkMax driveRightLeader    = new SparkMax(2, MotorType.kBrushed);
  private SparkMax driveRightFollower  = new SparkMax(5, MotorType.kBrushed);

  private SparkMax intakeMotor         = new SparkMax(7, MotorType.kBrushed);

  // Set up the Xbox controller.
  private XboxController driverController   = new XboxController(0);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    // m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    // m_chooser.addOption("My Auto", kCustomAuto);
    // SmartDashboard.putData("Auto choices", m_chooser);
  }

  @Override
  public void robotInit() {
      // TODO Auto-generated method stub
      super.robotInit();

      // Configure motors to turn correct direction.
      SparkMaxConfig driveLeftLeaderConfig = new SparkMaxConfig();
      SparkMaxConfig driveLeftFollowerConfig = new SparkMaxConfig();
      SparkMaxConfig driveRightLeaderConfig = new SparkMaxConfig();
      SparkMaxConfig driveRightFollowerConfig = new SparkMaxConfig();

      driveLeftLeaderConfig
        .inverted(false)
        .idleMode(IdleMode.kBrake);
      driveLeftLeader.configure(driveLeftLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
      driveLeftFollowerConfig
        .inverted(false)
        .idleMode(IdleMode.kBrake)
        .follow(driveLeftLeader);
      driveLeftFollower.configure(driveLeftFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      driveRightLeaderConfig
        .inverted(true)
        .idleMode(IdleMode.kBrake);
      driveRightLeader.configure(driveRightLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
      driveRightFollowerConfig
        .inverted(true)
        .idleMode(IdleMode.kBrake)
        .follow(driveRightLeader);
      driveRightFollower.configure(driveRightFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  
      SparkMaxConfig intakeMotorConfig = new SparkMaxConfig();
      intakeMotor.configure(intakeMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    // m_autoSelected = m_chooser.getSelected();
    // // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    // System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // switch (m_autoSelected) {
    //   case kCustomAuto:
    //     // Put custom auto code here
    //     break;
    //   case kDefaultAuto:
    //   default:
    //     // Put default auto code here
    //     break;
    // }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    ////////////////////////////////////////////////////
    //   DRIVETRAIN
    ////////////////////////////////////////////////////
    double driveLeftPower = driverController.getLeftY();
    double driveRightPower = driverController.getRightY();

    if (Math.abs(driveLeftPower) < 0.1) {
      driveLeftPower = 0;
    }
    if (Math.abs(driveRightPower) < 0.1) {
      driveRightPower = 0;
    }
    driveLeftLeader.set(driveLeftPower);
    driveRightLeader.set(driveRightPower);

    ////////////////////////////////////////////////////
    //   INTAKE
    ////////////////////////////////////////////////////
    double intakePower = 0.0;
    if (driverController.getXButton()) {
      intakePower = 0.5;
    } else if (driverController.getYButton()) {
      intakePower = -0.5;
    }
    intakeMotor.set(intakePower);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
