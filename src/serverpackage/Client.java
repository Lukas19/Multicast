package serverpackage;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class Client {

    final static int PRESSUREPORT = 10001;
    final static int TEMPERATUREPORT = 10002;
    final static int HUMIDITYPORT = 10003;

    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static void main(String[] args) throws UnknownHostException {
        String ipMulticast = args[0];
        String measurementBinary = args[1];
        String previousMeasurements = args[2];

        // Get the address that we are going to connect to.
        InetAddress address = InetAddress.getByName(ipMulticast);

        //If this client is new and wants the previous measurements...
        if (previousMeasurements.charAt(0) == '1') {
            try {
                InetAddress lhost = InetAddress.getByName("localhost");
                String message = "I'm new!";
                DatagramSocket emiSocket = new DatagramSocket();
                byte[] buffer = message.getBytes();
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, lhost, 10004);
                emiSocket.send(datagram);
                buffer = new byte[5];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                emiSocket.receive(response);
                byte[] binary = measurementBinary.getBytes();
                DatagramPacket binaries = new DatagramPacket(binary, binary.length, lhost, 10004);
                emiSocket.send(binaries);
                int size = byteArrayToInt(response.getData());
                byte[] bufferValues;
                DatagramPacket valuesServer;
                for(int i = 0; i < 3; i++) {
                    if (i == 0 && measurementBinary.charAt(0) == '1') {
                        System.out.print("Temperature: ");
                    }
                    if (i == 1 && measurementBinary.charAt(1) == '1') {
                        System.out.print("Pressure: ");
                    }
                    if (i == 2 && measurementBinary.charAt(2) == '1') {
                        System.out.print("Humidity: ");
                    }
                    for (int j = 0; j < size; j++) {
                        if (!(measurementBinary.charAt(i) == '0')) {
                            bufferValues = new byte[8];
                            valuesServer = new DatagramPacket(bufferValues, bufferValues.length);
                            emiSocket.receive(valuesServer);
                            System.out.print(ByteBuffer.wrap(bufferValues).getFloat() + " ");
                        }
                    }
                    if (measurementBinary.charAt(i) == '0') {
                        System.out.print("\n");
                    } else {
                        System.out.print("\n\n");
                    }
                }
                emiSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        //Review measurement
        for (int i = 0; i < measurementBinary.length(); i++) {
            if (i == 0 && measurementBinary.charAt(i) == '1') {
                Runnable r1 = new ThreadClient(address, PRESSUREPORT);
                Thread t1 = new Thread(r1);
                t1.start();
            } else if (i == 1 && measurementBinary.charAt(i) == '1') {
                Runnable r2 = new ThreadClient(address, TEMPERATUREPORT);
                Thread t2 = new Thread(r2);
                t2.start();
            } else if (i == 2 && measurementBinary.charAt(i) == '1') {
                Runnable r3 = new ThreadClient(address, HUMIDITYPORT);
                Thread t3 = new Thread(r3);
                t3.start();
            }
        }
    }
}
