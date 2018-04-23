package se.consys.params;

import java.time.LocalDate;

public class LocalDateParam  {
	
	private LocalDate value;
	private LocalDate defaultValue = LocalDate.MIN;

	public LocalDateParam() {
		this.value = this.defaultValue;
	}
	
	public LocalDateParam(String val) {
		this.value = LocalDate.parse(val); 
	}
	
	private void setValue(String val) {
		if(val == null || val.equals(""))
			this.value = this.defaultValue;
		else
			this.value = LocalDate.parse(val); 
	}
	
	public LocalDate getLocalDate() {
		return this.value;
	}
}
