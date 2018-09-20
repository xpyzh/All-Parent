package fastjson.model;

import java.io.Serializable;

/**
 * Created by youzhihao on 2017/9/4.
 */
public class InvocationContext implements Serializable {

    private static final long serialVersionUID = -4910660635277448599L;

    private Class targetClass;

    private String methodName;

    private Class[] parameterTypes;

    private Object[] args;


    public InvocationContext() {
    }

    public InvocationContext(Class targetClass, String methodName, Class[] parameterTypes, Object... args) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.targetClass = targetClass;
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
