package com.example.lodge.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

public class Server implements Parcelable, Serializable {
    public static final String TAG = "server";
    private int id;
    private String name;
    private String description;
    private String imageName;
    private String privacy;

    public Server(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Server(String name, String privacy) {
        this.name = name;
        this.privacy = privacy;
    }

    public Server(int id,String name, String description, String imageName, String privacy) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        this.privacy = privacy;
    }
    public Server(String name, String description, String imageName, String privacy) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.imageName = imageName;
        this.privacy = privacy;
    }


    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Server(int id, String name, String description, String imageName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageName = imageName;
    }

    protected Server(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        imageName = in.readString();
    }

    public static final Creator<Server> CREATOR = new Creator<Server>() {
        @Override
        public Server createFromParcel(Parcel in) {
            return new Server(in);
        }

        @Override
        public Server[] newArray(int size) {
            return new Server[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }



    public Server(String name) {
        this.name = name;
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



    public Server(String name, String description, String imageName) {
        this.name = name;
        this.description = description;
        this.imageName = imageName;
    }




    public int getId() {
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Server)) return false;
        Server server = (Server) o;
        return getId() == server.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageName);
    }
}
