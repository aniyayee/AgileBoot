package com.github.core.common.exception;

import cn.hutool.json.JSONUtil;
import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;
import com.github.core.common.resp.ResponseDTO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author yayee
 */
@Slf4j
@WebFilter(filterName = "ExceptionFilter", urlPatterns = "/*")
public class GlobalExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        try {
            chain.doFilter(request, response);
        } catch (AgileException ex) {
            log.error("global filter exceptions", ex);
            String resultJson = JSONUtil.toJsonStr(ResponseDTO.fail(ex));
            writeToResponse(response, resultJson);
        } catch (Exception e) {
            log.error("global filter exceptions, unknown error:", e);
            ResponseDTO<Object> responseDTO = ResponseDTO.fail(new AgileException(ErrorCode.Internal.INTERNAL_ERROR, e.getMessage()));
            String resultJson = JSONUtil.toJsonStr(responseDTO);
            writeToResponse(response, resultJson);
        }
    }

    private void writeToResponse(ServletResponse response, String resultJson) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(resultJson);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
