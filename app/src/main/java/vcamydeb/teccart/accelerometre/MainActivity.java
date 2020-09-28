package vcamydeb.teccart.accelerometre;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm ;
    private List<Sensor> sensorList;
    private Sensor accelerometer;
    private TextView gX;
    private TextView gY;
    private TextView gZ;
    private TextView txtMove;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sm.getSensorList(Sensor.TYPE_ALL);

        for(Sensor element:sensorList) {
            System.out.println("Sensor vendor:" + element.toString());
            System.out.println("");
        }

        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_GAME);

        gX = (TextView)findViewById(R.id.Xacceleration);
        gY = (TextView)findViewById(R.id.Yacceleration);
        gZ = (TextView)findViewById(R.id.Zacceleration);
        txtMove = (TextView)findViewById(R.id.txtMove);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float vectorLength;

        gX.setText("X: " + String.valueOf(event.values[0]));
        gY.setText("Y: " + String.valueOf(event.values[1]));
        gZ.setText("Z: " + String.valueOf(event.values[2]));

        vectorLength = (float)Math.sqrt(Math.pow((double)(event.values[0]),2)+Math.pow((double)(event.values[1]),2)
                + Math.pow((double)(event.values[2]),2));

        txtMove.setX(txtMove.getX() + event.values[0] * (-1*vectorLength));
        txtMove.setY(txtMove.getY() + event.values[1] * vectorLength);

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}