package session.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Penjual;

@WebFilter(filterName = "CheckLogoutPenjual", urlPatterns = {"/penjual/loginpenjual.jsp"})
public class CheckLogoutPenjual implements Filter{
	
		    public CheckLogoutPenjual() {
		    }

		    /**
		     *
		     * @param request The servlet request we are processing
		     * @param response The servlet response we are creating
		     * @param chain The filter chain we are processing
		     *
		     * @exception IOException if an input/output error occurs
		     * @exception ServletException if a servlet error occurs
		     */
		    @Override
		    public void doFilter(ServletRequest request, ServletResponse response,
		            FilterChain chain)
		            throws IOException, ServletException {
		        
		        Penjual user = null;
		        try {
		        	user = (Penjual) ((HttpServletRequest) request).getSession().getAttribute("LoginUser");
		        } catch (ClassCastException ex) {
		        	user = null;
		        }
		        
		        //SEE : when remove this line it work wrong
		        //System.out.println(user.getUserName());
		        if (user == null) { //if user logged out
		            //System.out.println("not logged in");
		            chain.doFilter(request, response);
		        } else {
		            //redirect to index page
		            System.out.println("already logged in");
		            ((HttpServletResponse) response).sendRedirect("index.jsp");
		        }
		    }

		    /**
		     * Destroy method for this filter
		     */
		    @Override
		    public void destroy() {
		    }

		    /**
		     * Init method for this filter
		     *
		     * @param filterConfig
		     */
		    @Override
		    public void init(FilterConfig filterConfig) {

		    }

}
