package com.ics.likeplayer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.ics.likeplayer.Database.Model.Database_players_play;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ravi on 15/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "playlist_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Database_players_play.CREATE_PLAY_LIST_TABLE);
        db.execSQL(Database_players_play.CREATE_FAVORITE_TABLE);
        db.execSQL(Database_players_play.CREATE_PLAYLIST_SONG_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Database_players_play.PLAYLIST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Database_players_play.PLAYLIST_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + Database_players_play.SONG_TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Inserting to the Playlist+++++++++++++++++++
    public long insertPlaySong(Context context, int play_list_id, String play_list_name, String song_name, String song_url , String song_time) {
        // get writable database as we want to write data
        Toast.makeText(context, "Play song inserting", Toast.LENGTH_SHORT).show();
        SQLiteDatabase db = this.getWritableDatabase();
        long id =0;
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Database_players_play.COLUMN_PLAYLIST_ID, play_list_id);
        values.put(Database_players_play.COLUMN_PLAYLIST_NAME, play_list_name);
        values.put(Database_players_play.COLUMN_SONGNAME, song_name);
        values.put(Database_players_play.COLUMN_URL, song_url);
        values.put(Database_players_play.COLUMN_TIME, song_time);
//        values.put(Database_players_play.COLUMN_TIME, song_time);


        // insert row
        id = db.insert(Database_players_play.SONG_TABLE_NAME, null, values);
//        db.close();
        if(id !=0)
        {
            Toast.makeText(context, "not zero id is"+id, Toast.LENGTH_SHORT).show();
            return  id;
        }else {
            Toast.makeText(context, "zero id is"+id, Toast.LENGTH_SHORT).show();
            return id;
        }
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++INSERT SONG TO the Favorites++++++++++++++++++++++
    public long insertPlaySongTOPLAYLIST(Context context, int play_list_id, String play_list_name, String song_name, String song_url , String song_time) {
        // get writable database as we want to write data
        Toast.makeText(context, "Play song inserting", Toast.LENGTH_SHORT).show();
        SQLiteDatabase db = this.getWritableDatabase();
        long id =0;
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Database_players_play.COLUMN_PLAYLIST_ID, play_list_id);
        values.put(Database_players_play.COLUMN_PLAYLIST_NAME, play_list_name);
        values.put(Database_players_play.COLUMN_SONGNAME, song_name);
        values.put(Database_players_play.COLUMN_URL, song_url);
        values.put(Database_players_play.COLUMN_TIME, song_time);
//        values.put(Database_players_play.COLUMN_TIME, song_time);


        // insert row
        id = db.insert(Database_players_play.SONG_TABLE_NAME, null, values);
//        db.close();
        if(id !=0)
        {
            Toast.makeText(context, "not zero id is"+id, Toast.LENGTH_SHORT).show();
            return  id;
        }else {
            Toast.makeText(context, "zero id is"+id, Toast.LENGTH_SHORT).show();
            return id;
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //+++++++++++++++++++++++++++++++++++++++INSERT SONG FOR EXISTING++++++++++++++++++++++++++++++++++++
    public long insertintoExistingPlay(Context context, int play_list_id, String play_list_name, String song_name, String song_url , String song_time) {
        // get writable database as we want to write data
//        Toast.makeText(context, "Play song inserting", Toast.LENGTH_SHORT).show();
        SQLiteDatabase db = this.getWritableDatabase();
        long id =0;
       Boolean done =false;
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Database_players_play.COLUMN_PLAYLIST_ID, play_list_id);
        values.put(Database_players_play.COLUMN_PLAYLIST_NAME, play_list_name);
        values.put(Database_players_play.COLUMN_SONGNAME, song_name);
        values.put(Database_players_play.COLUMN_URL, song_url);
        values.put(Database_players_play.COLUMN_TIME, song_time);
//        values.put(Database_players_play.COLUMN_TIME, song_time);
//        ArrayList<Database_players_play> checkedPlayers_plays = checkForsonginPlay(play_list_id);
        Boolean checked = checkForsonginPlay(play_list_id ,song_name);
        if(checked)
        {
            done =false;
            Log.e("Not equal", "Song exist");

        }else {
            id = db.insert(Database_players_play.SONG_TABLE_NAME, null, values);
            done = true;

        }
//        for(int i=0;i<checkedPlayers_plays.size();i++)
//        {
//            if( !checkedPlayers_plays.get(i).getSong_name().equals(song_name)) {
//
//                    id = db.insert(Database_players_play.SONG_TABLE_NAME, null, values);
//                    done = true;
//            }else {
//                done =false;
//                Log.e("Not equal", ""+getAllSOngsbyPlaylist().get(i).getSong_name());
//            }
//        }
        if(done !=false)
        {
            Toast.makeText(context, "Song added", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Song is already in the playlist", Toast.LENGTH_SHORT).show();
        }
        // insert row
//        db.close();
        if(id !=0)
        {
//            Toast.makeText(context, "not zero id is"+id, Toast.LENGTH_SHORT).show();
            return  id;
        }else {
//            Toast.makeText(context, "zero id is"+id, Toast.LENGTH_SHORT).show();
            return id;
        }
    }

    public Boolean checkForsonginPlay(int play_list_id,String song_name) {
        ArrayList<Database_players_play> notes = new ArrayList<>();

        // Select All Query

        String songquery = "SELECT  * FROM " + Database_players_play.SONG_TABLE_NAME + " WHERE "+
                Database_players_play.COLUMN_PLAYLIST_ID  + " = "+play_list_id +" AND "+Database_players_play.COLUMN_SONGNAME +" ==  \""+ song_name+"\" ";
        Log.d("SOng query is" , ""+songquery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor playlistqueryCursor = db.rawQuery(songquery, null);
        Log.d("cursor count" , ""+playlistqueryCursor.getCount());
            if(playlistqueryCursor.getCount() ==0)
            {
                return false;
            }else {
                return true;
            }
//        if (playlistqueryCursor.moveToFirst()) {
//            do {
//                Database_players_play database_players_play_Playlist = new Database_players_play(
//                        playlistqueryCursor.getInt ( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_ID))
//                        , playlistqueryCursor.getInt ( playlistqueryCursor.getColumnIndex(Database_players_play.SONG_COLUMN_ID))
//                        , playlistqueryCursor.getString( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_NAME))
//                        ,playlistqueryCursor.getString ( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME)),
//                        playlistqueryCursor.getString( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME))
//                        ,playlistqueryCursor.getString( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_TIME))
//                        ,playlistqueryCursor.getString( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));
////                note.setId(cursor.getInt(cursor.getColumnIndex(Database_players_play.COLUMN_ID)));
////                note.setNote(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME)));
////                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));
//
//                notes.add(database_players_play_Playlist);
//            } while (playlistqueryCursor.moveToNext());
//        }



    }
    //+++++++++++++++++++++++++++++++++++++++FOR CHECKING FAVORITES+++++++++++++++++++++++++++++++++++
    public Boolean checkForsonginFAV(int play_list_id,String song_name) {
        ArrayList<Database_players_play> notes = new ArrayList<>();

        // Select All Query

        String songquery = "SELECT  * FROM " + Database_players_play.SONG_TABLE_NAME + " WHERE "+
                Database_players_play.COLUMN_PLAYLIST_ID  + " = "+play_list_id +" AND "+Database_players_play.COLUMN_SONGNAME +" ==  \""+ song_name+"\" ";
        Log.d("SOng query is" , ""+songquery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor playlistqueryCursor = db.rawQuery(songquery, null);
        Log.d("cursor count" , ""+playlistqueryCursor.getCount());
        if(playlistqueryCursor.getCount() ==0)
        {
            return false;
        }else {
            return true;
        }

    }

    //+++++++++++++++++++++++++++++++++++++

    //++++++++++++++++++++++++++++++++++++++
    public long insertPlayList(Context context, String play_listname, String play_list_url, String play_list_no_of_songs, String play_list_type) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        long id =0;
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Database_players_play.COLUMN_PLAYLIST_NAME, play_listname);
        values.put(Database_players_play.COLUMN_PLAYLIST_URL, play_list_url);
        values.put(Database_players_play.COLUMN_PLAYLIST_NO_OF_SONGS, play_list_no_of_songs);
        values.put(Database_players_play.COLUMN_PLAYLIST_TYPE, play_list_type);
        // insert row
        ArrayList<Database_players_play> database_players_playslist =  getAllNotesPlaylist();
        if(database_players_playslist.size()==0)
        {
            id = db.insert(Database_players_play.PLAYLIST_TABLE_NAME, null, values);
            // close db connection
            return  id;
        }
        for(int i=0;i<database_players_playslist.size();i++)
        {
            if(database_players_playslist.get(i).getPlay_list_name().equals(play_listname))
            {
                Toast.makeText(context, "Play list"+database_players_playslist.get(i).getPlay_list_name(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "play_listname"+play_listname, Toast.LENGTH_SHORT).show();
                // close db connection
//                    db.close();
                return  id;

            }else {
                id = db.insert(Database_players_play.PLAYLIST_TABLE_NAME, null, values);
                // close db connection
                db.close();
                return  id;
            }
        }
//        db.close();
        return id;
    }
    //+++++++++++++++++++++++++++++++++++++++++++++TO the Favorites_+++++++++++++++++++++++++
    public long insertToFovorite(Context context, String play_listname, String play_list_url, String play_list_no_of_songs, String play_list_type) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        long id =0;
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Database_players_play.COLUMN_PLAYLIST_NAME, play_listname);
        values.put(Database_players_play.COLUMN_PLAYLIST_URL, play_list_url);
        values.put(Database_players_play.COLUMN_PLAYLIST_NO_OF_SONGS, play_list_no_of_songs);
        values.put(Database_players_play.COLUMN_PLAYLIST_TYPE, play_list_type);
        // insert row
        ArrayList<Database_players_play> database_players_playslist =  getAllNotesPlaylist();
        if(database_players_playslist.size()==0)
        {
            id = db.insert(Database_players_play.PLAYLIST_TABLE_NAME, null, values);
            // close db connection
            return  id;
        }
        for(int i=0;i<database_players_playslist.size();i++)
        {
            if(database_players_playslist.get(i).getPlay_list_name().equals(play_listname))
            {
                Toast.makeText(context, "Play list"+database_players_playslist.get(i).getPlay_list_name(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "play_listname"+play_listname, Toast.LENGTH_SHORT).show();
                // close db connection
//                    db.close();
                return  id;

            }else {
                id = db.insert(Database_players_play.PLAYLIST_FAVORITES, null, values);
                // close db connection
                db.close();
                return  id;
            }
        }
//        db.close();
        return id;
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    public ArrayList<Database_players_play> getAllSOngsbyPlaylist() {
        ArrayList<Database_players_play> notes = new ArrayList<>();

        // Select All Query

        String songquery = "SELECT  * FROM " + Database_players_play.SONG_TABLE_NAME + " ORDER BY " +
                Database_players_play.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor playlistqueryCursor = db.rawQuery(songquery, null);


//        Cursor songqueryCursor1 = db.rawQuery(songquery, null);

        // looping through all rows and adding to list
        if (playlistqueryCursor.moveToFirst()) {
            do {
                Database_players_play database_players_play_Playlist = new Database_players_play(
                        playlistqueryCursor.getInt ( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_ID))
                , playlistqueryCursor.getInt ( playlistqueryCursor.getColumnIndex(Database_players_play.SONG_COLUMN_ID))
                , playlistqueryCursor.getString( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_NAME))
                ,playlistqueryCursor.getString ( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME)),
                playlistqueryCursor.getString( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME))
                ,playlistqueryCursor.getString( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_TIME))
                ,playlistqueryCursor.getString( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));
//                note.setId(cursor.getInt(cursor.getColumnIndex(Database_players_play.COLUMN_ID)));
//                note.setNote(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME)));
//                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));

                notes.add(database_players_play_Playlist);
            } while (playlistqueryCursor.moveToNext());
        }

        // close db connection
//        db.close();

        // return notes list
        return notes;
    }
//++++++++++++++++++++++++++++++++++++++++++++++++Check FOr Playlist+++++++++++++++++++++
    public ArrayList<Database_players_play> getAllNotesPlaylist() {
        ArrayList<Database_players_play> notes = new ArrayList<>();

        // Select All Query
        String playlistquery = "SELECT  * FROM " + Database_players_play.PLAYLIST_TABLE_NAME + " ORDER BY " +
                Database_players_play.COLUMN_TIMESTAMP + " DESC";
        String songquery = "SELECT  * FROM " + Database_players_play.SONG_TABLE_NAME + " ORDER BY " +
                Database_players_play.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor playlistqueryCursor = db.rawQuery(playlistquery, null);


//        Cursor songqueryCursor1 = db.rawQuery(songquery, null);

        // looping through all rows and adding to list
        if (playlistqueryCursor.moveToFirst()) {
            do {
                Database_players_play database_players_play_Playlist = new Database_players_play(
                        playlistqueryCursor.getInt ( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_ID) )
                        ,playlistqueryCursor.getString ( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_NAME))
                        ,playlistqueryCursor.getString(playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_TYPE)
                        ),playlistqueryCursor.getString(playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_URL)
                ),playlistqueryCursor.getInt(playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_NO_OF_SONGS)
                ),playlistqueryCursor.getString(playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));
//                note.setId(cursor.getInt(cursor.getColumnIndex(Database_players_play.COLUMN_ID)));
//                note.setNote(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME)));
//                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));

                notes.add(database_players_play_Playlist);
            } while (playlistqueryCursor.moveToNext());
        }

        return notes;
    }
    //++++++++++++++++++++++++++++++++++++++++FOR FAVORITE PLAYLIST+++++++++++++++++++++++++++++++++++
    public ArrayList<Database_players_play> getAllNotesFavorites() {
        ArrayList<Database_players_play> notes = new ArrayList<>();

        // Select All Query
        String playlistquery = "SELECT  * FROM " + Database_players_play.PLAYLIST_TABLE_NAME + " ORDER BY " +
                Database_players_play.COLUMN_TIMESTAMP + " DESC";
        String songquery = "SELECT  * FROM " + Database_players_play.SONG_TABLE_NAME + " ORDER BY " +
                Database_players_play.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor playlistqueryCursor = db.rawQuery(playlistquery, null);


//        Cursor songqueryCursor1 = db.rawQuery(songquery, null);

        // looping through all rows and adding to list
        if (playlistqueryCursor.moveToFirst()) {
            do {
                Database_players_play database_players_play_Playlist = new Database_players_play(
                        playlistqueryCursor.getInt ( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_ID) )
                        ,playlistqueryCursor.getString ( playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_NAME))
                        ,playlistqueryCursor.getString(playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_TYPE)
                ),playlistqueryCursor.getString(playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_URL)
                ),playlistqueryCursor.getInt(playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_NO_OF_SONGS)
                ),playlistqueryCursor.getString(playlistqueryCursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));
//                note.setId(cursor.getInt(cursor.getColumnIndex(Database_players_play.COLUMN_ID)));
//                note.setNote(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME)));
//                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));

                notes.add(database_players_play_Playlist);
            } while (playlistqueryCursor.moveToNext());
        }

        // close db connection
//        db.close();

        // return notes list
        return notes;
    }
    //+++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++For Songs count+++++++++++++++++++++++++
public int getSongsCount() {
    String countQuery = "SELECT  * FROM " + Database_players_play.SONG_TABLE_NAME;
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(countQuery, null);
    int count = cursor.getCount();
    cursor.close();
    // return count
    return count;
}
    //++++++++++++++++++++++
    public int getPlaylistCount() {
        String countQuery = "SELECT  * FROM " + Database_players_play.PLAYLIST_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public int UpdatePlaylistNote(Database_players_play database_players_play)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Database_players_play.COLUMN_SONGNAME, database_players_play.getPlay_list_name());

        // updating row
        return db.update(Database_players_play.PLAYLIST_TABLE_NAME, values, Database_players_play.COLUMN_PLAYLIST_ID + " = ?",
                new String[]{String.valueOf(database_players_play.getPlay_id())});
    }
}
