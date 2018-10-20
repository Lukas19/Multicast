package serverpackage;

import java.util.ArrayList;
import java.util.List;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server{

    public static void main(String[] args) throws UnknownHostException {
        // Get the address that we are going to connect to.
        String ipMulticast = args[0];
        InetAddress addr = InetAddress.getByName(args[0]);
        List<Sensor> sensors= new ArrayList<>();

        // Open a new DatagramSocket, which will be used to send the data.
        Runnable hist = new ThreadServerHist(sensors);
        Runnable multi = new ThreadServerMult(addr, sensors);
        Thread t1 = new Thread(hist);
        Thread t2 = new Thread(multi);
        t1.start();
        t2.start();
    }
}