package serverpackage;

import java.util.Random;

public class Sensor {
    float temperature;
    float humidity;
    float pressure;
    Random rand = new Random();

    public Sensor() {
        this.temperature = rand.nextInt(40);
        this.humidity = rand.nextInt(100);
        this.pressure = rand.nextInt(1);
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }
}
