package infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class NotificationLogger implements INotification{

    @Override
    public void SendNotification(String subject, String body) {
        var record = String.format("[%s]\nSubject: %s\nBody: %s\n",
                LocalDateTime.now().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_INSTANT), subject,
                body);
        try {
            Files.writeString(Path.of("NotificationLog.txt"), record, StandardOpenOption.APPEND, StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
