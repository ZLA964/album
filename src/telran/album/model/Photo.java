package telran.album.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Photo implements Comparable<Photo>{
    private int albumId;
    private  int photoId;
    private String title;
    private String url;
    private LocalDateTime date;

    public Photo(int albumId, int photoId, String title, String url, LocalDateTime date) {
        this.albumId = albumId;
        this.photoId = photoId;
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getDate() {
        return date;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Photo photo = (Photo) o;
//        return albumId == photo.albumId && photoId == photo.photoId;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(albumId, photoId);
//    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo photo)) return false;

        return albumId == photo.albumId && photoId == photo.photoId;
    }

    @Override
    public int hashCode() {
        int result = albumId;
        result = 31 * result + photoId;
        return result;
    }

    @Override
    public int compareTo(Photo o) {
        return this.date.compareTo(o.date);
    }
}
