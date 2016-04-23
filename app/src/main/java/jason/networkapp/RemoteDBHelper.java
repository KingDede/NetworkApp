package jason.networkapp;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RemoteDBHelper {
    private final String rootURL = "http://kingdede.tk/gw2/";
    private final Context parentContext;

    private ArrayList<Spec> specOutput = new ArrayList<>();
    private ArrayList<Skill> skillOutput = new ArrayList<>();
    private ArrayList<Trait> traitOutput = new ArrayList<>();
    private ArrayList<SkillProf> skillProfOutput = new ArrayList<>();

    private ListObject allOutput = new ListObject(null, null, null, null);
    private ArrayList<UpdateData> updateList = new ArrayList<>();

    RemoteDBHelper(Context context) {
        parentContext = context;
    }

    public ListObject intialInsert() {
        IntialInsertTask myTask = new IntialInsertTask();
        try {
            allOutput = myTask.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        //Log.e("public method data", " "+ allOutput.spec_list.get(0).name);
        return allOutput;

    }

    public ArrayList<UpdateData> update(String lastUpdate) {
        UpdateTask myTask = new UpdateTask();
        try {
            updateList = myTask.execute(lastUpdate).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return updateList;
    }


    private class IntialInsertTask extends AsyncTask<Void, Void, ListObject> {

        @Override
        protected void onPreExecute() {
        }

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
                rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sb = new StringBuilder();

                while ((line = rd.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                Log.e("Response: ", sb.toString());

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
                    for (Integer i = 0; i < specializations.length(); i++) {
                        JSONObject jsonArrayObject = specializations.getJSONObject(i);
                        Integer spec_id = null;
                        if (jsonArrayObject.get("spec_id") != null) {
                            spec_id = jsonArrayObject.optInt("spec_id");
                        }
                        String name = jsonArrayObject.get("name").toString();
                        Integer elite = null;
                        if (jsonArrayObject.get("elite") != null) {
                            elite = jsonArrayObject.optInt("elite");
                        }
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
                    for (Integer i = 0; i < skills.length(); i++) {
                        JSONObject jsonArrayObject = skills.getJSONObject(i);
                        Integer skill_id = null;
                        if (jsonArrayObject.get("skill_id") != null) {
                            skill_id = jsonArrayObject.optInt("skill_id");
                        }
                        String name = jsonArrayObject.get("name").toString();
                        String description = jsonArrayObject.get("description").toString();
                        String type = jsonArrayObject.get("type").toString();
                        String weapon_type = jsonArrayObject.get("weapon_type").toString();
                        String slot = jsonArrayObject.get("slot").toString();
                        String category = jsonArrayObject.get("category").toString();
                        String attunement = jsonArrayObject.get("attunement").toString();
                        String toolbelt_skill = jsonArrayObject.get("toolbelt_skill").toString();
                        Integer cost = null;
                        if (jsonArrayObject.get("cost") != null) {
                            cost = jsonArrayObject.optInt("cost");
                        }
                        String dual_wield = jsonArrayObject.get("dual_wield").toString();
                        Integer flip_skill = null;
                        if (jsonArrayObject.get("flip_skill") != null) {
                            flip_skill = jsonArrayObject.optInt("flip_skill");
                        }
                        Integer initiative = null;
                        if (jsonArrayObject.get("initiative") != null) {
                            initiative = jsonArrayObject.optInt("initiative");
                        }
                        Integer next_chain = null;
                        if (jsonArrayObject.get("next_chain") != null) {
                            next_chain = jsonArrayObject.optInt("next_chain");
                        }
                        Integer prev_chain = null;
                        if (jsonArrayObject.get("prev_chain") != null) {
                            prev_chain = jsonArrayObject.optInt("prev_chain");
                        }
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
                    for (Integer i = 0; i < traits.length(); i++) {
                        JSONObject jsonArrayObject = traits.getJSONObject(i);
                        Integer trait_id = null;
                        if (jsonArrayObject.get("trait_id") != null) {
                            trait_id = jsonArrayObject.optInt("trait_id");
                        }
                        String name = jsonArrayObject.get("name").toString();
                        String description = jsonArrayObject.get("description").toString();
                        Integer spec_id = null;
                        if (jsonArrayObject.get("spec_id") != null) {
                            spec_id = jsonArrayObject.optInt("spec_id");
                        }
                        Integer tier = null;
                        if (jsonArrayObject.get("tier") != null) {
                            tier = jsonArrayObject.optInt("tier");
                        }
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
                    for (Integer i = 0; i < skillprof.length(); i++) {
                        JSONObject jsonArrayObject = skillprof.getJSONObject(i);
                        Integer skill_id = null;
                        if (jsonArrayObject.get("skill_id") != null) {
                            skill_id = jsonArrayObject.optInt("skill_id");
                        }
                        Integer elementalist = null;
                        if (jsonArrayObject.get("elementalist") != null) {
                            elementalist = jsonArrayObject.optInt("elementalist");
                        }
                        Integer mesmer = null;
                        if (jsonArrayObject.get("mesmer") != null) {
                            mesmer = jsonArrayObject.optInt("mesmer");
                        }
                        Integer necromancer = null;
                        if (jsonArrayObject.get("necromancer") != null) {
                            necromancer = jsonArrayObject.optInt("necromancer");
                        }
                        Integer ranger = null;
                        if (jsonArrayObject.get("ranger") != null) {
                            ranger = jsonArrayObject.optInt("ranger");
                        }
                        Integer thief = null;
                        if (jsonArrayObject.get("thief") != null) {
                            thief = jsonArrayObject.optInt("thief");
                        }
                        Integer engineer = null;
                        if (jsonArrayObject.get("engineer") != null) {
                            engineer = jsonArrayObject.optInt("engineer");
                        }
                        Integer warrior = null;
                        if (jsonArrayObject.get("warrior") != null) {
                            warrior = jsonArrayObject.optInt("warrior");
                        }
                        Integer guardian = null;
                        if (jsonArrayObject.get("guardian") != null) {
                            guardian = jsonArrayObject.optInt("guardian");
                        }
                        Integer revenant = null;
                        if (jsonArrayObject.get("revenant") != null) {
                            revenant = jsonArrayObject.optInt("revenant");
                        }

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
    }


    private class UpdateTask extends AsyncTask<String, Void, ArrayList<UpdateData>> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ArrayList<UpdateData> doInBackground(String... date) {
            BufferedReader rd;
            StringBuilder stringBuilder = null;
            String line;
            try {
                URL url = new URL(rootURL + "getchanges");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                connection.setDoOutput(true);
                connection.setDoInput(true);

                connection.connect();

                JSONObject jsonParam = new JSONObject();
                try {
                    jsonParam.put("date", date[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("date to server", jsonParam.toString());

                OutputStream outputStream = (OutputStream) connection.getOutputStream();
                outputStream.write(jsonParam.toString().getBytes());
                outputStream.flush();
                outputStream.close();

                InputStream inputStream = (InputStream) connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                stringBuilder = new StringBuilder();

                String jsonData;
                while ((jsonData = bufferedReader.readLine()) != null) {
                    stringBuilder.append(jsonData);
                }
                Log.d("JSON string: ", stringBuilder.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            String response = stringBuilder.toString();

            JSONArray jsonResult = null;
            if (response != null) {
                try {
                    jsonResult = new JSONArray(response);
                    Log.d("JSON Object: ", jsonResult.toString());
                    Log.d("JSON Object Length: ", "" + jsonResult.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ArrayList<UpdateData> output = new ArrayList<>();
            if (jsonResult != null) {
                for (Integer i = 0; i < jsonResult.length(); i++) {
                    try {
                        JSONObject jsonData = jsonResult.getJSONObject(i);
                        output.add(new UpdateData().FromJson(jsonData));
                        Log.d("JSON object in array", jsonData.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
            return output;
        }
    }

}
