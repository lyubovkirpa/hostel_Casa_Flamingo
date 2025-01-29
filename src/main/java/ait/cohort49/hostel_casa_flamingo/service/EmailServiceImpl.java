package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.ConfirmationService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.EmailService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final Configuration mailConfig;

    private final ConfirmationService confirmationService;

    private final static String HOST = "http:://localhost:8080/api";

    public EmailServiceImpl(JavaMailSender mailSender, Configuration mailCongig, ConfirmationService confirmationService) {
        this.mailSender = mailSender;
        this.mailConfig = mailCongig;
        this.confirmationService = confirmationService;

        this.mailConfig.setDefaultEncoding("UTF-8");
        this.mailConfig.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/mail"));
    }

    @Override
    public void sendConfirmationEmail(User user) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String emailText = generateEmailText(user);

            String fromAddress = System.getenv("MAIL_USERNAME");
            helper.setFrom(fromAddress);

            helper.setTo(user.getEmail());

            helper.setSubject("Registration confirmation");

            helper.setText(emailText, true);
            mailSender.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateEmailText(User user) {
        try {
//            загрузка шаблона письма
            Template template = mailConfig.getTemplate("confirm_reg_mail.ftlh");

//            генерация кода подтверждения
            String code = confirmationService.generateConfirmationCode(user);

//            сформировать ссылку http:://localhost:8080/api/confirm?code=сгенерированнный код

            String confirmationLink = HOST + "confirm?code=" + code;

//            модель данных для подстановки в шаблон
            Map<String, Object> modelPattern = new HashMap<>();
            modelPattern.put("name", user.getFirstName());
            modelPattern.put("confirmationLink", confirmationLink);

//            педаем модель в шаблон, чтобы получить текст письма
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, modelPattern);


        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);

        }
    }


}
