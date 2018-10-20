package serverpackage;
import java.io.IOException;
import java.lang.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.DatagramSocket;

public class ThreadClient implements Runnable{
    private InetAddress ip;
    private int port;

    public ThreadClient(InetAddress ipMulticast, int selectedPort){
        ip = ipMulticast;
        port = selectedPort;
    }

    public void run () {
        // Create a buffer of bytes, which will be used to store
        // the incoming bytes containing the information from the server.
        // Since the message is small here, 256 bytes should be enough.
        byte[] buf = new byte[256];
        // Create a new Multicast socket (that will allow other sockets/programs
        // to join it as well.
        try (MulticastSocket clientSocket = new MulticastSocket(port)) {
            //Joint the Multicast group.
            clientSocket.joinGroup(ip);

            while (true) {
                // Receive the information and print it.
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                String msg = new String(buf, 0, buf.length);
                System.out.println("Socket received in port " + Integer.toString(port) + " is: " + msg);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
