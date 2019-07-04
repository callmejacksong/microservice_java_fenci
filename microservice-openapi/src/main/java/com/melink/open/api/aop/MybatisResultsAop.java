package com.melink.open.api.aop;

import com.melink.open.api.annotation.mybatis.MyResult;
import com.melink.open.api.annotation.mybatis.MyResults;
import com.melink.open.api.spring.SpringContextUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Aspect
public class MybatisResultsAop {
    private static final Logger log = LoggerFactory.getLogger(MybatisResultsAop.class);
    @Pointcut("execution(* com.melink.open.api.mapper..*.*(..))")
    public void excu(){} //切面定义，mapper包（包括子包）下的所有方法
    //ProceedingJoinPoint
    @AfterReturning(returning="re",pointcut="excu()")//后置通知，得到方法结果re
    public Object addAfter(JoinPoint joinPoint,Object re) throws Exception {
        long start = System.currentTimeMillis();
        MyResults annotations = getAnnotation(joinPoint);//得到方法上的 自定义注解
        if(annotations == null) {
            return re;
        }
        if (re == null) {
            return re;
        }
        List results = (List) re;
        if (CollectionUtils.isEmpty(results)) {
            return re;
        }
        for (MyResult annotation : annotations.value()) {
            String column = annotation.column();
            String one = annotation.one();
            String foreign = annotation.foreign();
            String property = annotation.property();
            String many = annotation.many();
            if (StringUtils.isEmpty(one) && StringUtils.isEmpty(many)) {
                log.error("The parameters are incorrect. Please confirm one or many.");
                continue;
            }
            if (!StringUtils.isEmpty(one) && !StringUtils.isEmpty(many)) {
                log.error("cannot happen one and many at the same time . Please confirm one or many.");
                continue;
            }

            if (!hasArge(results.get(0), column)) {
                log.error("cannot find column in Result Object");
                continue;
            }
            if (!hasArge(results.get(0), property)) {
                log.error("cannot find property in Result Object");
                continue;
            }

            List ids = getIds(results, column);

            //one不为空
            if (!StringUtils.isEmpty(one)) {
                Map<Object, Object> map = new HashMap<>();
                //通过ids调用外键对应的mapper方法，等到结果
                List list = getForeignKeyByIds(ids, one);
                if (CollectionUtils.isEmpty(list)) {
                    log.error("according to column find result is null");
                    continue;
                }
                if (!hasArge(list.get(0), foreign)) {
                    log.error("cannot find foreign in pointing method");
                    continue;
                }
                //将得到的外键对象list 处理成一个map
                //key 为外键key的value，value 为对象自己
                for (Object o : list) {
                    Class<?> aClass = o.getClass();
                    Field guid = getField(aClass,foreign);
                    Object k = guid.get(o);
                    map.put(k, o);
                }

                //所结果与处理后的map进行一对一的映射赋值
                for (Object o : results) {
                    Class<?> aClass = o.getClass();
                    Field k = getField(aClass,property);
                    Field kk = getField(aClass,column);
                    k.set(o,map.get(kk.get(o)));
                }
            } else if (!StringUtils.isEmpty(many)) {

                List list = getForeignKeyByIds(ids, many);
                if (!hasArge(list.get(0), foreign)) {
                    log.error("cannot find foreign in pointing method");
                    throw new RuntimeException("cannot find foreign in pointing method");
                }
                if (isTypeList(results.get(0),property)) {
                    log.error("result object property type incorrect");
                }
                //处理成的map的value为一个list
                Map<Object, List<Object>> map = new HashMap<>();
                if (!CollectionUtils.isEmpty(list)) {
                    for (Object o : list) {
                        Class<?> aClass = o.getClass();
                        Field guid = getField(aClass,foreign);
                        guid.setAccessible(true);
                        Object k = guid.get(o);
                        if (!map.containsKey(k)) {
                            List<Object> v = new ArrayList<>();
                            v.add(o);
                            map.put(k, v);
                        } else {
                            map.get(k).add(o);
                        }
                    }
                }

                for (Object o : results) {
                    Class<?> aClass = o.getClass();
                    Field k = getField(aClass,property);
                    Field kk = getField(aClass,column);
                    k.set(o,map.get(kk.get(o)));
                }

            }
        }
        long end = System.currentTimeMillis();
        System.out.println("AOP运行时间："+ (end - start));
        return re;
    }

    //得到方法上的注解
    private MyResults getAnnotation(JoinPoint joinPoint) {
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        MyResults annotation = method.getAnnotation(MyResults.class);
        return annotation;
    }

    //根据re结果和注解中的数据，从re中获取每个对象的外键id的list
    private List getIds(List re,String column) throws Exception {
        List ids = new ArrayList();
        for (Object o : re) {
            Class<?> aClass = o.getClass();
            Field field = getField(aClass,column);
            Object id = field.get(o);
            ids.add(id);
        }
        return ids;
    }

    private List getForeignKeyByIds(List ids,String oneOrMany){
        //对外键mapper方法的路径进行处理分为 mapper接口路径和方法名称
        //com.my.project.mapper.KeywordNetMapper.findKeywordByKeywordIds
        // com.my.project.mapper.KeywordNetMapper and  findKeywordByKeywordIds
        int i = oneOrMany.lastIndexOf(".");
        String c = oneOrMany.substring(0, i);
        String m = oneOrMany.substring(i+1);
        List list = null;
        try {
            //通过mapper接口的字节码从Spring上下文环境中获取mapper对象，
            // 不能自己创建，mapper接口需要通过mybatis初始化
            Object bean = SpringContextUtil.getBean(Class.forName(c));
            Class<?> aClass2 = bean.getClass();
            Method method = aClass2.getDeclaredMethod(m, List.class);
            //反射执行方法，也会被AOP代理，该方法没有添加自定义的注解
            //所以AOP获取注解后要先判空，不然报错
            list = (List) method.invoke(bean, ids);
        } catch (Exception e) {
            log.error("one pointing incorrect or method ERROR", e);
            throw new RuntimeException("one pointing incorrect or method ERROR");
        }
        return list;
    }

    boolean hasArge(Object obj, String arge) {

        Class<?> aClass = obj.getClass();
        try {
            Field field = getField(aClass, arge);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    boolean isTypeList(Object obj,String arge) {
        Class<?> aClass = obj.getClass();
        Field field = null;
        try {
            field = aClass.getDeclaredField(arge);
            field.setAccessible(true);
        } catch (Exception e) {
            log.warn("cannot find field in Object",e);
        }
        Class<?> type = field.getType();
        String typeName = type.getTypeName();
        if (typeName.equals("list")) {
            return true;
        }
        return false;
    }
    //获取对象的属性，部分属性可能在父类，递归获取
    public Field getField(Class c, String str) throws NoSuchFieldException {
        Field guid = c.getDeclaredField(str);
        guid.setAccessible(true);
        return guid;
    }


    //    @Around("excu()")//环绕通知
//    public void twiceAsOld(ProceedingJoinPoint joinPoint){
//        System.err.println ("切面执行了。。。。");
//        System.out.println();
//
//       // return null;
//    }
}
