package jason.networkapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    // Initialise constants
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Gw2Build";
    private static final String SPEC_TABLE_NAME = "Specializations";
    private static final String[] SPEC_COLUMN_NAMES = {"spec_id", "name", "elite", "icon", "background", "profession"};
    private final Context parentContext;

    private RemoteDBHelper RemoteDBHelper;

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        parentContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String specQuery;
        String skillQuery;
        String traitQuery;
        String skillProfQuery;
        RemoteDBHelper = new RemoteDBHelper(parentContext);
        specQuery = "CREATE TABLE Specializations (spec_id INTEGER, name TEXT, elite INTEGER, icon TEXT, background TEXT, profession TEXT)";
        skillQuery = "CREATE TABLE Skills (skill_id INTEGER, name TEXT, description TEXT, type TEXT, weapon_type TEXT, slot TEXT, category TEXT, attunement TEXT," +
                                    " toolbelt_skill TEXT, cost INTEGER, dual_wield TEXT, flip_skill INTEGER, initiative INTEGER, next_chain INTEGER, prev_chain INTEGER)";
        traitQuery = "CREATE TABLE Traits (trait_id INTEGER, name TEXT, description TEXT, spec_id INTEGER, tier INTEGER, slot TEXT, icon TEXT)";
        skillProfQuery = "CREATE TABLE Skill_Prof (skill_id INTEGER, elementalist INTEGER, mesmer INTEGER, necromancer INTEGER, ranger INTEGER, thief INTEGER, engineer INTEGER," +
                " warrior INTEGER, guardian INTEGER, revenant INTEGER)";
        ListObject data = RemoteDBHelper.intialInsert();
        db.execSQL(specQuery);
        db.execSQL(skillQuery);
        db.execSQL(traitQuery);
        db.execSQL(skillProfQuery);
        Log.e("all the data", "" + data.spec_list.get(0).name);

        if (data != null) {
            for (Spec s : data.spec_list) {
                ContentValues row = new ContentValues();
                row.put("spec_id", s.spec_id);
                row.put("name", s.name);
                row.put("elite", s.elite);
                row.put("icon", s.icon);
                row.put("background", s.background);
                row.put("profession", s.profession);
                db.insert(SPEC_TABLE_NAME, null, row);
                Log.e("insert data", "" + s);
            }

            for (Skill s : data.skill_list) {
                ContentValues row = new ContentValues();
                row.put("skill_id", s.skill_id);
                row.put("name", s.name);
                row.put("description", s.description);
                row.put("type", s.type);
                row.put("weapon_type", s.weapon_type);
                row.put("slot", s.slot);
                row.put("category", s.category);
                row.put("attunement", s.attunement);
                row.put("toolbelt_skill", s.toolbelt_skill);
                row.put("cost", s.cost);
                row.put("dualwield", s.dual_wield);
                row.put("flip_skill", s.flip_skill);
                row.put("initiative", s.initiative);
                row.put("next_chain", s.next_chain);
                row.put("prev_chain", s.prev_chain);

                db.insert("Skills", null, row);
                Log.e("insert data", "" + s);
            }

            for (Trait s : data.trait_list) {
                ContentValues row = new ContentValues();
                row.put("trait_id", s.trait_id);
                row.put("name", s.name);
                row.put("description", s.description);
                row.put("spec_id", s.spec_id);
                row.put("tier", s.tier);
                row.put("slot", s.slot);
                row.put("icon", s.icon);

                db.insert("Traits", null, row);
                Log.e("insert data", "" + s);
            }

            for (SkillProf s : data.skill_prof_list) {
                ContentValues row = new ContentValues();
                row.put("skill_id", s.skill_id);
                row.put("elementalist", s.elementalist);
                row.put("mesmer", s.ranger);
                row.put("necromancer", s.necromancer);
                row.put("ranger", s.ranger);
                row.put("thief", s.thief);
                row.put("engineer", s.engineer);
                row.put("warrior", s.warrior);
                row.put("guardian", s.guardian);
                row.put("revenant", s.revenant);

                db.insert("Skill_Prof", null, row);
                Log.e("insert data", "" + s);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS Specializations";
        db.execSQL(query);
        onCreate(db);
    }

    public void addSpec(Spec s){
        ContentValues row = new ContentValues();
        row.put("spec_id", s.spec_id);
        row.put("name", s.name);
        row.put("elite", s.elite);
        row.put("icon", s.icon);
        row.put("background", s.background);
        row.put("profession", s.profession);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Specializations", null, row);
        db.close();
    }

    public void addSkill(Skill s){
        ContentValues row = new ContentValues();
        row.put("skill_id", s.skill_id);
        row.put("name", s.name);
        row.put("description", s.description);
        row.put("type", s.type);
        row.put("weapon_type", s.weapon_type);
        row.put("slot", s.slot);
        row.put("category", s.category);
        row.put("attunement", s.attunement);
        row.put("toolbelt_skill", s.toolbelt_skill);
        row.put("cost", s.cost);
        row.put("dualwield", s.dual_wield);
        row.put("flip_skill", s.flip_skill);
        row.put("initiative", s.initiative);
        row.put("next_chain", s.next_chain);
        row.put("prev_chain", s.prev_chain);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Skills", null, row);
        db.close();
    }

    public void addTrait(Trait s){
        ContentValues row = new ContentValues();
        row.put("trait_id", s.trait_id);
        row.put("name", s.name);
        row.put("description", s.description);
        row.put("spec_id", s.spec_id);
        row.put("tier", s.tier);
        row.put("slot", s.slot);
        row.put("icon", s.icon);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Traits", null, row);
        db.close();
    }

    public void addSkillProf(SkillProf s){
        ContentValues row = new ContentValues();
        row.put("skill_id", s.skill_id);
        row.put("elementalist", s.elementalist);
        row.put("mesmer", s.ranger);
        row.put("necromancer", s.necromancer);
        row.put("ranger", s.ranger);
        row.put("thief", s.thief);
        row.put("engineer", s.engineer);
        row.put("warrior", s.warrior);
        row.put("guardian", s.guardian);
        row.put("revenant", s.revenant);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Skill_Prof", null, row);
        db.close();
    }

    public int specRowCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.query(SPEC_TABLE_NAME, SPEC_COLUMN_NAMES, null, null, null, null, null, null);

        if(result != null) {
            int rowCount = result.getCount();
            db.close();
            return rowCount;
        } else {
            return -1;
        }
    }
}
