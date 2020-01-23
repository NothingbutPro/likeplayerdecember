package com.ics.likeplayer.Model;

import java.io.Serializable;

public class AllVideos implements Serializable {
    String Name;
    int Length;
    String Thmb;
    String Path;
    String Artist;
    String Album;
    String RooDirName;
    public String getRooDirName() {
        return RooDirName;
    }

    public void setRooDirName(String rooDirName) {
        RooDirName = rooDirName;
    }



    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    String Year;

    public AllVideos(String name, int length, String thmb, String Path,String RooDirName) {
        this.Name = name;
        this.Length = length;
        this.Thmb = thmb;
        this.Path = Path;
        this.RooDirName = RooDirName;
    }

    public AllVideos(String name, int length, String thmb, String Path, String artist, String album,String year) {
        this.Name = name;
        this.Length = length;
        this.Thmb = thmb;
        this.Path = Path;
        this.Artist = artist;
        this.Album = album;
        this.Year = year;
    }
    public String getArtist() {
        return Artist;
    }


    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String album) {
        Album = album;
    }
    public void setArtist(String artist) {
        Artist = artist;
    }
    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this. Name = name;
    }

    public int getLength() {
        return Length;
    }

    public void setLength(int length) {
        this.Length = length;
    }

    public String getThmb() {
        return Thmb;
    }

    public void setThmb(String thmb) {
        this.Thmb = thmb;
    }
}
