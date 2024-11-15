package telran.album.dao;

import telran.album.model.Photo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Predicate;

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
        int newIndex = 0;
        if (size != 0) {
            newIndex = Arrays.binarySearch(photos, 0, size, photo);
        }
        if (newIndex < 0) {
            newIndex = -1 - newIndex;
        }
        System.arraycopy(photos, newIndex, photos, newIndex + 1, size - newIndex);
        photos[newIndex] = photo;
        size++;
        return true;
    }

    private boolean isPhoto(Photo newPhoto) {
        for (int i = 0; i < size; i++) {
            if (newPhoto.equals(photos[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removePhoto(int photoId, int albumId) {
        int indexPhotoForRemove = indexPhoto(photoId, albumId);
        if (indexPhotoForRemove < 0) {
            return false;
        }
        System.arraycopy(photos, indexPhotoForRemove + 1, photos, indexPhotoForRemove,
                size - indexPhotoForRemove - 1);
        photos[--size] = null;
        return true;
    }

    private int indexPhoto(int photoId, int albumId) {
        for (int i = 0; i < size; i++) {
            if (photos[i].getAlbumId() == albumId && photos[i].getPhotoId() == photoId) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean updatePhoto(int photoId, int albumId, String newUrl) {
        Photo photo = getPhotoFromAlbum(photoId, albumId);
        if (photo == null || newUrl == null || newUrl.isBlank()) {
            return false;
        }
        photo.setUrl(newUrl);
        return true;
    }

    @Override
    public Photo getPhotoFromAlbum(int photoId, int albumId) {
        for (int i = 0; i < size; i++) {
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
            if (albumId == thisPotoAlbumId) {
                photosInAlbum[inAlbum++] = this.photos[i];
            }
        }
        return Arrays.copyOf(photosInAlbum, inAlbum);
    }

    @Override
    public Photo[] getPhotoBetweenDate(LocalDate dateFrom, LocalDate dateTo) {
        Photo[] photosBetween = new Photo[size];
        int inAlbum = 0;
        for (int i = 0; i < size; i++) {
            LocalDate lacalDatePhoto = this.photos[i].getDate().toLocalDate();
            if ((lacalDatePhoto.isEqual(dateFrom) || dateFrom.isBefore(lacalDatePhoto))
                    && dateTo.isAfter(lacalDatePhoto)) {
                photosBetween[inAlbum++] = this.photos[i];
            }
        }
        return Arrays.copyOf(photosBetween, inAlbum);
    }

    @Override
    public int size() {
        return size;
    }

    private int[] findIndexPhotosByPredicate(Predicate<Photo> predicate) {
        int iTrue = 0;
        int[] indexes = new int[size];
        for (int index = 0; index < size; index++) {
            if (predicate.test(photos[index])) {
                indexes[iTrue++] = index;
            }
        }
        return Arrays.copyOf(indexes, iTrue);
    }

    private Photo[] photosByPredicateThrowIndex(Predicate<Photo> predicate) {
        int iTrue = 0;
        int[] indexes = new int[size];
        for (int index = 0; index < size; index++) {
            if (predicate.test(photos[index])) {
                indexes[iTrue++] = index;
            }
        }
        Photo[] result = new Photo[iTrue];
        for (int i = 0; i < iTrue; i++) {
            result[i] = this.photos[indexes[i]];
        }
        return result;
    }


    private Photo[] photosByPredicate(Predicate<Photo> predicate) {
        Photo[] result = new Photo[size];
        int iTrue = 0;
        for (int index = 0; index < size; index++) {
            if (predicate.test(photos[index])) {
                result[iTrue++] = photos[index];
            }
        }
        return Arrays.copyOf(result, iTrue);
    }


}
