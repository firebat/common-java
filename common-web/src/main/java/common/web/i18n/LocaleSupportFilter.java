package common.web.i18n;

import com.google.common.base.Strings;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

/**
 * 优先使用URL中的lang参数，作为Locale取值
 *
 * @see LocaleQuerySupport
 */
public class LocaleSupportFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String lang = request.getParameter("lang");
        if (!Strings.isNullOrEmpty(lang)) {
            LocaleQuerySupport.setLocale(Locale.forLanguageTag(lang));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
