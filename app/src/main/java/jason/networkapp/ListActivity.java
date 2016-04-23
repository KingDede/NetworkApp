package jason.networkapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbHelper = new DBHelper(this);
        ListView listView = (ListView)findViewById(R.id.listView);
        Intent intent = getIntent();
        String profession = intent.getStringExtra("profession");
        AllProfessionData listContents = dbHelper.getProfessionData(profession);
        ArrayList<String> finalList = new ArrayList<>();

        finalList.add(profession.substring(0, 1).toUpperCase() + profession.substring(1));
        finalList.add("    Specializations");
        for (ProfessionData pd : listContents.spec_traits) {
            finalList.add("        " +String.valueOf(pd.specs.name));
            finalList.add("            Traits");
            for (Trait tr : pd.traits) {
                finalList.add("                " + tr.name);
            }
        }

        finalList.add("    Skills");
        for (Skill sk : listContents.skills) {
            finalList.add("         " + sk.name +": " + sk.description);
        }

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, finalList));


    }
}
