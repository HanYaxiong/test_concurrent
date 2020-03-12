package com.synTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Date;

public class TestProxy {

    public static void main(String[] args) {

		/*Proxy :
			帮助程员产生代理对象，提供产生代理类和代理对象的静态方法
		InvocationHandler :
			句柄接口，拦截到，所有代理对象调用的方法（所有代理对象调用的方法，都可以被
			InvocationHandler拦截到）*/

        /***
         * ClassLoader：类加载器  自定义类的名称.class.getClassLoader();
         * Class[]interfaces  :指定产生的虚拟类，需要实现的接口
         * InvocationHandler:句柄对象
         * **/
        Object proxy2 = Proxy.newProxyInstance(TestProxy.class.getClassLoader(), new Class[]{UserDao.class}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                if(methodName.equals("save")) {
                    System.out.println("proxy2 invoke  " + methodName);
                } else {
                    System.out.println("proxy2 else  " + methodName);
                }
                Object result = method.invoke(proxy, args);
                return result;
            }
        });

        Object proxy = Proxy.newProxyInstance(TestProxy.class.getClassLoader(),
                new Class[]{UserDao.class}, new  InvocationHandler(){

                    //创建目标对象的实例
                    UserDao dao = new UserDaoImpl();
                    @Override
                    public Object invoke(
                            Object proxy,  //表示代理对象
                            Method method, //表对通过代理对象，正在调用的方法
                            Object[] args  //表示通过代理对象，调用的方法，需要接受的参数
                    ) throws Throwable {
                        // TODO Auto-generated method stub

                        //System.out.println("==============="+method.getName()+"----"+Arrays.toString(args));
                        String name=method.getName();
                        if(name.equals("save"))
                        {
                            //添加实现功能增强的代码：完成日志记录
                            System.out.println("在"+new Date()+"  时间，此方法被调用----------");

                        }
                        if(name.equals("hashCode"))
                        {
                            return 100;
                        }
                        //放行：调用目标对应中的原始的方法
                        Object rv=method.invoke(dao, args);

                        return rv;
                    }

                });
        //GET:
        //request.getParamter("uname");

        UserDao dao = new UserDaoImpl();
        dao.save("zhangsan");
        dao.hashCode();
        dao.toString();


        UserDao dao2=(UserDao)proxy2;

        //System.out.println(dao2.getClass());

        dao2.save("lisi");
        Object code=dao2.hashCode();
        System.out.println("code="+code);
        dao2.toString();



    }

	/*class MyInvocationHandler implements InvocationHandler
	{

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			// TODO Auto-generated method stub
			return null;
		}

	}*/

}

