package infrastructure;

public class TelegramSender implements INotification{
    private String receivingUser;

    public TelegramSender(String receivingUser)
    {
        this.receivingUser = receivingUser;
    }

    @Override
    public void SendNotification(String subject, String body) {
        System.out.print(">> Telegram notification:...\n");
        System.out.printf(">> Sending to %s\n   Subject: %s\n   Body: %s\n", receivingUser, subject, body);
    }


    public String getReceivingUser() {
        return receivingUser;
    }

    public void setReceivingUser(String receivingUser) {
        this.receivingUser = receivingUser;
    }
}
