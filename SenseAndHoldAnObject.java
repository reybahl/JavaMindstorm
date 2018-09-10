import java.util.Timer;
import java.util.TimerTask;

import lejos.hardware.BrickFinder;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RegulatedMotor;

public class SenseAndHoldAnObject {

	public static void main(String[] args) {
		try {
			// TODO Auto-generated method stub
			EV3 ev3 = (EV3) BrickFinder.getLocal();
			TextLCD lcd = ev3.getTextLCD();

			int rotAngle = 720;
			RegulatedMotor mARot = new EV3LargeRegulatedMotor(MotorPort.A);
			RegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
			mB.setSpeed(400);

			RegulatedMotor mC = new EV3LargeRegulatedMotor(MotorPort.C);
			mC.setSpeed(400);
			EV3IRSensor irSensor = new EV3IRSensor(SensorPort.S4);
			ReminderBeep reminderBeep = new ReminderBeep(60);

			int index = 0;
			mARot.rotate(-rotAngle, true);
			
			while (true) {

				mB.forward();
				mC.forward();

				float[] distances = new float[irSensor.sampleSize()];

				irSensor.getDistanceMode().fetchSample(distances, 0);
				irSensor.fetchSample(distances, 0);
				if (distances[0] < 12) {
					ev3.getAudio().setVolume(100);
					ev3.getAudio().playTone(500, 100);

					mB.stop(true);
					mC.stop(true);

					Thread.sleep(1000);

					mARot.rotate(rotAngle, true);

					Thread.sleep(1000);

					mB.backward();
					mC.forward();

					Thread.sleep(1000);

					mB.stop(true);
					mC.stop(true);

					Thread.sleep(1000);
					mARot.rotate(-rotAngle, true);

					Thread.sleep(1000);

					mB.forward();
					mC.backward();
					Thread.sleep(900);

					mB.stop(true);
					mC.stop(true);
					
					if (index == 3) {
						System.exit(0);
					}
					index++;
				}

			}

		} catch (Exception e) {

		}
	}

}

class ReminderBeep {

	public ReminderBeep(int seconds) {
		Timer timer = new Timer();
		timer.schedule(new RemindTask(), seconds * 1000);

	}

	class RemindTask extends TimerTask {
		public void run() {
			System.out.format("Time's up!%n");
			// timer.cancel(); //Not necessary because
			// we call System.exit.
			System.exit(0); // Stops the AWT thread
			// (and everything else).
		}
	}
}
