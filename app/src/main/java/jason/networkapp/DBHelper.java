package jason.networkapp;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

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

    private RemoteDBHelper remoteDBHelper;

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
        remoteDBHelper = new RemoteDBHelper(parentContext);
        specQuery = "CREATE TABLE Specializations (spec_id INTEGER, name TEXT, elite INTEGER, icon TEXT, background TEXT, profession TEXT)";
        skillQuery = "CREATE TABLE Skills (skill_id INTEGER, name TEXT, description TEXT, type TEXT, weapon_type TEXT, slot TEXT, category TEXT, attunement TEXT," +
                                    " toolbelt_skill TEXT, cost INTEGER, dual_wield TEXT, flip_skill INTEGER, initiative INTEGER, next_chain INTEGER, prev_chain INTEGER)";
        traitQuery = "CREATE TABLE Traits (trait_id INTEGER, name TEXT, description TEXT, spec_id INTEGER, tier INTEGER, slot TEXT, icon TEXT)";
        skillProfQuery = "CREATE TABLE Skill_Prof (skill_id INTEGER, elementalist INTEGER, mesmer INTEGER, necromancer INTEGER, ranger INTEGER, thief INTEGER, engineer INTEGER," +
                " warrior INTEGER, guardian INTEGER, revenant INTEGER)";
        ListObject data = remoteDBHelper.intialInsert();
        db.execSQL(specQuery);
        db.execSQL(skillQuery);
        db.execSQL(traitQuery);
        db.execSQL(skillProfQuery);
        //Log.e("all the data", "" + data.spec_list.get(0).name);

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

    public void update(String table, Spec sp, Skill sk, Trait tr, SkillProf skp) {
        ContentValues row = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("updating", " update called");

        if (sp != null) {
            row.put(SPEC_COLUMN_NAMES[0], sp.spec_id);
            row.put(SPEC_COLUMN_NAMES[1], sp.name);
            row.put(SPEC_COLUMN_NAMES[2], sp.elite);
            row.put(SPEC_COLUMN_NAMES[3], sp.icon);
            row.put(SPEC_COLUMN_NAMES[4], sp.background);
            row.put(SPEC_COLUMN_NAMES[5], sp.profession);

            Log.d("updating spec table", "" + sk);
            db.update(table, row, "spec_id = " + sp.spec_id, null);
            db.close();
        }
        else if (sk != null){
            row.put(SKILL_COLUMN_NAMES[0], sk.skill_id);
            row.put(SKILL_COLUMN_NAMES[1], sk.name);
            row.put(SKILL_COLUMN_NAMES[2], sk.description);
            row.put(SKILL_COLUMN_NAMES[3], sk.type);
            row.put(SKILL_COLUMN_NAMES[4], sk.weapon_type);
            row.put(SKILL_COLUMN_NAMES[5], sk.slot);
            row.put(SKILL_COLUMN_NAMES[6], sk.category);
            row.put(SKILL_COLUMN_NAMES[7], sk.attunement);
            row.put(SKILL_COLUMN_NAMES[8], sk.toolbelt_skill);
            row.put(SKILL_COLUMN_NAMES[9], sk.cost);
            row.put(SKILL_COLUMN_NAMES[10], sk.dual_wield);
            row.put(SKILL_COLUMN_NAMES[11], sk.flip_skill);
            row.put(SKILL_COLUMN_NAMES[12], sk.initiative);
            row.put(SKILL_COLUMN_NAMES[13], sk.next_chain);
            row.put(SKILL_COLUMN_NAMES[14], sk.prev_chain);

            db.update(table, row, "skill_id = " + sk.skill_id, null);
            db.close();
        }
        else if (tr != null){
            row.put(TRAIT_COLUMN_NAMES[0], tr.trait_id);
            row.put(TRAIT_COLUMN_NAMES[1], tr.name);
            row.put(TRAIT_COLUMN_NAMES[2], tr.description);
            row.put(TRAIT_COLUMN_NAMES[3], tr.spec_id);
            row.put(TRAIT_COLUMN_NAMES[4], tr.tier);
            row.put(TRAIT_COLUMN_NAMES[5], tr.slot);
            row.put(TRAIT_COLUMN_NAMES[6], tr.icon);

            db.update(table, row, "trait_id = " + tr.trait_id, null);
            db.close();
        }
        else if (skp != null){
            row.put(SKILL_PROF_COLUMN_NAMES[0], skp.skill_id);
            row.put(SKILL_PROF_COLUMN_NAMES[1], skp.elementalist);
            row.put(SKILL_PROF_COLUMN_NAMES[2], skp.ranger);
            row.put(SKILL_PROF_COLUMN_NAMES[3], skp.necromancer);
            row.put(SKILL_PROF_COLUMN_NAMES[4], skp.ranger);
            row.put(SKILL_PROF_COLUMN_NAMES[5], skp.thief);
            row.put(SKILL_PROF_COLUMN_NAMES[6], skp.engineer);
            row.put(SKILL_PROF_COLUMN_NAMES[7], skp.warrior);
            row.put(SKILL_PROF_COLUMN_NAMES[8], skp.guardian);
            row.put(SKILL_PROF_COLUMN_NAMES[9], skp.revenant);

            db.update(table, row, "skill_id = " + skp.skill_id, null);
            db.close();
        }
    }

    public void update(String table, Spec sp) {
        update(table, sp, null, null, null);
    }

    public void update(String table, Skill sk) {
        update(table, null, sk, null, null);
    }

    public void update(String table, Trait tr) {
        update(table, null, null, tr, null);
    }

    public void update(String table, SkillProf skp) {
        update(table, null, null, null, skp);
    }

    public void delete(String table, String key_column, String id) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(table, key_column + "= ?", new String[]{id});
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

    public String updateDB(String date) {
        String lastUpdate = date;
        String updateDate = "";
        remoteDBHelper = new RemoteDBHelper(parentContext);
        ArrayList<UpdateData> updateData = remoteDBHelper.update(lastUpdate);

        for (UpdateData values : updateData) {
            Log.d("operation: ", values.operation);
            Log.d("table: ", values.table_name);
            switch (values.operation) {
                case "insert":
                    switch (values.table_name) {
                        case SPEC_TABLE_NAME:
                            addSpec(values.spec);
                            break;
                        case SKILL_TABLE_NAME:
                            addSkill(values.skill);
                            break;
                        case TRAIT_TABLE_NAME:
                            addTrait(values.trait);
                            break;
                        case SKILL_PROF_TABLE_NAME:
                            addSkillProf(values.skill_prof);
                            break;
                    }
                    break;
                case "update":
                    switch (values.table_name) {
                        case SPEC_TABLE_NAME:
                            Log.d("update table: ", SPEC_TABLE_NAME + values.table_name);
                            update(values.table_name, values.spec);
                            break;
                        case SKILL_TABLE_NAME:
                            Log.d("update table: ", SKILL_TABLE_NAME + values.table_name);
                            update(values.table_name, values.skill);
                            break;
                        case TRAIT_TABLE_NAME:
                            Log.d("update table: ", TRAIT_TABLE_NAME + values.table_name);
                            update(values.table_name, values.trait);
                            break;
                        case SKILL_PROF_TABLE_NAME:
                            Log.d("update table: ", SKILL_PROF_TABLE_NAME + values.table_name);
                            update(values.table_name, values.skill_prof);
                            break;
                    }
                    break;
                case "delete":
                    switch (values.table_name) {
                        case SPEC_TABLE_NAME:
                            delete(values.table_name, "spec_id", values.row_id);
                            break;
                        case SKILL_TABLE_NAME:
                            delete(values.table_name, "skill_id", values.row_id);
                            break;
                        case TRAIT_TABLE_NAME:
                            delete(values.table_name, "trait_id", values.row_id);
                            break;
                        case SKILL_PROF_TABLE_NAME:
                            delete(values.table_name, "skill_id", values.row_id);
                            break;
                    }
                    break;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        updateDate = sdf.format(new Date());

//        updateDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Log.e("old update time", date);
        Log.e("new update time", updateDate);
        return updateDate;
    }

    public AllProfessionData getProfessionData(String profession) {
        AllProfessionData result = new AllProfessionData();
        SQLiteDatabase db = this.getReadableDatabase();
        ProfessionData pd;

        String specs = "Select * from Specializations where profession = ?";

        String traits = "select * from Traits where spec_id = ?";

        String skill_prof = "select Skills.* from Skills " +
                "left join Skill_Prof on Skills.skill_id = Skill_Prof.skill_id " +
                "where Skill_Prof." + profession + " = 1 " +
                "order by Skills.type asc";


        ArrayList<Integer> prof_skills =  new ArrayList<>();

        Cursor cursor = db.rawQuery(specs, new String[] {profession});
        //cursor.moveToFirst();
        while (cursor.moveToNext()) {
            pd = new ProfessionData();
            pd.specs = new Spec();
            pd.specs.spec_id = cursor.getInt(cursor.getColumnIndex(SPEC_COLUMN_NAMES[0]));
            pd.specs.name = cursor.getString(cursor.getColumnIndex(SPEC_COLUMN_NAMES[1]));
            pd.specs.elite = cursor.getInt(cursor.getColumnIndex(SPEC_COLUMN_NAMES[2]));
            pd.specs.icon = cursor.getString(cursor.getColumnIndex(SPEC_COLUMN_NAMES[3]));
            pd.specs.background = cursor.getString(cursor.getColumnIndex(SPEC_COLUMN_NAMES[4]));
            pd.specs.profession = cursor.getString(cursor.getColumnIndex(SPEC_COLUMN_NAMES[5]));

            Cursor cursor2 = db.rawQuery(traits, new String[] {String.valueOf(pd.specs.spec_id)});
            //cursor2.moveToFirst();
            while (cursor2.moveToNext()) {
                Trait tr = new Trait();
                tr.trait_id = cursor2.getInt(cursor2.getColumnIndex(TRAIT_COLUMN_NAMES[0]));
                tr.name = cursor2.getString(cursor2.getColumnIndex(TRAIT_COLUMN_NAMES[1]));
                tr.description = cursor2.getString(cursor2.getColumnIndex(TRAIT_COLUMN_NAMES[2]));
                tr.tier = cursor2.getInt(cursor2.getColumnIndex(TRAIT_COLUMN_NAMES[4]));
                tr.slot = cursor2.getString(cursor2.getColumnIndex(TRAIT_COLUMN_NAMES[5]));
                tr.icon = cursor2.getString(cursor2.getColumnIndex(TRAIT_COLUMN_NAMES[6]));
                pd.traits.add(tr);
            }
            cursor2.close();
            result.spec_traits.add(pd);

        }
        cursor.close();

        Cursor cursor3 = db.rawQuery(skill_prof, null);
        //cursor3.moveToFirst();
        while (cursor3.moveToNext()) {
            Skill sk = new Skill();
            sk.skill_id = cursor3.getInt(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[0]));
            sk.name = cursor3.getString(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[1]));
            sk.description = cursor3.getString(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[2]));
            sk.type = cursor3.getString(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[3]));
            sk.weapon_type = cursor3.getString(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[4]));
            sk.slot = cursor3.getString(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[5]));
            sk.category = cursor3.getString(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[6]));
            sk.attunement = cursor3.getString(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[7]));
            sk.toolbelt_skill = cursor3.getString(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[8]));
            sk.cost = cursor3.getInt(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[9]));
            sk.dual_wield = cursor3.getString(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[10]));
            sk.flip_skill = cursor3.getInt(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[11]));
            sk.initiative = cursor3.getInt(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[12]));
            sk.next_chain = cursor3.getInt(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[13]));
            sk.prev_chain = cursor3.getInt(cursor3.getColumnIndex(SKILL_COLUMN_NAMES[14]));
            result.skills.add(sk);
        }
        cursor3.close();

        return result;
    }
}
