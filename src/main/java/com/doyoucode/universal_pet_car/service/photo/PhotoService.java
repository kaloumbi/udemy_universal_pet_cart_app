package com.doyoucode.universal_pet_car.service.photo;

import com.doyoucode.universal_pet_car.entity.Photo;
import com.doyoucode.universal_pet_car.entity.User;
import com.doyoucode.universal_pet_car.exceptions.ResourceNotFoundException;
import com.doyoucode.universal_pet_car.repository.PhotoRepo;
import com.doyoucode.universal_pet_car.repository.UserRepo;
import com.doyoucode.universal_pet_car.utils.FeedBackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoService implements IPhotoService {

    private final UserRepo userRepo;

    private final PhotoRepo photoRepo;


    @Override
    public Photo savePhoto(MultipartFile file, Long userId) throws IOException, SQLException {
        Optional<User> theUser = userRepo.findById(userId);

        Photo photo = new Photo();
        if (file != null && !file.isEmpty()){
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes); //convert bytes frontend image to blob
            photo.setImage(photoBlob);
            photo.setFileType(file.getContentType());
            photo.setFileName(file.getOriginalFilename());
        }
        Photo savePhoto = photoRepo.save(photo);
        theUser.ifPresent(user -> {user.setPhoto(savePhoto);}); //on charge la photo à l'utilisateur

        userRepo.save(theUser.get());

        return savePhoto;
    }

    @Override
    public Photo getPhotoById(Long id) {
        return photoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FeedBackMessage.RESOURCE_NOT_FOUND));
    }

    @Transactional
    @Override
    public void deletePhoto(Long id, Long userId) throws SQLException {
        userRepo.findById(userId).ifPresentOrElse(User ::removeUserPhoto, () ->{
            throw new ResourceNotFoundException(FeedBackMessage.RESOURCE_NOT_FOUND);
        });
        photoRepo.findById(id)
                .ifPresentOrElse(photoRepo::delete, ()->{
                    throw new ResourceNotFoundException(FeedBackMessage.RESOURCE_NOT_FOUND);
                });

    }

    @Override
    public Photo updatePhoto(Long id, MultipartFile file) throws IOException, SQLException {
        Photo photo = getPhotoById(id);
        if (photo != null){
            byte[] photoBytes = file.getBytes();
            Blob blobPhoto = new SerialBlob(photoBytes);

            photo.setImage(blobPhoto);
            photo.setFileName(file.getContentType());
            photo.setFileName(file.getOriginalFilename());
            return photoRepo.save(photo);
        }

        throw new ResourceNotFoundException(FeedBackMessage.RESOURCE_NOT_FOUND);
    }

    @Override
    public byte[] getImageData(Long id) throws SQLException {

        Optional<Photo> photo = photoRepo.findById(id);
        if (photo.isPresent()){
            Blob photoBlob = photo.get().getImage();
            int blobLength = (int) photoBlob.length();
            return new byte[blobLength];
        }

        return null;
    }
}
