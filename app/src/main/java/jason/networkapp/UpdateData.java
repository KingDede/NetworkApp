package jason.networkapp;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateData {
    public Spec spec;
    public Skill skill;
    public Trait trait;
    public SkillProf skill_prof;
    public String table_name;
    public String operation;

    public UpdateData(Spec spec, Skill skill, Trait trait, SkillProf skill_prof, String table_name, String operation) {
        this.spec = spec;
        this.skill = skill;
        this.trait = trait;
        this.skill_prof = skill_prof;
        this.table_name = table_name;
        this.operation = operation;
    }

    public UpdateData() {
    }

    public void FromJson(JSONObject json){
        try {
            table_name = json.getString("table_name");
            operation = json.getString("operation");
            switch (table_name){
                case "Specializations":
                    spec.spec_id = json.getInt("spec_id");
                case "Skills":

                case "Traits":

                case "Skill_Prof":
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
