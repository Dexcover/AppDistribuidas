package softupcloud.appdistribuidas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /* my variables declared */
    private Button btn2;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn4;
    private Button btn3;
    private Button btn11;
    private Button btn12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* My reference to shActivity */
        final Intent intentBt2 = new Intent(this, ShActivity.class);
        final Intent intentBt5 = new Intent(this, PlaceActivity.class);
        final Intent intentBt6 = new Intent(this, SwActivity.class);
        final Intent intentBt7 = new Intent(this, CoSortActivity.class);
        final Intent intentBt4 = new Intent(this, DF_BeneficiarioActivity.class);
        final Intent intentBt3 = new Intent(this, DF_ColaboradorActivity.class);
        final Intent intentBt11 = new Intent(this, DF_FotoActivity.class);
        final Intent intentBt12 = new Intent(this, DF_TipoObraActivity.class);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* btns with their action listener */
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentBt2);
            }
        });

        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentBt5);
            }
        });

        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentBt6);
            }
        });

        btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentBt7);
            }
        });

        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentBt4);
            }
        });

        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentBt3);
            }
        });

        btn11 = (Button) findViewById(R.id.btn11);
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentBt11);
            }
        });

        btn12 = (Button) findViewById(R.id.btn12);
        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentBt12);
            }
        });
    }
}
