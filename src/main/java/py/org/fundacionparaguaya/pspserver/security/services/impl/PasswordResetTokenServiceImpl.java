package py.org.fundacionparaguaya.pspserver.security.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import py.org.fundacionparaguaya.pspserver.common.exceptions.CustomParameterizedException;
import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.mail.service.EmailService;
import py.org.fundacionparaguaya.pspserver.security.entities.PasswordResetTokenEntity;
import py.org.fundacionparaguaya.pspserver.security.entities.UserEntity;
import py.org.fundacionparaguaya.pspserver.security.repositories.PasswordTokenRepository;
import py.org.fundacionparaguaya.pspserver.security.repositories.UserRepository;
import py.org.fundacionparaguaya.pspserver.security.services.PasswordResetTokenService;

@Service
public class PasswordResetTokenServiceImpl
    implements PasswordResetTokenService {

    private UserRepository userRepository;

    private PasswordTokenRepository passwordTokenRepository;

    private EmailService emailService;

    @Value("${client.wepapp.domain}")
    private String clientWepappDomain;

    private static final String MAIL_PAGE_TEMPLATE = "reset-mail-template.html";

    private static final String PASSWORD_RECOVERY_PAGE =
            "recovery-password.html";

    private static final String MAIL_TEMPLATE_PATH = "templates/email/";

    private static final String MAIL_PARAM_TOKEN = "token";

    private static final String MAIL_PARAM_ID = "id";

    public PasswordResetTokenServiceImpl(UserRepository userRepository,
            PasswordTokenRepository passwordTokenRepository,
            EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.emailService = emailService;
    }

    @Override
    public void resetPassword(HttpServletRequest request,
            String userEmail) {

        UserEntity user = userRepository.findUserByEmail(userEmail).get();

        if (user == null) {
            throw
            new CustomParameterizedException("User not found");
        }

        String token = UUID.randomUUID().toString();

        createPasswordResetTokenForUser(user, token);

        SimpleMailMessage template = new SimpleMailMessage();
        template.setText(loadTemplate(MAIL_PAGE_TEMPLATE));

        String[] args = {clientWepappDomain
                + PASSWORD_RECOVERY_PAGE + "?"+MAIL_PARAM_TOKEN+"="
                + token
                + "&"+MAIL_PARAM_ID+"="
                + user.getId(),
                user.getEmail()};

        emailService.sendSimpleMessageUsingTemplate(user.getEmail(),
                "Reset your password for Poverty Stoplight Platform",
                template,
                args);

    }

    private String loadTemplate(String templateId) {
        URL url = Resources.getResource(
                MAIL_TEMPLATE_PATH + templateId);
        String content = "";
        try {
            content = Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            throw new CustomParameterizedException(
                    "Could not read email template with ID = ",
                    templateId);
        }
        return content;
    }

    @Override
    public void createPasswordResetTokenForUser(UserEntity user,
            String token) {
        PasswordResetTokenEntity myToken =
                new PasswordResetTokenEntity(token, user);
        LocalDateTime expirationLocalDate = LocalDateTime.now().
                plusMinutes(PasswordResetTokenEntity.
                        getExpiration());
        Instant instant = expirationLocalDate.toInstant(ZoneOffset.UTC);
        Date expirationDate = Date.from(instant);
        myToken.setExpiryDate(expirationDate);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public void validatePasswordResetToken(String token,
            Long userId, String password,
            String repeatPassword) {

        checkArgument(userId > 0,
                "Argument was %s but expected nonnegative", userId);

        UserEntity userEntity = userRepository.findOne(userId);

        if (userEntity == null) {
            throw new UnknownResourceException("User does not exist");
        }

        PasswordResetTokenEntity passwordResetTokenEntity =
                passwordTokenRepository.findByToken(token);

        if (passwordResetTokenEntity == null
                || passwordResetTokenEntity.
                getUser().getId().longValue()
                != userId.longValue()) {
            throw new CustomParameterizedException("Invalid token", token);
        }

        Calendar cal = Calendar.getInstance();
        if ((passwordResetTokenEntity.getExpiryDate().getTime()
                - cal.getTime().getTime()) <= 0) {
            throw new CustomParameterizedException("Token expired", token);
        }

        if (!password.equals(repeatPassword)) {
            throw new CustomParameterizedException(
                    "Password does not match the confirm password");
        }

        userEntity.setPass(encryptPassword(repeatPassword));
        userRepository.save(userEntity);

    }

    private String encryptPassword(String plainTextPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(plainTextPassword);
    }

}