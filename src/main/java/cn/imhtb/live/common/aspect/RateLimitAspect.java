package cn.imhtb.live.common.aspect;

import cn.imhtb.live.common.annotation.CustomRateLimit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateType;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @author pinteh
 * @date 2025/4/26
 */
@Aspect
@Component
public class RateLimitAspect {

    @Resource
    private Redisson redisson;

    ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    @Pointcut(value = "@annotation(cn.imhtb.live.common.annotation.CustomRateLimit)")
    public void pointCut(){}

    @Around(value = "pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String limitKey = className + "_" + methodName + "_";

        CustomRateLimit annotation = method.getDeclaredAnnotation(CustomRateLimit.class);

        String keyStr = annotation.key();
        if (isEl(keyStr)){
            String spElValue = getSpElValue(keyStr, joinPoint);
            limitKey += spElValue;
        }

        // 创建限流器
        RRateLimiter rateLimiter = redisson.getRateLimiter(limitKey);
        if (!rateLimiter.isExists()){
            rateLimiter.trySetRate(RateType.OVERALL, annotation.rate(), Duration.ofSeconds(annotation.rateSecondInterval()));
        }
        // 申请资源
        boolean acquire = rateLimiter.tryAcquire(1);
        if (!acquire){
            throw new RuntimeException("请求频率过快");
        }
        return joinPoint.proceed();
    }

    /**
     * 获取 SpEL 表达式值
     */
    public String getSpElValue(String keyStr, ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SpelExpressionParser sep = new SpelExpressionParser();
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        // 获取方法上的参数
        String[] parameterNames = discoverer.getParameterNames(methodSignature.getMethod());
        if (parameterNames == null){
            return null;
        }
        for (int i = 0; i < parameterNames.length; i++) {
            ctx.setVariable(parameterNames[i], joinPoint.getArgs()[i]);
        }
        Expression expression = sep.parseExpression(keyStr);
        Object value = expression.getValue(ctx);
        return value.toString();
    }

    public boolean isEl(String str){
        return str != null && str.contains("#");
    }

}
