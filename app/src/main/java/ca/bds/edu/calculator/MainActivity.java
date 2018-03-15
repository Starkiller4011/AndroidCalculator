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
    private int StateIds[] = new int[3];
    private View LayoutContent;
    private ViewGroup LayoutRoot;
/*
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_simple:
                    ChangeTypeState(0);
                    return true;
                case R.id.navigation_scientific:
                    ChangeTypeState(1);
                    return true;
                case R.id.navigation_graphing:
                    ChangeTypeState(2);
                    return true;
            }
            return false;
        }
    };
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize member variables
        StateIds[0] = R.layout.simple_calc;
        StateIds[1] = R.layout.scientific_calc;
        StateIds[2] = R.layout.graphing_calc;
        TypeStates[0] = findViewById(R.id.button_type_simple);
        TypeStates[1] = findViewById(R.id.button_type_scientific);
        TypeStates[2] = findViewById(R.id.button_type_graphing);
        LayoutRoot = findViewById(R.id.layout_button_zone);

        // Set initial state to simple calculator
        ChangeTypeState(0);

        // Top nav bar button listeners
        TypeStates[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeTypeState(0);
            }
        });
        TypeStates[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeTypeState(1);
            }
        });
        TypeStates[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeTypeState(2);
            }
        });

        // This is the old BottomNavigationView code
        // TODO - Remove the BottomNavigationView
        //BottomNavigationView navigation = findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void ChangeTypeState(int State) {
        // Reset layout content
        if (LayoutContent != null) {
            LayoutRoot.removeView(LayoutContent);
        }
        // Get new layout content
        LayoutContent = getLayoutInflater().inflate(StateIds[State], null);
        // Set new layout content
        LayoutRoot.addView(LayoutContent);
        // Reset nav-bar text color
        TypeStates[0].setTextColor(getResources().getColor(R.color.colorTextLight));
        TypeStates[1].setTextColor(getResources().getColor(R.color.colorTextLight));
        TypeStates[2].setTextColor(getResources().getColor(R.color.colorTextLight));
        // Set current state nav-bar text color
        TypeStates[State].setTextColor(getResources().getColor(R.color.colorTextSelected));
    }

}
