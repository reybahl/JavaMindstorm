import java.util.*;
import java.lang.Object;

public class Typingspeedtest {
    public static void main(String [] args){

        String t= "You are typing this text to find out how fast you can type";
        Scanner scanner = new Scanner(System.in);
        int x = 0;
        int accuracy = 0;
        long startTime = System.nanoTime();

        System.out.println("Type the following text as fast as you can and press enter as fast as you can:");
        System.out.println(t);
        String s = scanner.nextLine();
        if (!s.equals(t)){
            while (x<t.length()){
                if (s.charAt(x) != (t.charAt(x))){
                    accuracy++;
                }
                x++;
            }
        }


        long endTime = System.nanoTime();
        long totaltime = endTime - startTime;

        totaltime /= 1E9;


            float length = t.length()/5.0F;
            double dif = length / totaltime;
            int differentspeed = (int)(dif * 60);

            if (differentspeed<200){
            System.out.println("Nice");
            System.out.println("Your typing speed is " + differentspeed + " words per minute with " + ((length- accuracy) / length) * 100 + "% accuracy");
            if (accuracy <3){
                System.out.println("You got three stars for accuracy! Great job!");
            }

            if (differentspeed > 70 && differentspeed<200){
                System.out.println("Wow...You're too good at typing...you're a professional typist!");
            }
            }

            if (differentspeed>200){
                System.out.println("Cheater!............");
            }


    }


}
