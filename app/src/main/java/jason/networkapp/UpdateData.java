package jason.networkapp;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateData {
    public Spec spec = new Spec();
    public Skill skill = new Skill();
    public Trait trait = new Trait();
    public SkillProf skill_prof = new SkillProf();
    public String table_name;
    public String operation;
    public String row_id;

    public UpdateData(Spec spec, Skill skill, Trait trait, SkillProf skill_prof, String row_id, String table_name, String operation) {
        this.spec = spec;
        this.skill = skill;
        this.trait = trait;
        this.skill_prof = skill_prof;
        this.table_name = table_name;
        this.operation = operation;
        this.row_id = row_id;
    }

    public UpdateData() {
    }

    public UpdateData FromJson(JSONObject json) {
        try {
            table_name = json.getString("table_name");
            operation = json.getString("operation");
            row_id = json.getString("row_id");
            if (!operation.equals("delete")) {
                switch (table_name) {
                    case "Specializations":
                        if (json.getJSONObject("0").get("spec_id") != null) {
                            spec.spec_id = json.getJSONObject("0").optInt("spec_id");
                        }
                        spec.name = json.getJSONObject("0").getString("name");
                        if (json.getJSONObject("0").get("elite") != null) {
                            spec.elite = json.getJSONObject("0").optInt("elite");
                        }
                        spec.icon = json.getJSONObject("0").getString("icon");
                        spec.background = json.getJSONObject("0").getString("background");
                        spec.profession = json.getJSONObject("0").getString("profession");
                        break;
                    case "Skills":
                        if (json.getJSONObject("0").get("skill_id") != null) {
                            skill.skill_id = json.getJSONObject("0").optInt("skill_id");
                        }
                        skill.name = json.getJSONObject("0").getString("name");
                        skill.description = json.getJSONObject("0").getString("description");
                        skill.type = json.getJSONObject("0").getString("type");
                        skill.weapon_type = json.getJSONObject("0").getString("weapon_type");
                        skill.slot = json.getJSONObject("0").getString("slot");
                        skill.category = json.getJSONObject("0").getString("category");
                        skill.attunement = json.getJSONObject("0").getString("attunement");
                        skill.toolbelt_skill = json.getJSONObject("0").getString("toolbelt_skill");
                        if (json.getJSONObject("0").get("cost") != null) {
                            skill.cost = json.getJSONObject("0").optInt("cost");
                        }
                        skill.dual_wield = json.getJSONObject("0").getString("dual_wield");
                        if (json.getJSONObject("0").get("flip_skill") != null) {
                            skill.flip_skill = json.getJSONObject("0").optInt("flip_skill");
                        }
                        if (json.getJSONObject("0").get("initiative") != null) {
                            skill.initiative = json.getJSONObject("0").optInt("initiative");
                        }
                        if (json.getJSONObject("0").get("next_chain") != null) {
                            skill.next_chain = json.getJSONObject("0").optInt("next_chain");
                        }
                        if (json.getJSONObject("0").get("prev_chain") != null) {
                            skill.prev_chain = json.getJSONObject("0").optInt("prev_chain");
                        }
                        break;
                    case "Traits":
                        if (json.getJSONObject("0").get("trait_id") != null) {
                            trait.trait_id = json.getJSONObject("0").optInt("trait_id");
                        }
                        trait.name = json.getJSONObject("0").getString("name");
                        trait.description = json.getJSONObject("0").getString("description");
                        if (json.getJSONObject("0").get("spec_id") != null) {
                            trait.spec_id = json.getJSONObject("0").optInt("spec_id");
                        }
                        trait.tier = json.getJSONObject("0").optInt("tier");
                        trait.slot = json.getJSONObject("0").getString("slot");
                        trait.icon = json.getJSONObject("0").getString("icon");
                        break;
                    case "Skill_Prof":
                        if (json.getJSONObject("0").get("skill_id") != null) {
                            skill_prof.skill_id = json.getJSONObject("0").optInt("skill_id");
                        }
                        if (json.getJSONObject("0").get("elementalist") != null) {
                            skill_prof.elementalist = json.getJSONObject("0").optInt("elementalist");
                        }
                        if (json.getJSONObject("0").get("mesmer") != null) {
                            skill_prof.mesmer = json.getJSONObject("0").optInt("mesmer");
                        }
                        if (json.getJSONObject("0").get("necromancer") != null) {
                            skill_prof.necromancer = json.getJSONObject("0").optInt("necromancer");
                        }
                        if (json.getJSONObject("0").get("ranger") != null) {
                            skill_prof.ranger = json.getJSONObject("0").optInt("ranger");
                        }
                        if (json.getJSONObject("0").get("thief") != null) {
                            skill_prof.thief = json.getJSONObject("0").optInt("thief");
                        }
                        if (json.getJSONObject("0").get("engineer") != null) {
                            skill_prof.engineer = json.getJSONObject("0").optInt("engineer");
                        }
                        if (json.getJSONObject("0").get("warrior") != null) {
                            skill_prof.warrior = json.getJSONObject("0").optInt("warrior");
                        }
                        if (json.getJSONObject("0").get("guardian") != null) {
                            skill_prof.guardian = json.getJSONObject("0").optInt("guardian");
                        }
                        if (json.getJSONObject("0").get("revenant") != null) {
                            skill_prof.revenant = json.getJSONObject("0").optInt("revenant");
                        }
                        break;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }
}
