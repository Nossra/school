//package se.consys.filters;
//
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;
//
//import se.consys.viewmodels.ExceptionViewModel;
//
//@Provider
//public class ExceptionHandler implements ExceptionMapper<Exception> {
//	
//	private String message = "ERROR: You didn't specify anything to save.";
//	private int statusCode = 400;
//	private String suggestion = "Specify values to save.";
//	
//	@Override
//	public Response toResponse(Exception e) {
//
////		switch (e) {
////		case NoSuchElementException :
////			this.message = "ERROR: You didn't specify anything to save.";
////			this.statusCode = 400;
////			this.suggestion = "Specify values to save.";
////		}
//		
//		ExceptionViewModel exceptionViewModel = new ExceptionViewModel(this.message, this.statusCode, this.suggestion);
//		return Response.status(this.statusCode).entity(exceptionViewModel).build();
//	}
//
//}
