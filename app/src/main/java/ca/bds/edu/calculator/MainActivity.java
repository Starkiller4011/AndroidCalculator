package ca.bds.edu.calculator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Button TypeStates[] = new Button[3];
    private View ButtonZone;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            ViewGroup ButtonZoneContainer = findViewById(R.id.layout_button_zone);

            switch (item.getItemId()) {
                case R.id.navigation_simple:
                    ChangeTargetLayout(ButtonZoneContainer, ButtonZone, R.layout.simple_calc, TypeStates, 0);
                    return true;
                case R.id.navigation_scientific:
                    ChangeTargetLayout(ButtonZoneContainer, ButtonZone, R.layout.scientific_calc, TypeStates, 1);
                    return true;
                case R.id.navigation_graphing:
                    ChangeTargetLayout(ButtonZoneContainer, ButtonZone, R.layout.graphing_calc, TypeStates, 2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize member variables
        TypeStates[0] = findViewById(R.id.button_type_simple);
        TypeStates[1] = findViewById(R.id.button_type_scientific);
        TypeStates[2] = findViewById(R.id.button_type_graphing);
        ViewGroup ButtonZoneContainer = findViewById(R.id.layout_button_zone);

        // Set initial state to simple calculator
        ChangeTargetLayout(ButtonZoneContainer, ButtonZone, R.layout.simple_calc, TypeStates, 0);

        

        // This is the old BottomNavigationView code
        // TODO - Remove the BottomNavigationView
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void ChangeTargetLayout(ViewGroup TargetViewSpace, View TargetView, int TargetId, Button[] TypeStates, int State) {
        if (TargetView != null) {
            TargetViewSpace.removeView(TargetView);
        }
        TypeStates[0].setTextColor(getResources().getColor(R.color.colorTextLight));
        TypeStates[1].setTextColor(getResources().getColor(R.color.colorTextLight));
        TypeStates[2].setTextColor(getResources().getColor(R.color.colorTextLight));
        TypeStates[State].setTextColor(getResources().getColor(R.color.colorTextSelected));
        TargetView = getLayoutInflater().inflate(TargetId, null);
        TargetViewSpace.addView(TargetView);
    }

}
