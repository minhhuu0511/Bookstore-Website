package THJava.Ngay2.Books.Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		super.onAuthenticationFailure(request, response, exception);
		String targetUrl = "/shoppingcart/fail"; // Đường dẫn đến trang đăng nhập với thông báo lỗi
		SecurityContextHolder.clearContext();
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//			Authentication authentication) throws IOException, ServletException {
//		super.onAuthenticationSuccess(request, response, authentication);
//		String targetUrl = determineTargetUrl(authentication); // Xác định trang đích dựa trên quyền của người dùng
//		getRedirectStrategy().sendRedirect(request, response, targetUrl);
//	}

//	private String determineTargetUrl(Authentication authentication) {
//		// Kiểm tra quyền của người dùng và trả về trang đích tương ứng
//		if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
//			return "/admin/dashboard";
//		} else {
//			return "/user/dashboard";
//		}
//	}
}
