package serverpackage;

import java.util.List;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.lang.*;
import java.net.InetAddress;

public class ThreadServerMult implements Runnable{
    private InetAddress addr;
    private List<Sensor> sensors;

    public ThreadServerMult(InetAddress multiaddr,  List<Sensor> sensores){
        addr = multiaddr;
        sensors = sensores;
    }

    public void run () {
        try (DatagramSocket serverSocket = new DatagramSocket()){
            while (true) {
                Sensor aux = new Sensor();
                sensors.add(aux);
                String pressure = "Pressure: " + Float.toString(aux.getPressure());
                String temperature = "Temperature: " + Float.toString(aux.getTemperature());
                String humidity = "Humidity: " + Float.toString(aux.getHumidity());

                // Create a packet that will contain the data
                // (in the form of bytes) and send it.
                DatagramPacket pressurePacket = new DatagramPacket(pressure.getBytes(),
                        pressure.getBytes().length, addr, 10001);
                serverSocket.send(pressurePacket);
                System.out.println(pressure);


                DatagramPacket temperaturePacket = new DatagramPacket(temperature.getBytes(),
                        temperature.getBytes().length, addr, 10002);
                serverSocket.send(temperaturePacket);
                System.out.println(temperature);


                DatagramPacket humidityPacket = new DatagramPacket(humidity.getBytes(),
                        humidity.getBytes().length, addr, 10003);
                serverSocket.send(humidityPacket);
                System.out.println(humidity);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException exe){
                    exe.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
