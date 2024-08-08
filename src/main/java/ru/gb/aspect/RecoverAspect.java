package ru.gb.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class RecoverAspect {

    @Around("@annotation(ru.gb.aspect.Recover)")
    public Object processRecoveryMethods(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();

        try {
            return pjp.proceed();
        } catch (Throwable e) {
            String className = pjp.getTarget().getClass().getSimpleName();
            String methodName = pjp.getSignature().getName();
            log.info("Recovering {}#{} after Exception[{}.class, '{}'", className, methodName, e.getClass().getSimpleName(), e.getMessage());

            if (methodSignature.getReturnType().isPrimitive()) {
                // возвращаем примитив
                // TODO: исправить, этот метод вообще не про то,что нужно было сделать
                return ClassUtils.wrapperToPrimitive(methodSignature.getReturnType());
            }
        }
        // возвращаем null
        // TODO: исправить, этот метод вообще не про то,что нужно было сделать
        return ClassUtils.primitiveToWrapper(methodSignature.getReturnType());
    }

}
