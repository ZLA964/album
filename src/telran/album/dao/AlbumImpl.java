package telran.album.dao;

import telran.album.model.Photo;

import java.time.LocalDate;

public class AlbumImpl implements Album {
    private Photo[] photos;
    private int size;
//    private int indexPhotosId[];
//    private int indexPhotosDate[];

    public AlbumImpl(int capacity) {
        this.photos = new Photo[capacity];
//        this.indexPhotosId = new int[capacity];
//        this.indexPhotosDate = new int[capacity];
    }

    @Override
    public boolean addPhoto(Photo photo) {
        if (this.size == photos.length || photo == null || isPhoto(photo))
            return false;
        photos[this.size] = photo;
        this.size++;
        return true;
    }

    private boolean isPhoto(Photo newPhoto) {
        for (Photo photo : photos) {
            if (newPhoto.equals(photo)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean removePhoto(int photoId, int albumId) {
        return false;
    }

    @Override
    public boolean updatePhoto(int photoId, int albumId, String url) {
        return false;
    }

    @Override
    public Photo getPhotoFromAlbum(int photoId, int albumId) {
        for (Photo photo : photos) {
            if (photo != null && photo.getAlbumId() == albumId && photo.getPhotoId() == photoId) {
                return photo;
            }
        }
        return null;
    }

    @Override
    public Photo[] getAllPhotoFromAlbum(int albumId) {
        return new Photo[0];
    }

    @Override
    public Photo[] getPhotoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {
        return new Photo[0];
    }

    @Override
    public int size() {
        return size;
    }
}
