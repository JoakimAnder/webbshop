package se.iths.webbshop.annotations;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import se.iths.webbshop.controllers.utilities.Login;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Aspect
@Component
public class CredentialsAspect {

    @Autowired
    Login login;

    @Around("@annotation(Credentials)")
    public Object checkCredentials(ProceedingJoinPoint point) throws Throwable {

        Method method = getMethod(point);
        int index = getParameterIndex(method, p -> p.getName().equalsIgnoreCase("id") && p.getAnnotation(PathVariable.class) != null);
        if(index == -1)
            return "redirect:/warning?type=InvalidUseOfAnnotation&status=555&message=@Credentials has been used on "+point.getSignature().toLongString();

        int id = (int) point.getArgs()[index];
        List<String> levels = getLevels(method);

        boolean hasCredentials = false;
        if(levels.contains("admin") && id == login.getID() && login.isAdmin())
            hasCredentials = true;
        if(levels.contains("customer") && id == login.getID() && !login.isAdmin())
            hasCredentials = true;
        if(levels.contains("anyone"))
            hasCredentials = true;


        if(!hasCredentials) {
            System.err.printf("WARNING User %d Tried to access method %s which has required level of %s\n",
                    login.getID(),
                    point.getSignature().toLongString(),
                    levels
            );
            return "redirect:/home";
        }


        return point.proceed();
    }

    private Method getMethod(ProceedingJoinPoint point) {
        try {
            return point.getTarget().getClass().getMethod(point.getSignature().getName(), getParameterClasses(point).toArray(new Class[0]));
        } catch (NoSuchMethodException e) {
            return null;
        }

    }

    private List<Class> getParameterClasses(ProceedingJoinPoint point) {
        String str = point.getSignature().toLongString();
        Pattern pattern = Pattern.compile("(?<="+point.getSignature().getName()+"\\().*?(?=\\))");
        Matcher matcher = pattern.matcher(str);
        String[] parameterClassNames = matcher.find() ? matcher.group().split(",") : new String[0];

        List<Class> parameterTypes = Arrays.stream(parameterClassNames).map(s -> {
            try {
                return Class.forName(s);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        return parameterTypes;
    }

    private int getParameterIndex(Method method, Predicate<Parameter> predicate) {
        if(method == null) return -1;

        for(int i = 0; i < method.getParameters().length; i++) {
            if(predicate.test(method.getParameters()[i]))
                return i;
        }
        return -1;
    }

    private List<String> getLevels(Method method) {
        String[] levels = {};

        Credentials classAnnotation = method.getDeclaringClass().getAnnotation(Credentials.class);
        Credentials methodAnnotation = method.getAnnotation(Credentials.class);

        if(classAnnotation != null) {
            levels = classAnnotation.value();
        }
        if(methodAnnotation != null) {
            levels = methodAnnotation.value().length == 0 ? levels : methodAnnotation.value();
        }

        return Arrays.asList(levels);
    }
}
