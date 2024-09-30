package com.example.photobrowser;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {
    private String title;
    private String url;

    public Photo(String title, String url) {
        this.title = title;
        this.url = url;
    }

    protected Photo(Parcel in) {
        title = in.readString();
        url = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
    }
}
