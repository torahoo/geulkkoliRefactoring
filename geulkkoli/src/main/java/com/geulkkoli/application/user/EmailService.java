package com.geulkkoli.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class EmailService {

    @Autowired
    private  JavaMailSender javaMailSender;

    public void sendEmail(EmailDto form) {
        SimpleMailMessage message = new SimpleMailMessage(); // 파일 없이 텍스트만 전송할 때 사용
        message.setTo(form.getTo());
        message.setSubject(form.getSubject());
        message.setText(form.getText());
        javaMailSender.send(message);
    }

    // 임시 비밀번호 보내는 양식
    public void sendTempPasswordEmail(String email, String tempPassword) {
        EmailDto emailDto = new EmailDto();
        emailDto.setTo(email);
        emailDto.setSubject("[글꼬리] This is 임시 비밀번호");
        emailDto.setText("임시 비밀번호: " + tempPassword);

        sendEmail(emailDto);
    }

}