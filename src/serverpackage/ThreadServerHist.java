package serverpackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.List;


public class ThreadServerHist implements Runnable{
    private List<Sensor> sensorsValues;

    public ThreadServerHist(List<Sensor> sensors){
        sensorsValues = sensors;
    }

    public void run (){

        try (DatagramSocket serverSocket = new DatagramSocket(10004)){
            while(true) {
                byte [] buff = new byte[10];
                DatagramPacket emiPacket = new DatagramPacket(buff, buff.length);
                serverSocket.receive(emiPacket);
                String message = new String(buff);
                if( message.contains("I'm new!")){
                    byte [] size = ByteBuffer.allocate(4).putInt(sensorsValues.size()).array();
                    DatagramPacket sizePacket = new DatagramPacket(size, size.length, emiPacket.getAddress(), emiPacket.getPort());
                    serverSocket.send(sizePacket);
                    buff = new byte[10];
                    DatagramPacket binaries = new DatagramPacket(buff, buff.length);
                    serverSocket.receive(binaries);
                    String measurementBinaries = new String(binaries.getData());
                    byte [] bufferValues;
                    DatagramPacket valuesPacket;
                    for(int i = 0; i < 3; i++) {
                        for (Sensor elements : sensorsValues) {
                            if (i == 0 && measurementBinaries.charAt(0) == '1'){
                                bufferValues = ByteBuffer.allocate(8).putFloat(elements.getTemperature()).array();
                                valuesPacket = new DatagramPacket(bufferValues, bufferValues.length, emiPacket.getAddress(), emiPacket.getPort());
                                serverSocket.send(valuesPacket);
                            }
                            if (i == 1 && measurementBinaries.charAt(1) == '1'){
                                bufferValues = ByteBuffer.allocate(8).putFloat(elements.getPressure()).array();
                                valuesPacket = new DatagramPacket(bufferValues, bufferValues.length, emiPacket.getAddress(), emiPacket.getPort());
                                serverSocket.send(valuesPacket);
                            }
                            if (i == 2 && measurementBinaries.charAt(2) == '1'){
                                bufferValues = ByteBuffer.allocate(8).putFloat(elements.getPressure()).array();
                                valuesPacket = new DatagramPacket(bufferValues, bufferValues.length, emiPacket.getAddress(), emiPacket.getPort());
                                serverSocket.send(valuesPacket);
                            }
                        }
                    }
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace( );
        }
    }
}
