package shop.notification;

public abstract class AbstractChannel implements NotificationChannel {
    private int sentCount = 0;
    private String lastDelivered = "";

    @Override
    public final boolean send(CustomerContact contact, String message) {
        if (message == null || message.isBlank()) return false;
        
        String recipient = resolveRecipient(contact);
        if (recipient == null || recipient.isBlank()) return false;
        
        String preparedMessage = prepare(message);
        if (deliver(recipient, preparedMessage)) {
            sentCount++;
            lastDelivered = preparedMessage;
            return true;
        }
        return false;
    }

    protected abstract String resolveRecipient(CustomerContact contact);
    protected abstract boolean deliver(String recipient, String message);
    
    protected String prepare(String message) { return message; }
    public int getSentCount() { return sentCount; }
    public String getLastDelivered() { return lastDelivered; }
}