package telran.album.dao;

import telran.album.model.Photo;

import java.time.LocalDate;
import java.util.Arrays;

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
        for (int i = 0; i < size - 1; i++) {
            if (newPhoto.equals(photos[i])) {
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
    public boolean updatePhoto(int photoId, int albumId, String newUrl) {
        Photo photo = getPhotoFromAlbum(photoId, albumId);
        if (photo == null || newUrl == null || newUrl.isBlank()) {
            return false;
        }
        ;
        photo.setUrl(newUrl);
        return true;
    }

    @Override
    public Photo getPhotoFromAlbum(int photoId, int albumId) {
        for (int i = 0; i < size - 1; i++) {
            if (photos[i].getAlbumId() == albumId && photos[i].getPhotoId() == photoId) {
                return photos[i];
            }
        }
        return null;
    }

    @Override
    public Photo[] getAllPhotoFromAlbum(int albumId) {
        Photo[] photosInAlbum = new Photo[size];
        int inAlbum = 0;
        for (int i = 0; i < size; i++) {
            int thisPotoAlbumId = this.photos[i].getAlbumId();
            if (albumId == thisPotoAlbumId ) {
                photosInAlbum[inAlbum++] = this.photos[i];
            }
        }
        return Arrays.copyOf(photosInAlbum, inAlbum);
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
