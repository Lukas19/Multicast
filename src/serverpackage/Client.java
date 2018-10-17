package serverpackage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Client {

    final static int PRESSUREPORT = 10001;
    final static int TEMPERATUREPORT = 10002;
    final static int HUMIDITYPORT = 10003;



    public static void main(String[] args) throws UnknownHostException {
        String ipMulticast = args[0];
        String measurementBinary = args[1];

        // Get the address that we are going to connect to.
        InetAddress address = InetAddress.getByName(ipMulticast);

        //Review measurement
        for (int i = 0; i < measurementBinary.length(); i++){
            if (i == 0 && measurementBinary.charAt(i) == '1'){
                Runnable r1 = new ThreadClient(address, PRESSUREPORT);
                Thread t1 = new Thread(r1);
                t1.start();
            }
            else if (i == 1 && measurementBinary.charAt(i) == '1'){
                Runnable r2 = new ThreadClient(address, TEMPERATUREPORT);
                Thread t2 = new Thread(r2);
                t2.start();
            }
            else if (i == 2 && measurementBinary.charAt(i) == '1'){
                Runnable r3 = new ThreadClient(address, HUMIDITYPORT);
                Thread t3 = new Thread(r3);
                t3.start();
            }
        }
    }
}
