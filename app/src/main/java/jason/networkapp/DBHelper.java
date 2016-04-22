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
    private static final String SKILL_TABLE_NAME = "Skills";
    private static final String[] SKILL_COLUMN_NAMES = {"skill_id", "name", "description", "type", "weapon_type", "slot", "category","attunement",
                                                        "toolbelt_skill", "cost", "dual_wield", "flip_skill", "initiative", "next_chain", "prev_chain"};
    private static final String TRAIT_TABLE_NAME = "Traits";
    private static final String[] TRAIT_COLUMN_NAMES = {"trait_id", "name", "description", "spec_id", "tier", "slot", "icon"};
    private static final String SKILL_PROF_TABLE_NAME = "Skill_Prof";
    private static final String[] SKILL_PROF_COLUMN_NAMES = {"skill_id", "elementalist", "mesmer", "necromancer", "ranger", "thief", "engineer", "warrior", "guardian", "revenant"};
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
                row.put(SPEC_COLUMN_NAMES[0], s.spec_id);
                row.put(SPEC_COLUMN_NAMES[1], s.name);
                row.put(SPEC_COLUMN_NAMES[2], s.elite);
                row.put(SPEC_COLUMN_NAMES[3], s.icon);
                row.put(SPEC_COLUMN_NAMES[4], s.background);
                row.put(SPEC_COLUMN_NAMES[5], s.profession);
                
                db.insert(SPEC_TABLE_NAME, null, row);
                Log.e("insert data", "" + s);
            }

            for (Skill s : data.skill_list) {
                ContentValues row = new ContentValues();
                row.put(SKILL_COLUMN_NAMES[0], s.skill_id);
                row.put(SKILL_COLUMN_NAMES[1], s.name);
                row.put(SKILL_COLUMN_NAMES[2], s.description);
                row.put(SKILL_COLUMN_NAMES[3], s.type);
                row.put(SKILL_COLUMN_NAMES[4], s.weapon_type);
                row.put(SKILL_COLUMN_NAMES[5], s.slot);
                row.put(SKILL_COLUMN_NAMES[6], s.category);
                row.put(SKILL_COLUMN_NAMES[7], s.attunement);
                row.put(SKILL_COLUMN_NAMES[8], s.toolbelt_skill);
                row.put(SKILL_COLUMN_NAMES[9], s.cost);
                row.put(SKILL_COLUMN_NAMES[10], s.dual_wield);
                row.put(SKILL_COLUMN_NAMES[11], s.flip_skill);
                row.put(SKILL_COLUMN_NAMES[12], s.initiative);
                row.put(SKILL_COLUMN_NAMES[13], s.next_chain);
                row.put(SKILL_COLUMN_NAMES[14], s.prev_chain);

                db.insert(SKILL_TABLE_NAME, null, row);
                Log.e("insert data", "" + s);
            }

            for (Trait s : data.trait_list) {
                ContentValues row = new ContentValues();
                row.put(TRAIT_COLUMN_NAMES[0], s.trait_id);
                row.put(TRAIT_COLUMN_NAMES[1], s.name);
                row.put(TRAIT_COLUMN_NAMES[2], s.description);
                row.put(TRAIT_COLUMN_NAMES[3], s.spec_id);
                row.put(TRAIT_COLUMN_NAMES[4], s.tier);
                row.put(TRAIT_COLUMN_NAMES[5], s.slot);
                row.put(TRAIT_COLUMN_NAMES[6], s.icon);

                db.insert(TRAIT_TABLE_NAME, null, row);
                Log.e("insert data", "" + s);
            }

            for (SkillProf s : data.skill_prof_list) {
                ContentValues row = new ContentValues();
                row.put(SKILL_PROF_COLUMN_NAMES[0], s.skill_id);
                row.put(SKILL_PROF_COLUMN_NAMES[1], s.elementalist);
                row.put(SKILL_PROF_COLUMN_NAMES[2], s.ranger);
                row.put(SKILL_PROF_COLUMN_NAMES[3], s.necromancer);
                row.put(SKILL_PROF_COLUMN_NAMES[4], s.ranger);
                row.put(SKILL_PROF_COLUMN_NAMES[5], s.thief);
                row.put(SKILL_PROF_COLUMN_NAMES[6], s.engineer);
                row.put(SKILL_PROF_COLUMN_NAMES[7], s.warrior);
                row.put(SKILL_PROF_COLUMN_NAMES[8], s.guardian);
                row.put(SKILL_PROF_COLUMN_NAMES[9], s.revenant);

                db.insert(SKILL_PROF_TABLE_NAME, null, row);
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
        row.put(SPEC_COLUMN_NAMES[0], s.spec_id);
        row.put(SPEC_COLUMN_NAMES[1], s.name);
        row.put(SPEC_COLUMN_NAMES[2], s.elite);
        row.put(SPEC_COLUMN_NAMES[3], s.icon);
        row.put(SPEC_COLUMN_NAMES[4], s.background);
        row.put(SPEC_COLUMN_NAMES[5], s.profession);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(SPEC_TABLE_NAME, null, row);
        db.close();
    }

    public void addSkill(Skill s){
        ContentValues row = new ContentValues();
        row.put(SKILL_COLUMN_NAMES[0], s.skill_id);
        row.put(SKILL_COLUMN_NAMES[1], s.name);
        row.put(SKILL_COLUMN_NAMES[2], s.description);
        row.put(SKILL_COLUMN_NAMES[3], s.type);
        row.put(SKILL_COLUMN_NAMES[4], s.weapon_type);
        row.put(SKILL_COLUMN_NAMES[5], s.slot);
        row.put(SKILL_COLUMN_NAMES[6], s.category);
        row.put(SKILL_COLUMN_NAMES[7], s.attunement);
        row.put(SKILL_COLUMN_NAMES[8], s.toolbelt_skill);
        row.put(SKILL_COLUMN_NAMES[9], s.cost);
        row.put(SKILL_COLUMN_NAMES[10], s.dual_wield);
        row.put(SKILL_COLUMN_NAMES[11], s.flip_skill);
        row.put(SKILL_COLUMN_NAMES[12], s.initiative);
        row.put(SKILL_COLUMN_NAMES[13], s.next_chain);
        row.put(SKILL_COLUMN_NAMES[14], s.prev_chain);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(SKILL_TABLE_NAME, null, row);
        db.close();
    }

    public void addTrait(Trait s){
        ContentValues row = new ContentValues();
        row.put(TRAIT_COLUMN_NAMES[0], s.trait_id);
        row.put(TRAIT_COLUMN_NAMES[1], s.name);
        row.put(TRAIT_COLUMN_NAMES[2], s.description);
        row.put(TRAIT_COLUMN_NAMES[3], s.spec_id);
        row.put(TRAIT_COLUMN_NAMES[4], s.tier);
        row.put(TRAIT_COLUMN_NAMES[5], s.slot);
        row.put(TRAIT_COLUMN_NAMES[6], s.icon);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TRAIT_TABLE_NAME, null, row);
        db.close();
    }

    public void addSkillProf(SkillProf s){
        ContentValues row = new ContentValues();
        row.put(SKILL_PROF_COLUMN_NAMES[0], s.skill_id);
        row.put(SKILL_PROF_COLUMN_NAMES[1], s.elementalist);
        row.put(SKILL_PROF_COLUMN_NAMES[2], s.ranger);
        row.put(SKILL_PROF_COLUMN_NAMES[3], s.necromancer);
        row.put(SKILL_PROF_COLUMN_NAMES[4], s.ranger);
        row.put(SKILL_PROF_COLUMN_NAMES[5], s.thief);
        row.put(SKILL_PROF_COLUMN_NAMES[6], s.engineer);
        row.put(SKILL_PROF_COLUMN_NAMES[7], s.warrior);
        row.put(SKILL_PROF_COLUMN_NAMES[8], s.guardian);
        row.put(SKILL_PROF_COLUMN_NAMES[9], s.revenant);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(SKILL_PROF_TABLE_NAME, null, row);
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
