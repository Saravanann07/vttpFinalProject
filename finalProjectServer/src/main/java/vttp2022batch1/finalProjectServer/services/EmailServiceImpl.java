package vttp2022batch1.finalProjectServer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendSimpleEmail (String email, String username){

        try {
            SimpleMailMessage mailMessage= new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(email);
            mailMessage.setText("""
                                    Dear %s,
                                    
                                    Welcome to StockStatuss. Thank you for registering with us. 

                                    Please access the site at https://stockstatuss.herokuapp.com/ and start tracking your stock purchases!
                                    
                                    Regards,
                                    StockStatus Team""".formatted(username));

            mailMessage.setSubject("Welcome to StockStatus!");

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        catch (Exception e){
            return "Error while Sending email";
        }
    }
}
