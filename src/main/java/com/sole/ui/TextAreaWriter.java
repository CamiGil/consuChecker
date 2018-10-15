/**
 * 
 */
package com.sole.ui;

import java.io.IOException;
import java.io.Writer;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author soledad.mora
 *
 */
public class TextAreaWriter extends Writer {

	private final JTextArea textArea;
	private final JScrollPane scrollPane;

	public TextAreaWriter(JTextArea textArea, JScrollPane scrollPane) {
		this.textArea = textArea;
		this.scrollPane = scrollPane;
  }

	@Override
  public void close() throws IOException {
  }

	@Override
  public void flush() throws IOException {
  }

	@Override
  public void write(char[] arg0, int arg1, int arg2) throws IOException {
	  textArea.setText(textArea.getText() + String.copyValueOf(arg0, arg1, arg2));
	  scrollPane.getVerticalScrollBar().setValue( scrollPane.getVerticalScrollBar().getMaximum());
	}


}
