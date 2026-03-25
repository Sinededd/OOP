package infrastructure;

/**
 * SmtpMailer - имитация почтового сервиса
 */
public class SmtpMailer implements INotification {
    private String server;
    private String receivingUserMail = "";

    public SmtpMailer(String server) {
        this.server = server;
    }

    public SmtpMailer(String server, String receivingUserMail) {
        this.server = server;
        this.receivingUserMail = receivingUserMail;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public void SendNotification(String subject, String body) {
        if(receivingUserMail.isEmpty()) {
            System.out.print("Recipient not specified");
            return;
        }

        System.out.printf(">> Connecting to SMTP server %s...\n", server);
        System.out.printf(">> Sending EMAIL to %s\n   Subject: %s\n   Body: %s\n", receivingUserMail, subject, body);
    }

    public String getReceivingUserMail() {
        return receivingUserMail;
    }

    public void setReceivingUserMail(String receivingUserMail) {
        this.receivingUserMail = receivingUserMail;
    }
}