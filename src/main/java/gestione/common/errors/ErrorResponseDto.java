package gestione.common.errors;

import java.time.LocalDateTime;

public class ErrorResponseDto {

	private String message;
    private int statusCode;
    private LocalDateTime timestamp;
    
    
    
	public ErrorResponseDto(String message, int statusCode, LocalDateTime orario) {
		this.message = message;
		this.statusCode = statusCode;
		this.timestamp = orario;
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
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
    
    
    
    
}
