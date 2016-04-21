package jason.networkapp;


public class SkillProf {
    public int skill_id = 0;
    public int elementalist = 0;
    public int mesmer = 0;
    public int necromancer = 0;
    public int ranger = 0;
    public int thief = 0;
    public int engineer = 0;
    public int warrior = 0;
    public int guardian = 0;
    public int revenant = 0;

    SkillProf(int g, int u, int i, int l, int d, int w, int a, int r, int s, int t){
        skill_id = g;
        elementalist = u;
        mesmer = a;
        necromancer = i;
        ranger = l;
        thief = d;
        engineer = w;
        warrior = r;
        guardian = s;
        revenant = t;
    }
}
