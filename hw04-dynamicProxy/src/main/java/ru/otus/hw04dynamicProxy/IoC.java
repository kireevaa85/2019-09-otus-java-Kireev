package ru.otus.hw04dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class IoC {

    public static TestLogin getTestLogin() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoginImpl());
        return (TestLogin) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{TestLogin.class}, handler);
    }

    private static class DemoInvocationHandler implements InvocationHandler {
        private final Object object;

        DemoInvocationHandler(Object object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //Если метод интерфейса(либо метод класса) помечен аннотацией ru.otus.hw04dynamicProxy.Log
            // - в консоле дожно быть:
            //executed method: <method name>, params: <param list>
            if (method.getAnnotation(Log.class) != null
                    || object.getClass().getMethod(method.getName(), method.getParameterTypes()).getAnnotation(Log.class) != null) {
                System.out.println("executed method: " + method.getName() + ", params: " + Arrays.toString(args));
            }
            return method.invoke(object, args);
        }
    }

}
