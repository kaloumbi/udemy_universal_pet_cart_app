package com.doyoucode.universal_pet_car.service.photo;

import com.doyoucode.universal_pet_car.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface IPhotoService {

    Photo savePhoto(MultipartFile file, Long userId) throws IOException, SQLException;

    Photo getPhotoById(Long id);

    void deletePhoto(Long photoId, Long userId) throws SQLException ;

    Photo updatePhoto(Long id, MultipartFile file) throws SQLException, IOException;

    byte[] getImageData(Long id) throws SQLException;

}
