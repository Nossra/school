//package se.consys.filters;
//
//import java.io.IOException;
//import java.security.Key;
//import javax.crypto.spec.SecretKeySpec;
//import javax.ws.rs.NotAuthorizedException;
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.core.HttpHeaders;
//import javax.ws.rs.ext.Provider;
//import io.jsonwebtoken.Jwts;
//
//@Provider
//@JWTRequired
//public class JWTFilter implements ContainerRequestFilter {
//	
//	@Override
//	public void filter(ContainerRequestContext requestContext) throws IOException {
//		String header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
//		System.out.println("header:" + header);
//		if (header == null || !header.startsWith("Bearer ")) {
//			throw new NotAuthorizedException("Authorization header must be provided");
//		} else {
//			String token = header.substring("Bearer".length()).trim();
//			String user = getUserIfValid(token);
//		}
//	}
//	
//	private String getUserIfValid(String token) {
//		Key key = new SecretKeySpec("secret".getBytes(), "DES");
//		try {
//			return Jwts.parser().setSigningKey(key)
//					.parseClaimsJws(token)
//					.getBody()
//					.getSubject();
//		} catch (Exception e) {
//			throw new NotAuthorizedException("Invalid JWT token");
//		}
//	}
//}
