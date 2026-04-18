package Utilities.OrderSave;

import Model.Order.Order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressionDecorator extends OrderSaverDecorator {
    public CompressionDecorator(OrderSaver saver) {
        super(saver);
    }

    @Override
    public void save(List<Order> orders, String filename) {
        super.save(orders, filename);
        String zipFileName = filename + ".zip";
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
             FileInputStream fis = new FileInputStream(filename)) {

            ZipEntry zipEntry = new ZipEntry(new File(filename).getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            System.out.println("--- Файл сжат в архив: " + zipFileName);

            new File(filename).delete();
        } catch (IOException e) {
            System.err.println("Ошибка сжатия: " + e.getMessage());
        }
    }
}