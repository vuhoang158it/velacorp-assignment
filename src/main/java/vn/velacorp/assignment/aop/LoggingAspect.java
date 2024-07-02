package vn.velacorp.assignment.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

    @NotNull
    protected final HttpServletRequest httpServletRequest;

    public LoggingAspect(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }


    @Pointcut("execution(* vn.velacorp.assignment.controller..*(..)))")
    protected void restControllers() {
    }


    @Around("(restControllers())")
    public Object logAspectController(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        String ip = getIPRequest(this.httpServletRequest);
        String headers = header(this.httpServletRequest);
        String params = getParams(this.httpServletRequest.getParameterMap());

        LOGGER.debug("[REQ] ip : {} ; Request uri: {} ;method: {} ;params: {} ;header: {} ",
                ip,
                this.httpServletRequest.getRequestURI(),
                this.httpServletRequest.getMethod(),
                params,
                headers);

        Object output = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - startTime;
        LOGGER.debug(" >>> Exiting method <<< ,time: {} ms", elapsedTime);

        return output;
    }

    private String header(HttpServletRequest httpServletRequest) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> x = httpServletRequest.getHeaderNames();
        while (x.hasMoreElements()) {
            String key = x.nextElement();
            sb.append(key).append("=").append(httpServletRequest.getHeader(key)).append(",");
        }
        return sb.toString();
    }


    private String getIPRequest(HttpServletRequest servletRequest) {
        if (servletRequest == null)
            return null;
        String remoteIpAddress = null;
        remoteIpAddress = servletRequest.getHeader("X-FORWARDED-FOR");
        if (remoteIpAddress == null || remoteIpAddress.isEmpty()) {
            remoteIpAddress = servletRequest.getRemoteAddr();
        }

        return remoteIpAddress;
    }

    private String getParams(Map<String, String[]> parameterMap) {
        if (parameterMap == null || parameterMap.isEmpty())
            return "No Params";
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            sb.append(key).append("=").append(Arrays.toString(value)).append(",");
        }
        return sb.toString();
    }

}
