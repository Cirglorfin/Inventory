package com.example.inventory.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

public class Dependency implements Parcelable, Serializable {
    public static final String TAG = "dependency";

    public Dependency(int id, String shortname, String name, String description, String imageName) {
        this.id = id;
        this.shortname = shortname;
        this.name = name;
        this.description = description;
        this.imageName = imageName;
    }

    protected Dependency(Parcel in) {
        id = in.readInt();
        shortname = in.readString();
        name = in.readString();
        description = in.readString();
        imageName = in.readString();
    }

    public static final Creator<Dependency> CREATOR = new Creator<Dependency>() {
        @Override
        public Dependency createFromParcel(Parcel in) {
            return new Dependency(in);
        }

        @Override
        public Dependency[] newArray(int size) {
            return new Dependency[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String shortname;
    private String name;
    private String description;
    private String imageName;

    public Dependency(String shortname, String name) {
        this.shortname=shortname;
        this.name = name;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }



    public Dependency( String shortname, String name, String description, String imageName) {

        this.shortname = shortname;
        this.name = name;
        this.description = description;
        this.imageName = imageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dependency)) return false;
        Dependency that = (Dependency) o;
        return shortname.equals(that.shortname) && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortname, name);
    }

    public int getId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortname);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageName);
    }
}
