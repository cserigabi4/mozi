package hu.alkfejl.filter;

import hu.alkfejl.model.Felhasznalo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private List<String> exclusions;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.exclusions = Arrays.asList(filterConfig.getServletContext().getInitParameter("bejelentkezes-filter-exclusion").split(","));
        this.exclusions.replaceAll(String::trim);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String path = ((HttpServletRequest) request).getServletPath();
        if (exclusions.stream().anyMatch(path::equals)) {
            chain.doFilter(request, response);
            return;
        }

        Felhasznalo aktFelhasznalo = (Felhasznalo) ((HttpServletRequest) request).getSession().getAttribute("felhasznalo");

        if (aktFelhasznalo == null) {
            ((HttpServletResponse)response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/pages/bejelentkezes.jsp");
        }
        else{
            chain.doFilter(request, response);
        }
    }
}
