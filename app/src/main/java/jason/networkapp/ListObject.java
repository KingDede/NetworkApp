package jason.networkapp;

import java.util.ArrayList;

public class ListObject {
    ArrayList<Spec> spec_list = new ArrayList<>();
    ArrayList<Skill> skill_list = new ArrayList<>();
    ArrayList<Trait> trait_list = new ArrayList<>();
    ArrayList<SkillProf> skill_prof_list = new ArrayList<>();

    ListObject(ArrayList<Spec> sp, ArrayList<Skill> sk, ArrayList<Trait> tr, ArrayList<SkillProf> skp)
    {
        spec_list = sp;
        skill_list = sk;
        trait_list = tr;
        skill_prof_list = skp;
    }
}
