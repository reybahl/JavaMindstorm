import lejos.hardware.BrickFinder;
import lejos.hardware.LED;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class ColorDetector {

	public static void main(String[] args) {
		
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		TextLCD lcd = ev3.getTextLCD();
		
		// TODO Auto-generated method stub
		RegulatedMotor mARot = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor mB = new EV3LargeRegulatedMotor(MotorPort.B);
		RegulatedMotor mC = new EV3LargeRegulatedMotor(MotorPort.C);
		EV3ColorSensor colorS = new EV3ColorSensor(SensorPort.S1); 
		/*mB.setSpeed(900);
		mC.setSpeed(900);*/
		/*mC.forward();
		mB.forward();*/
		
		long startTime = System.currentTimeMillis();
		long duration = 0;
		long sixDuration = duration;

		int colorId = 0;
		int prevColorId = 0;
		int flip[] = {10, -10};

		int i = 1;
		boolean movForward = true;
		do {

			duration = System.currentTimeMillis() - startTime;
			
			if (duration > (sixDuration + 4000))
			{
				sixDuration = duration;
				movForward = (movForward == true ? false : true);
			}
			
			if(movForward == true)
			{
				mC.forward();
				mB.forward();
			}
			else 
			{
				mC.backward();
				mB.backward();
			}
			
			mARot.rotate(flip[i%2]);
			SensorMode color = colorS.getColorIDMode();
			float[] sample = new float[color.sampleSize()];
			color.fetchSample(sample, 0);
			prevColorId = colorId;
			colorId = (int)sample[0];
			if(prevColorId != colorId)
			{
				Sound.playTone(Sound.BUZZ, 350);
			}
			String colorName = "";
			switch(colorId){
				case Color.NONE: colorName = "NONE"; break;
				case Color.BLACK: colorName = "BLACK"; break;
				case Color.BLUE: colorName = "BLUE"; break;
				case Color.GREEN: colorName = "GREEN"; break;
				case Color.YELLOW: colorName = "YELLOW"; break;
				case Color.RED: colorName = "RED"; break;
				case Color.WHITE: colorName = "WHITE"; break;
				case Color.BROWN: colorName = "BROWN"; break;
			}
			lcd.drawString(colorId + " - " + colorName, 0, 0);
			i = (i%2 + 1);
		} while (duration < 20000);
		
		
	}

}
