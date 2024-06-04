import java.net.*;
import java.util.Scanner;
import java.io.IOException;

public class Client {
    public static void main(String[] args) {
        try {
            InetAddress iaddr = InetAddress.getByName("192.168.56.1"); // adresa IP a serverului
            DatagramSocket ds = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter your guess (1-100): ");
                String s = scanner.nextLine();

                DatagramPacket dp = new DatagramPacket(s.getBytes(), s.length(), iaddr, 55000);
                ds.send(dp);

                dp = new DatagramPacket(new byte[120], 120);
                ds.receive(dp);
                String response = new String(dp.getData(), 0, dp.getLength()).trim();
                System.out.println(response);

                if (response.startsWith("You guessed the number!") || response.startsWith("Game over")) {
                    break;
                }
            }
            ds.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
