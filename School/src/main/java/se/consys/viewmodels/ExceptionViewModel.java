package se.consys.viewmodels;

public class ExceptionViewModel {
	private String message;
	private int statusCode;
	private String suggestion;
	
	public ExceptionViewModel(String message, int statusCode, String suggestion) {
		this.setMessage(message);
		this.setStatusCode(statusCode);
		this.setSuggestion(suggestion);
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
}
