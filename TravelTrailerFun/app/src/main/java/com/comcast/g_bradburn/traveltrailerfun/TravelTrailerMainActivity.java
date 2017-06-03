package com.comcast.g_bradburn.traveltrailerfun;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Greg on 5/14/2017.
 */

public class TravelTrailerMainActivity extends Activity {

    MyCustomAdapter dataAdapter = null;

    ArrayList<Action> actionList = null;

//    private RadioGroup selectListType;
//    private RadioButton selectionRB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));
        setContentView(R.layout.main);

        // Generate list View from ArrayList
        String selection = "ArriveCamp";
        //      "HomeToStorage"
        //      "DepartStorage"
        //      "ArriveCamp"
        //      "DepartCamp"
        //      "Dump Station"
        //      "ArriveStorage"
        //      "StorageToHome"

        displayListView(selection);

        checkButtonClick();

//        addListenerRG();
    }

    @Override
    protected void onResume() {
        // Recover saved states of CheckBoxes
        super.onResume();
    }

    @Override
    protected void onPause() {
        // Save states of CheckBoxes
        super.onPause();
    }

    @Override
    protected void onStart() {
        // Recover saved states of CheckBoxes?  Do I need to do anything extra here?
        super.onStart();
    }

    @Override
    protected void onRestart() {
        // Recover saved states of CheckBoxes?  Do I need to do anything extra here?
        super.onRestart();
    }

//    private void displayListView() {
    private void displayListView(String selection) {
        actionList = new ArrayList<>();

        myArrayList(actionList, "ArriveCamp");
        //      "HomeToStorage"
        //      "DepartStorage"
        //      "ArriveCamp"
        //      "DepartCamp"
        //      "Dump Station"
        //      "ArriveStorage"
        //      "StorageToHome"

        // Create an ArrayAdapter from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.list_item, actionList);
        ListView listView = (ListView) findViewById(R.id.actionListView);
        //Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Action myAction = (Action) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + myAction.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private class MyCustomAdapter extends ArrayAdapter<Action> {

        private ArrayList<Action> actionList;

        public MyCustomAdapter(Context context, int textViewResourcesId,
                               ArrayList<Action> actionList) {
            super(context, textViewResourcesId, actionList);
            this.actionList = new ArrayList<Action>();
            this.actionList.addAll(actionList);
        }
        // TT_Action is a List Item that identifies an act that should be verified by clicking a checkbox

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_item, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Action myAction = (Action) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on CheckBox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        myAction.setSelected(cb.isChecked());
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Action myAction = actionList.get(position);
            holder.code.setText(" (" + myAction.getCode() + ")");
            holder.name.setText(myAction.getName());
            holder.name.setChecked(myAction.isSelected());
            holder.name.setTag(myAction);

            return convertView;
        }

    }

    private void checkButtonClick() {

        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Action> actionList = dataAdapter.actionList;
                for (int i = 0; i < actionList.size(); i++) {
                    Action myAction = actionList.get(i);
                    if (myAction.isSelected()) {
                        responseText.append("\n" + myAction.getName());
                    }

                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();
            }

        });
    }

//    private void addListenerRG(){
//        selectListType = (RadioGroup) findViewById(R.id.selectTripRG);
//
//        selectListType.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // get selected radio button from radio button group
//                int selectedID = selectListType.getCheckedRadioButtonId();
//
//                // find the radio button by returned id
//                switch (selectedID) {
//                    case selectHome2Storage: {
//                        displayListView("HomeToStorage");
//                    }
//                    case selectStorage2Camp: {
//                        displayListView("DepartStorage");
//                    }
//                    case selectArriveCamp: {
//                        displayListView("ArriveCamp");
//                    }
//                    case selectDepartCamp: {
//                        displayListView("DepartCamp");
//                    }
//                    case selectCamp2Storage: {
//                        displayListView("DumpStation");
//                    }
//                    case selectDumpStation: {
//                        displayListView("ArriveStorage");
//                    }
//                    case selectStorage2Home: {
//                        displayListView("ReturnFromStorage");
//                    }
//                    default: {
//                        // problem
//                    }
//                }
//            }
//        });
//    }

    public void myArrayList(ArrayList<Action> inArrList, String whichList) {
        // Array list of actions
        // whichList (String):
        //      "HomeToStorage"
        //      "DepartStorage"
        //      "ArriveCamp"
        //      "DepartCamp"
        //      "DumpStation"
        //      "ArriveStorage"
        //      "ReturnFromStorage"

        Action myAction = new Action("", "", false);

        switch (whichList) {
            // Leaving home, heading to storage facility
            case "HomeToStorage":
                myAction = new Action("AFG", "Install hitch on truck!", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Load tool box", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Load generator(s)", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Load gas for generator(s) - no more than 10% alcohol", false);
                inArrList.add(myAction);
                break;

            // Leaving storage facility, heading to camping / trip
            case "DepartStorage":
                myAction = new Action("AFG", "Turn on LP gas (one bottle)", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Turn refrigerator on (Auto or LP mode)", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Ensure water tank filled (if no water available at destination)", false);
                inArrList.add(myAction);
                break;

            // Arriving at camping location and setting up
            case "ArriveCamp":
                myAction = new Action("ALB", "Level side to side", false);
                inArrList.add(myAction);

                myAction = new Action("DZA", "Chock wheels", false);
                inArrList.add(myAction);

                myAction = new Action("AND", "Disconnect from truck (brakes and ball) and move truck forward", false);
                inArrList.add(myAction);

                myAction = new Action("ASM", "Level front to back", false);
                inArrList.add(myAction);

                myAction = new Action("AGO", "Connect power, 30A", false);
                inArrList.add(myAction);

                myAction = new Action("AIA", "Cleanse & connect water (regulator, filter & hose)", false);
                inArrList.add(myAction);

                myAction = new Action("AIA", "Connect sewer hose (if available)", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Set refrigerator source to electric (if appropriate)", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Put batteries in refrigerator fan", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Unpack food to refrigerator (when cold)", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Unpack clothes", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Unpack primary box", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Unpack clothes", false);
                inArrList.add(myAction);
                break;

            // Leaving camping location for storage facility
            case "DepartCamp":
                myAction = new Action("AFG", "Disconnect", false);
                inArrList.add(myAction);
                break;

            // Dump station
            case "DumpStation":
                myAction = new Action("AFG", "Connect seweer drain hose", false);
                inArrList.add(myAction);
                break;

            // Arriving at storage facility to leave trailer
            case "ArriveStorage":
                myAction = new Action("AFG", "Turn off LP gas (both bottles", false);
                inArrList.add(myAction);

                myAction = new Action("AFG", "Empty refrigerator and ensure doors are propped open", false);
                inArrList.add(myAction);
                break;

            // Leaving storage facility and heading home
            case "StorageToHome":
                break;

            default:
                myAction = new Action("AIA", "Connect water1", false);
                inArrList.add(myAction);

                myAction = new Action("AIA", "Connect water2", false);
                inArrList.add(myAction);

                myAction = new Action("AIA", "Connect water3", false);
                inArrList.add(myAction);

                myAction = new Action("AIA", "Connect water4", false);
                inArrList.add(myAction);

                myAction = new Action("AIA", "Connect water5", false);
                inArrList.add(myAction);

                myAction = new Action("AIA", "Connect water6", false);
                inArrList.add(myAction);

                myAction = new Action("AIA", "Connect water7", false);
                inArrList.add(myAction);

                myAction = new Action("AIA", "Connect water8", false);
                inArrList.add(myAction);

                myAction = new Action("AIA", "Connect water9", false);
                inArrList.add(myAction);

                myAction = new Action("AIA", "Connect water10", false);
                inArrList.add(myAction);
        }

    }

}
