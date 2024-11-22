package telran.album.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.album.dao.Album;
import telran.album.dao.AlbumImpl;
import telran.album.model.Photo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumImplTest {
    private final LocalDateTime now = LocalDateTime.now();
    private final int capacity = 6;
    private Album album;
    private Album album2;
    private Photo  newPhoto;
    private Photo[] photos;
    private final Comparator<Photo> comparatorT = (p1,p2) -> {
       int res = Integer.compare(p1.getAlbumId(), p2.getAlbumId());
       return res != 0 ? res : Integer.compare(p1.getPhotoId(),p2.getPhotoId());
    };

    @BeforeEach
    void setUp() {
        // Инициализация альбомов и фото для тестов
        photos = new Photo[capacity];
        album = new AlbumImpl(capacity); // предположительно, реализация интерфейса Album
        album2 = new AlbumImpl(capacity);

        // Инициализация фото с разными параметрами
        photos[0] = new Photo(1, 1, "Title1", "url1", now.minusDays(7));
        photos[1] = new Photo(1, 2, "Title2", "url2", LocalDate.now().minusDays(5).atStartOfDay());
        System.out.println(now.minusDays(6));
        photos[2] = new Photo(1, 3, "Title3", "url3", LocalDate.now().minusDays(5).atStartOfDay());
        photos[3] = new Photo(2, 1, "Title1", "url1", now.minusDays(5));
        System.out.println(now.minusDays(4));
        photos[4] = new Photo(2, 4, "Title4", "url4", now.minusDays(5));
        System.out.println(now.minusDays(3));
        photos[5] = new Photo(1, 4, "Title4", "url4", now.minusDays(2));

        newPhoto = new Photo(1, 5, "New Photo", "url5", now.minusDays(1));

        for (int i = 0; i < photos.length - 1; i++) {
            album.addPhoto(photos[i]);
        }
        // Добавление фото в альбомы
        album2.addPhoto(photos[4]);
        album2.addPhoto(photos[5]);
        album2.addPhoto(photos[3]);
        album2.addPhoto(photos[2]);
    }

    @Test
    void testAddPhoto() {
        assertFalse(album.addPhoto(null));
        assertFalse(album.addPhoto(photos[2]), "фото уже есть");
        assertTrue(album.addPhoto(photos[5]), "Фото должно успешно добавиться в альбом");
        assertEquals(6, album.size(), "Размер альбома должен увеличиться до 4");
        assertFalse(album.addPhoto(newPhoto), "альюом уже полон");
    }

    @Test
    void testRemovePhoto() {
        assertTrue(album.removePhoto(1, 1), "Фото должно быть удалено из альбома");
        assertEquals(4, album.size(), "Размер альбома должен уменьшиться до 2");
        assertFalse(album.removePhoto(1,1));
        assertTrue(album.removePhoto(2, 1), "Фото должно быть удалено из альбома");
        assertTrue(album.removePhoto(3, 1), "Фото должно быть удалено из альбома");
        assertTrue(album.removePhoto(1, 2), "Фото должно быть удалено из альбома");
        assertTrue(album.removePhoto(4, 2), "Фото должно быть удалено из альбома");
        assertEquals(0, album.size(), "Размер альбома должен уменьшиться до 2");
        assertFalse(album.removePhoto(4,1),"альбом уже пустой");
    }

    @Test
    void testUpdatePhoto() {
        assertTrue(album.updatePhoto(1, 1, "newUrl"), "Фото должно быть обновлено");
        assertEquals("newUrl", album.getPhotoFromAlbum(1, 1).getUrl(), "URL фотографии должен обновиться");
        assertFalse(album.updatePhoto(1,4, "newUrl4"), "фото нет в альюоме");
    }

    @Test
    void testGetPhotoFromAlbum() {
        assertNotNull(album.getPhotoFromAlbum(1, 1), "Фото с ID 1 должно существовать в альбоме");
        assertNull(album.getPhotoFromAlbum(99, 1), "Фото с несуществующим ID должно возвращать null");
        assertEquals(photos[1], album.getPhotoFromAlbum(2,1));
    }

    @Test
    void testGetAllPhotoFromAlbum() {
        Photo[] actual = album.getAllPhotoFromAlbum(2);
        assertEquals(2, actual.length, "Второй альбом должен содержать 2 фотографии");
        Arrays.sort(actual, comparatorT);
        Photo[] expected = { photos[3], photos[4] };
        assertArrayEquals( expected , actual);

    }

    @Test
    void testGetPhotoBetweenDate() {
        LocalDate dateFrom = now.minusDays(5).toLocalDate() ;
        LocalDate dateTo = now.minusDays(3).toLocalDate() ;
        Photo[] expectedPhotos = new Photo[]{photos[1], photos[2], photos[3], photos[4]};
        System.out.println("--test--");
        System.out.println(dateFrom);
        System.out.println(dateTo);
        Photo[] actualPhotos = album.getPhotoBetweenDate(dateFrom, dateTo);
        Arrays.sort(actualPhotos, comparatorT);
        assertArrayEquals(expectedPhotos, actualPhotos, "Массив фотографий должен совпадать с ожидаемым");
    }

    @Test
    void testSize() {
        assertEquals(capacity-1, album.size(), "Первый альбом должен содержать 3 фотографии");
        assertEquals(4, album2.size(), "Второй альбом должен содержать 4 фотографии");
    }



    @Test
    void testSortingByDateAfterAddAndRemove() {
        int albumId = 1; // Проверяем сортировку для конкретного альбома (например, albumId=1)

        // Проверяем, что фотографии из альбома отсортированы по дате в начале
        Photo[] initialPhotos = album.getAllPhotoFromAlbum(albumId);
        Photo[] sortedPhotosByDate = Arrays.copyOf(initialPhotos, initialPhotos.length);
        Arrays.sort(sortedPhotosByDate, Comparator.comparing(Photo::getDate));
        assertArrayEquals(sortedPhotosByDate, initialPhotos, "Фотографии в альбоме должны быть отсортированы по дате в начале");

        // Добавляем новую фотографию с другой датой
        Photo photoToAdd = new Photo(albumId, 5, "Added Photo", "url5", now.minusDays(1));
        album.addPhoto(photoToAdd);
        Photo[] afterAdd = album.getAllPhotoFromAlbum(albumId);
        Photo[] expectedAfterAdd = Arrays.copyOf(afterAdd, afterAdd.length);
        Arrays.sort(expectedAfterAdd, Comparator.comparing(Photo::getDate));
        assertArrayEquals(expectedAfterAdd, afterAdd, "Фотографии в альбоме должны быть отсортированы по дате после добавления");

        // Удаляем фотографию
        assertTrue(album.removePhoto(albumId, 2), "Фотография с albumId=1 и photoId=2 должна быть удалена");
        Photo[] afterRemove = album.getAllPhotoFromAlbum(albumId);
        Photo[] expectedAfterRemove = Arrays.copyOf(afterRemove, afterRemove.length);
        Arrays.sort(expectedAfterRemove, Comparator.comparing(Photo::getDate));
        assertArrayEquals(expectedAfterRemove, afterRemove, "Фотографии в альбоме должны быть отсортированы по дате после удаления");
    }



}



