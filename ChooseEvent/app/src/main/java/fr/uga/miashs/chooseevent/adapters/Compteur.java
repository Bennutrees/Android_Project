package fr.uga.miashs.chooseevent.adapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.uga.miashs.chooseevent.adapters.ActiviteBidon;

public class Compteur extends AppCompatActivity {

    private int compteur = 0;
    private TextView tv;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout l = new LinearLayout(this);
        tv = new TextView(this);
        Button bt = new Button(this);
        bt.setText("Cliquez ici");
        l.addView(tv);
        l.addView(bt);
        bt.setOnClickListener((View.OnClickListener) this);
        setContentView(l);
        compteur++;
        tv.setText(Integer.toString(compteur));
    }

    public void onClick(View v) {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), ActiviteBidon.class);
        startActivity(i);
    }
}
