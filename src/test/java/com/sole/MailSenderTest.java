/**
 * 
 */
package com.sole;

import org.junit.Test;


/**
 * @author soledad.mora
 *
 */
public class MailSenderTest {

	@Test
	public void testSendMail() {
		MailSender.sendTestEmail("solcin@gmail.com");
	}
}
