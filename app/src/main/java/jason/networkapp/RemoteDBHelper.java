package jason.networkapp;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class RemoteDBHelper {
    private final String rootURL = "http://kingdede.tk/gw2/";
    private final Context parentContext;

    private ArrayList<Spec> specOutput = new ArrayList<>();
    private ArrayList<Skill> skillOutput = new ArrayList<>();
    private ArrayList<Trait> traitOutput = new ArrayList<>();
    private ArrayList<SkillProf> skillProfOutput = new ArrayList<>();

    private ListObject allOutput;

    RemoteDBHelper(Context context) {
        parentContext = context;
    }

    public ListObject intialInsert()
    {
        IntialInsertTask myTask = new IntialInsertTask();
        myTask.execute();
        //Log.e("public method data", " "+ allOutput.spec_list.get(0).name);
        return allOutput;

    }


    private class IntialInsertTask extends AsyncTask<Void, Void, ListObject>
    {

        @Override
        protected void onPreExecute() {}

        @Override
        protected ListObject doInBackground(Void... params) {
            BufferedReader rd;
            StringBuilder sb = null;
            String line;
            try {
                URL url = new URL(rootURL + "getalldata");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sb = new StringBuilder();

                while ((line = rd.readLine()) != null)
                {
                    sb.append(line + '\n');
                }
                Log.e("Response: ", sb.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String response = sb.toString();

            JSONObject jsonResult = null;
            if (response != null) {
                try {
                    jsonResult = new JSONObject(response);
                    Log.d("JSON Object: ", jsonResult.toString());
                    Log.d("JSON Object Length: ", "" + jsonResult.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ArrayList<Spec> spec = new ArrayList<>();
            ArrayList<Skill> skill = new ArrayList<>();
            ArrayList<Trait> trait = new ArrayList<>();
            ArrayList<SkillProf> skillProf = new ArrayList<>();

            if (jsonResult != null) {
                JSONArray specializations;
                try {
                    specializations = jsonResult.getJSONArray("Specializations");
                    for (int i = 0; i < specializations.length(); i++) {
                        JSONObject jsonArrayObject = specializations.getJSONObject(i);
                        int spec_id = Integer.parseInt((String) jsonArrayObject.get("spec_id"));
                        String name = jsonArrayObject.get("name").toString();
                        int elite = Integer.parseInt((String) jsonArrayObject.get("elite"));
                        String icon = jsonArrayObject.get("icon").toString();
                        String background = jsonArrayObject.get("background").toString();
                        String profession = jsonArrayObject.get("profession").toString();
                        // Log for debugging.
                        Log.e("spec object:", "" + spec_id + ", " + name + ", " + elite + ", " + icon + ", " + background + ", " + profession);
                        spec.add(new Spec(spec_id, name, elite, icon, background, profession));
                    }
                } catch (JSONException e) {
                    // Log exceptions
                    e.printStackTrace();
                }

                JSONArray skills;
                try {
                    skills = jsonResult.getJSONArray("Skills");
                    for (int i = 0; i < skills.length(); i++) {
                        JSONObject jsonArrayObject = skills.getJSONObject(i);
                        int skill_id = Integer.parseInt((String) jsonArrayObject.get("skill_id"));
                        String name = jsonArrayObject.get("name").toString();
                        String description = jsonArrayObject.get("description").toString();
                        String type = jsonArrayObject.get("type").toString();
                        String weapon_type = jsonArrayObject.get("weapon_type").toString();
                        String slot = jsonArrayObject.get("slot").toString();
                        String category = jsonArrayObject.get("category").toString();
                        String attunement = jsonArrayObject.get("attunement").toString();
                        String toolbelt_skill = jsonArrayObject.get("toolbelt_skill").toString();
                        int cost = Integer.parseInt((String) jsonArrayObject.get("cost"));
                        String dual_wield = jsonArrayObject.get("dual_wield").toString();
                        int flip_skill = Integer.parseInt((String) jsonArrayObject.get("flip_skill"));
                        int initiative = Integer.parseInt((String) jsonArrayObject.get("initiative"));
                        int next_chain = Integer.parseInt((String) jsonArrayObject.get("next_chain"));
                        int prev_chain = Integer.parseInt((String) jsonArrayObject.get("prev_chain"));
                        // Log for debugging.
                        Log.e("skill object:", "" + skill_id + ", " + name + ", " + description + ", " + type + ", " + weapon_type + ", " + slot + ", " + category + ", " + attunement +
                                ", " + toolbelt_skill + ", " + cost + ", " + dual_wield + ", " + flip_skill + ", " + initiative + ", " + next_chain + ", " + prev_chain);
                        skill.add(new Skill(skill_id, name, description, type, weapon_type, slot, category, attunement, toolbelt_skill, cost, dual_wield, flip_skill, initiative, next_chain, prev_chain));
                    }
                } catch (JSONException e) {
                    // Log exceptions
                    e.printStackTrace();
                }

                JSONArray traits;
                try {
                    traits = jsonResult.getJSONArray("Traits");
                    for (int i = 0; i < traits.length(); i++) {
                        JSONObject jsonArrayObject = traits.getJSONObject(i);
                        int trait_id = Integer.parseInt((String) jsonArrayObject.get("trait_id"));
                        String name = jsonArrayObject.get("name").toString();
                        String description = jsonArrayObject.get("description").toString();
                        int spec_id = Integer.parseInt((String) jsonArrayObject.get("spec_id"));
                        int tier = Integer.parseInt((String) jsonArrayObject.get("tier"));
                        String slot = jsonArrayObject.get("slot").toString();
                        String icon = jsonArrayObject.get("icon").toString();
                        // Log for debugging.
                        Log.e("trait object:", "" + trait_id + ", " + name + ", " + description + ", " + spec_id + ", " + tier + ", " + slot + ", " + icon);
                        trait.add(new Trait(trait_id, name, description, spec_id, tier, slot, icon));
                    }
                } catch (JSONException e) {
                    // Log exceptions
                    e.printStackTrace();
                }

                JSONArray skillprof;
                try {
                    skillprof = jsonResult.getJSONArray("Skill_Prof");
                    for (int i = 0; i < skillprof.length(); i++) {
                        JSONObject jsonArrayObject = skillprof.getJSONObject(i);
                        int skill_id = Integer.parseInt((String) jsonArrayObject.get("skill_id"));
                        int elementalist = Integer.parseInt((String) jsonArrayObject.get("elementalist"));
                        int mesmer = Integer.parseInt((String) jsonArrayObject.get("mesmer"));
                        int necromancer = Integer.parseInt((String) jsonArrayObject.get("necromancer"));
                        int ranger = Integer.parseInt((String) jsonArrayObject.get("ranger"));
                        int thief = Integer.parseInt((String) jsonArrayObject.get("thief"));
                        int engineer = Integer.parseInt((String) jsonArrayObject.get("engineer"));
                        int warrior = Integer.parseInt((String) jsonArrayObject.get("warrior"));
                        int guardian = Integer.parseInt((String) jsonArrayObject.get("guardian"));
                        int revenant = Integer.parseInt((String) jsonArrayObject.get("revenant"));

                        // Log for debugging.
                        Log.e("skill_prof object:", "" + skill_id + ", " + elementalist + ", " + mesmer + ", " + necromancer + ", " + ranger + ", " + thief + ", " + engineer +
                                ", " + warrior + ", " + guardian + ", " + revenant);
                        skillProf.add(new SkillProf(skill_id, elementalist, mesmer, necromancer, ranger, thief, engineer, warrior, guardian, revenant));
                    }
                } catch (JSONException e) {
                    // Log exceptions
                    e.printStackTrace();
                }
            }
            ListObject output = new ListObject(spec, skill, trait, skillProf);
            Log.e("do in background result", "" + output.spec_list.get(0).name);
            return output;
        }

        //its broke
        @Override
        protected void onPostExecute(ListObject result) {
            allOutput = result;
            Log.e("postexecute result", "" + result.spec_list.get(0).name + "all output: " + allOutput.spec_list.get(0).name);
        }
    }

}
