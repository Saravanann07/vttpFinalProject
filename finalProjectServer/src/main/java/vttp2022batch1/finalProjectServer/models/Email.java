package vttp2022batch1.finalProjectServer.models;

public class Email {

    private String recipient;
    private String msgBody;
    private String subject;



    public String getRecipient() {return this.recipient;}
    public void setRecipient(String recipient) {this.recipient = recipient; }

    public String getMsgBody() { return this.msgBody;}
    public void setMsgBody(String msgBody) {this.msgBody = msgBody;}

    public String getSubject() {return this.subject;}
    public void setSubject(String subject) {this.subject = subject;}
}

