package jason.networkapp;

public class Skill {
    public int skill_id = 0;
    public String name = "";
    public String description = "";
    public String type = "";
    public String weapon_type = "";
    public String slot = "";
    public String category = "";
    public String attunement = "";
    public String toolbelt_skill = "";
    public int cost = 0;
    public String dual_wield = "";
    public int flip_skill = 0;
    public int initiative = 0;
    public int next_chain = 0;
    public int prev_chain = 0;

    Skill(int q, String w, String e, String r, String t, String y, String u, String i, String o, int p, String a, int s, int d, int f, int g){
        skill_id = q;
        name = w;
        description = e;
        type = r;
        weapon_type = t;
        slot = y;
        category = u;
        attunement = i;
        toolbelt_skill = o;
        cost = p;
        dual_wield = a;
        flip_skill = s;
        initiative = d;
        next_chain = f;
        prev_chain = g;
    }

    Skill() {
    }
}
