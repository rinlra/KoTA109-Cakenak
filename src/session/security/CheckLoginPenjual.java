package session.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

@WebFilter(filterName = "LoginPenjual",
urlPatterns = {"/penjual/*", "/penjual"})
public class CheckLoginPenjual implements Filter {

    public CheckLoginPenjual() {
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
    
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/penjual/loginpenjual.jsp", "/penjual/SigninPenjual", "/penjual/logoutpenjual",
            		"/penjual/register.jsp", "/penjual/SignupPenjual")));
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest hreq = (HttpServletRequest) request;
        
        String path = hreq.getRequestURI().substring(hreq.getContextPath().length()).replaceAll("[/]+$", "");
        Penjual user = null;
        try {
        	user = (Penjual) hreq.getSession().getAttribute("LoginUser");
        } catch (ClassCastException ex) {
        	user = null;
        }
        
        
        boolean loggedIn = (user != null && user.getTipeakun().equalsIgnoreCase("ROLE_SELLER"));
        boolean allowedPath = ALLOWED_PATHS.contains(path);
        
        if (loggedIn || allowedPath)
        {
            chain.doFilter(request, response);
        } else {
            //handle ajax request 
            String redirectUrl = "loginpenjual.jsp";
            //check if request from ajax
            if (hreq.getHeader("x-requested-with") != null
                    && hreq.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {

                // Set up response 
                HttpServletResponse hres = (HttpServletResponse) response;
                hres.setContentType("text/json; charset=UTF-8");

                PrintWriter out = hres.getWriter();
                //write response as json
                String json = "{\"redirect\":\"" + redirectUrl + "\"}";

                out.write(json);
                out.flush();
                out.close();

            } else {

                //redirect to login if not logged in
                ((HttpServletResponse) response).sendRedirect(redirectUrl);
            }
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
