package com.ics.likeplayer.Database.Model;

public class Database_players_play {
    public static final String PLAYLIST_TABLE_NAME = "PlayList";
    public static final String PLAYLIST_FAVORITES = "Favorites";
    public static final String SONG_TABLE_NAME = "PlayList_Songs";

    public static final String SONG_COLUMN_ID = "song_list_id";
    public static final String COLUMN_SONGNAME = "song_name";
    public static final String COLUMN_URL = "song_url";
    public static final String COLUMN_TIME = "song_time";
//    public static final String COLUMN_TYPE = "row_type";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    //++++++++++++++++++++++Playlist Songs++++++++++++++++++++++++++++
//    public static final String COLUMN_PLAYLIST_ID = "play_list_id";
    public static final String COLUMN_PLAYLIST_ID = "play_id";
    public static final String COLUMN_PLAYLIST_NAME = "play_list_type";
    public static final String COLUMN_PLAYLIST_URL = "playlist_url";
    public static final String COLUMN_PLAYLIST_TYPE = "playlist_type";
    public static final String COLUMN_PLAYLIST_NO_OF_SONGS = "playlist_no_of_songs";
    //++++++++++++++++++++++++++Playlist variable++++++++++++++
    private int play_id;
    private String play_list_type;
    private String play_list_name;
    private String playlist_url;
    private int playlist_no_of_songs;
    private String play_list_timestamp;
//    private String play_list_type;
    //+++++++++++++++++++++++++++++++For song of playlist++++++++++++++++++
    //++++++++++++for SONG++++++++++++++++
    private  int song_list_id;
    private  String song_name;
    private  String song_url;
    private  String song_time;
    private  String timestamp;
    //++++++++++++++++++++++++++++++++++++++++++++++
    // Create table SQL query
    public static final String CREATE_PLAY_LIST_TABLE =
            "CREATE TABLE " + PLAYLIST_TABLE_NAME + "("
                    +  COLUMN_PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PLAYLIST_URL + " TEXT,"
                    + COLUMN_PLAYLIST_NAME + " VARCHAR,"
                    + COLUMN_PLAYLIST_NO_OF_SONGS + " INT,"
                    + COLUMN_PLAYLIST_TYPE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
    public static final String CREATE_FAVORITE_TABLE =
            "CREATE TABLE " + PLAYLIST_FAVORITES + "("
                    +  COLUMN_PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PLAYLIST_URL + " TEXT,"
                    + COLUMN_PLAYLIST_NAME + " VARCHAR,"
                    + COLUMN_PLAYLIST_NO_OF_SONGS + " INT,"
                    + COLUMN_PLAYLIST_TYPE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
//                public static final String CREATE_FAVORITE_TABLE =
//                    "CREATE TABLE " + PLAYLIST_FAVORITES + "("
//                    +  COLUMN_PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                    + COLUMN_PLAYLIST_URL + " TEXT,"
//                    + COLUMN_PLAYLIST_NAME + " VARCHAR,"
//                    + COLUMN_PLAYLIST_NO_OF_SONGS + " INT,"
//                    + COLUMN_PLAYLIST_TYPE + " TEXT,"
//                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
//                    + ")";


    // Create table SQL query
    public static final String CREATE_PLAYLIST_SONG_TABLE =
            "CREATE TABLE " + SONG_TABLE_NAME + "("
                    + SONG_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SONGNAME + " VARCHAR,"
                    + COLUMN_URL + " TEXT,"
                    + COLUMN_TIME + " TEXT,"
                    + COLUMN_PLAYLIST_NAME + " TEXT,"
                    + COLUMN_PLAYLIST_ID + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Database_players_play(int play_id,String play_list_name ,String play_list_type, String playlist_url, int playlist_no_of_songs, String play_list_timestamp) {
        this.play_id = play_id;
        this.play_list_type = play_list_type;
        this.playlist_url = playlist_url;
        this.playlist_no_of_songs = playlist_no_of_songs;
        this.play_list_timestamp = play_list_timestamp;
        this.play_list_name = play_list_name;
    }

    public Database_players_play(int play_id,int song_list_id,String play_list_name , String song_name, String song_url, String song_time, String timestamp) {
        this.play_id = play_id;
        this.play_list_name = play_list_name;
        this.song_list_id = song_list_id;
        this.song_name = song_name;
        this.song_url = song_url;
        this.song_time = song_time;
        this.timestamp = timestamp;
    }

    public int getPlay_id() {
        return play_id;
    }

    public void setPlay_id(int play_id) {
        this.play_id = play_id;
    }

    public String getPlay_list_type() {
        return play_list_type;
    }

    public void setPlay_list_type(String play_list_type) {
        this.play_list_type = play_list_type;
    }

    public String getPlaylist_url() {
        return playlist_url;
    }

    public void setPlaylist_url(String playlist_url) {
        this.playlist_url = playlist_url;
    }

    public int getPlaylist_no_of_songs() {
        return playlist_no_of_songs;
    }

    public String getPlay_list_name() {
        return play_list_name;
    }

    public void setPlay_list_name(String play_list_name) {
        this.play_list_name = play_list_name;
    }

    public void setPlaylist_no_of_songs(int playlist_no_of_songs) {
        this.playlist_no_of_songs = playlist_no_of_songs;
    }

    public String getPlay_list_timestamp() {
        return play_list_timestamp;
    }

    public void setPlay_list_timestamp(String play_list_timestamp) {
        this.play_list_timestamp = play_list_timestamp;
    }

    public int getSong_list_id() {
        return song_list_id;
    }

    public void setSong_list_id(int song_list_id) {
        this.song_list_id = song_list_id;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }

    public String getSong_time() {
        return song_time;
    }

    public void setSong_time(String song_time) {
        this.song_time = song_time;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}