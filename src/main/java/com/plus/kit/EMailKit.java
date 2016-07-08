/**
 * Copyright (c) 2009-2016, LarryKoo 老古 (gumutianqi@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plus.kit;

import com.google.common.base.Throwables;
import jodd.mail.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EMailKit {
    public static final Integer PRIORITY_HIGHEST = 1;
    public static final Integer PRIORITY_NORMAL = 2;
    public static final Integer PRIORITY_LOW = 3;

    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 创建一个简单的Email对象
     *
     * @param from
     * @param to
     * @param subject
     * @param text
     * @param html
     * @return
     */
    public static Email createBaseEmail(String from, String to, String subject, String text, String html) {
        Email email = Email.create()
                .from(from)
                .to(to)
                .subject(subject, DEFAULT_ENCODING)
                .addText(text, DEFAULT_ENCODING)
                .addHtml(html, DEFAULT_ENCODING);
        return email;
    }

    /**
     * 带附件的简单邮件模版
     *
     * @param from
     * @param to
     * @param subject
     * @param text
     * @param html
     * @param attachmentBuilder
     * @return
     */
    public static Email createBaseEmail(String from, String to, String subject, String text, String html, EmailAttachmentBuilder attachmentBuilder) {
        Email email = Email.create()
                .from(from)
                .to(to)
                .subject(subject, DEFAULT_ENCODING)
                .addText(text, DEFAULT_ENCODING)
                .addHtml(html, DEFAULT_ENCODING);
        if (null != attachmentBuilder) {
            email.attach(attachmentBuilder);
        }
        return email;
    }

    /**
     * 发送一封邮件
     *
     * @param host         host，如smtp.qq.com
     * @param hostUser     邮箱帐号
     * @param hostPassWord 邮箱密码
     * @param email        要发送邮件文档对象
     * @return
     */
    public static String sendMail(String host, String hostUser, String hostPassWord, Boolean useSSL, Email email) {
        String msgId = null;
        SendMailSession session = null;
        try {
            SmtpServer smtpServer;
            if (useSSL) {
                smtpServer = SmtpServer.create(host).authenticateWith(hostUser, hostPassWord);
            } else {
                smtpServer = SmtpSslServer.create(host).authenticateWith(hostUser, hostPassWord);
            }
            session = smtpServer.createSession();
            session.open();
            msgId = session.sendMail(email);
        } catch (Throwable t) {
            log.error("sendMail-ex:{}", t);
            throw Throwables.propagate(t);
        } finally {
            session.close();
            return msgId;
        }
    }
}
