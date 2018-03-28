package controllerinterceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.phy.gsjlpt.entity.User;

public class DoInterceptor implements HandlerInterceptor {
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO 自动生成的方法存根

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO 自动生成的方法存根

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO 自动生成的方法存根
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		System.out.println("进入了拦截器");
		//没得到user就返回登录页面
		if(user==null){
			System.out.println("没得到user");
			response.sendRedirect("/compantchat/Login.html");
			return false;
		}else {
			//得到user查看user的用户名是否为空，为空也返回登录页面
			if(user.getUsername()!=null){
				return true;
			}else{
				System.out.println("没得到username");
				//request.getRequestDispatcher("/Login.html").forward(request, response);
				response.sendRedirect("/compantchat/Login.html");
			}
		}
		return false;
	}

}
