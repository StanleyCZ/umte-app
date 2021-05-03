package com.example.umte_app.ui.editProduct;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.umte_app.models.entities.CartItem;
import com.example.umte_app.models.repos.ProductRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditProductViewModel extends AndroidViewModel {

    private ProductRepository productRepo;

    public EditProductViewModel(@NonNull Application application) {
        super(application);
        productRepo = new ProductRepository(application);
    }
    public void insert(CartItem item, Bitmap image){

        if(image != null){
            Date dtnow = Calendar.getInstance().getTime();
            String formatedDate = new SimpleDateFormat("ddMMyyhhmmss", Locale.getDefault()).format(dtnow);
            String imageName = "IMG" + formatedDate +".jpg";
            String imagePath = saveImageToInternalStorage(image,imageName);
            item.imagePath = imagePath;
        }
        productRepo.insert(item);
    }

    public void update(CartItem item,Bitmap image){

        if(image != null){ //existuje novy obrazek

            if(item.imagePath == null){ //cesta k obrazku jeste neexistuje - vytvorim novy obrazek
                Date dtnow = Calendar.getInstance().getTime();
                String formatedDate = new SimpleDateFormat("ddMMyyhhmmss", Locale.getDefault()).format(dtnow);
                String imageName = "IMG" + formatedDate +".jpg";
                String imagePath = saveImageToInternalStorage(image,imageName);
                item.imagePath = imagePath;
            }
            else{ //cesta k obrazku uz existuje, budu prepisovat
                File originImageFile = new File(item.imagePath);
                if(originImageFile.exists()){
                    Bitmap originImage = BitmapFactory.decodeFile(originImageFile.getAbsolutePath());
                    if(!originImage.sameAs(image)){
                        saveImageToInternalStorage(image,item.imagePath.split("app_images/")[1]);
                    }
                }
            }
        }
        productRepo.update(item);

    }


    private String saveImageToInternalStorage(Bitmap image, String imageName){
        ContextWrapper wrapper = new ContextWrapper(getApplication().getApplicationContext());
        File directory = wrapper.getDir("images", Context.MODE_PRIVATE);
        File path = new File(directory,imageName);


        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            image.compress(Bitmap.CompressFormat.JPEG,100,fos);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            try {
                fos.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath() + "/" + imageName;
    }
}
