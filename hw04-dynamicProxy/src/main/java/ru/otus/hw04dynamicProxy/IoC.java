package ru.otus.hw04dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IoC {

    public static TestLogin getTestLogin() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoginImpl());
        return (TestLogin) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{TestLogin.class}, handler);
    }

    private static class DemoInvocationHandler implements InvocationHandler {
        private final Object object;
        private Set<Method> methods4Log;

        DemoInvocationHandler(Object object) {
            this.object = object;
            initMethods4Log();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //Если метод класса помечен аннотацией ru.otus.hw04dynamicProxy.Log
            // - в консоле должно быть:
            //executed method: <method name>, params: <param list>
            if (methods4Log.contains(object.getClass().getMethod(method.getName(), method.getParameterTypes()))) {
                System.out.println("executed method: " + method.getName() + ", params: " + Arrays.toString(args));
            }
            return method.invoke(object, args);
        }

        private void initMethods4Log() {
            methods4Log = new HashSet<>();
            Arrays.stream(object.getClass().getDeclaredMethods()).forEach(method -> {
                if (method.getAnnotation(Log.class) != null) {
                    methods4Log.add(method);
                }
            });
        }
    }

}
