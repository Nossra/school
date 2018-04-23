package se.consys.params;

import java.time.LocalDateTime;

public class LocalDateTimeParam {
	private LocalDateTime value;
	private LocalDateTime defaultValue = LocalDateTime.MIN;

	public LocalDateTimeParam() {
		this.value = this.defaultValue;
	}
	
	public LocalDateTimeParam(String val) {
		this.value = LocalDateTime.parse(val); 
	}
	
	private void setValue(String val) {
		if(val == null || val.equals(""))
			this.value = this.defaultValue;
		else
			this.value = LocalDateTime.parse(val); 
	}
	
	public LocalDateTime getLocalDateTime() {
		return this.value;
	}
}
