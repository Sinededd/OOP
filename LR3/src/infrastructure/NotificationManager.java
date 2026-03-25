package infrastructure;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private final List<INotification> notificationServices;

    public NotificationManager()
    {
        notificationServices = new ArrayList<>();
    }

    public List<INotification> getNotificationServices() {
        return notificationServices;
    }

    public void registerNotificationService(INotification notification)
    {
        notificationServices.add(notification);
    }

    public void unregisterNotificationService(INotification notification) {
        notificationServices.remove(notification);
    }

    public void sendNotification(String subject, String body){
        for(var notification : notificationServices)
        {
            notification.SendNotification(subject, body);
        }
    }
}
