package serverpackage;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server{

    final static int PORT = 9000;


    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        // Get the address that we are going to connect to.
        String ipMulticast = args[0];
        InetAddress addr = InetAddress.getByName(args[0]);
        List<Sensor> sensors=new ArrayList<>();


        // Open a new DatagramSocket, which will be used to send the data.
        try (DatagramSocket serverSocket = new DatagramSocket()) {
           while(true) {
                Sensor aux = new Sensor ();
                sensors.add(aux);
                String pressure =  "Pressure: " + Float.toString(aux.getPressure());
                String temperature = "Temperature: " +Float.toString(aux.getTemperature());
                String humidity = "Humidity: " + Float.toString(aux.getHumidity());

                // Create a packet that will contain the data
                // (in the form of bytes) and send it.
                DatagramPacket pressurePacket = new DatagramPacket(pressure.getBytes(),
                        pressure.getBytes().length, addr, 10000);
                serverSocket.send(pressurePacket);
                System.out.println("serverpackage.serverpackage sent packet with msg: " + pressure);


               DatagramPacket temperaturePacket = new DatagramPacket(temperature.getBytes(),
                       temperature.getBytes().length, addr, 10001);
               serverSocket.send(temperaturePacket);
               System.out.println("serverpackage.serverpackage sent packet with msg: " + temperature);


               DatagramPacket humidityPacket = new DatagramPacket(humidity.getBytes(),
                       humidity.getBytes().length, addr, 10002);
               serverSocket.send(humidityPacket);
               System.out.println("serverpackage.serverpackage sent packet with msg: " + humidity);

               Thread.sleep(500);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}