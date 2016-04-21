package jason.networkapp;


public class Trait {
    public int trait_id = 0;
    public String name = "";
    public String description = "";
    public int spec_id = 0;
    public int tier = 0;
    public String slot = "";
    public String icon = "";

    Trait(int g, String u, String i, int l, int d, String w, String a){
        trait_id = g;
        name = u;
        description = i;
        spec_id = l;
        tier = d;
        slot = w;
        icon = a;

    }
}
