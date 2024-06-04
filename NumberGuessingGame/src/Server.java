import java.net.*;
import java.util.Random;
import java.io.IOException;

public class Server {
    private static final java.text.DateFormat DF = java.text.DateFormat.getDateTimeInstance();

    private static String getTime() {
        return DF.format(new java.util.Date());
    }

    public static void main(String[] args) {
        System.out.println("Server Start ..");

        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;
        int guesses = 5;

        System.out.println("De ghicit: " + randomNumber);

        try {
            DatagramSocket ds = new DatagramSocket(55000);
            DatagramPacket dp = new DatagramPacket(new byte[120], 120);
            String resp;
            while (guesses > 0) {
                ds.receive(dp);
                String strPrimit = new String(dp.getData(), 0, dp.getLength()).trim();
                System.out.println("Am primit de la client " + strPrimit + " von Port " + dp.getPort());
                int playerGuess = Integer.parseInt(strPrimit);

                if (playerGuess == randomNumber) {
                    resp = "You guessed the number! Congratulations!!!";
                    System.out.println(resp);
                    dp.setData(resp.getBytes());
                    ds.send(dp);
                    break;
                } else {
                    guesses--;
                    if (guesses == 0) {
                        resp = "Game over. You lost! The number was: " + randomNumber;
                    } else {
                        resp = "Your answer is not correct. Try again. " + (randomNumber < 50 ? "Hint: The number is between 1 and 50" : "Hint: The number is greater than 50");
                    }
                }

                dp.setData(resp.getBytes());
                ds.send(dp);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
