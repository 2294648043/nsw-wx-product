package com.nsw.wx.product.util;//package com.nsw.wx.plat.draw.util;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.nsw.wx.plat.draw.pojo.User;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//
//public class UserContext {
//
//	public static final String LOGIN_IN_SESSION = "logininfo";
//	public static final String VERIFYCODE_IN_SESSION = "VERIFYCODE_IN_SESSION";
//
//	private static HttpServletRequest getRequest() {
//		return ((ServletRequestAttributes) RequestContextHolder
//				.getRequestAttributes()).getRequest();
//	}
//
//	public static void putLogininfo(User logininfo) {
//		getRequest().getSession().setAttribute(LOGIN_IN_SESSION, logininfo);
//	}
//
//	public static User getCurrent() {
//		return (User) getRequest().getSession().getAttribute(
//				LOGIN_IN_SESSION);
//	}
//
//	public static void putVerifyCode(VerifyCode code) {
//		getRequest().getSession().setAttribute(VERIFYCODE_IN_SESSION, code);
//	}
//
//	public static VerifyCode getVerifyCode() {
//		return (VerifyCode) getRequest().getSession().getAttribute(
//				VERIFYCODE_IN_SESSION);
//	}
//}
