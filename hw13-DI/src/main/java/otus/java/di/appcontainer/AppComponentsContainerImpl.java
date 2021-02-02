package otus.java.di.appcontainer;

import otus.java.di.appcontainer.api.AppComponent;
import otus.java.di.appcontainer.api.AppComponentsContainer;
import otus.java.di.appcontainer.api.AppComponentsContainerConfig;
import otus.java.di.config.ParsedConfiguration;
import otus.java.di.exception.BeanInitializationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final List<Object> configClassesInstances = new ArrayList<>();

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) {
        processConfig(initialConfigClasses);
    }

    private void processConfig(Class<?>... configClasses) {
        checkConfigClasses(configClasses);
        Map<Integer, List<ParsedConfiguration>> beans = getBeanCreationMethods(configClasses);
        initializeBeans(beans);
    }

    private void checkConfigClasses(Class<?>... configClasses) {
        final Optional<Class<?>> optional = Arrays.stream(configClasses)
                .filter(c -> !c.isAnnotationPresent(AppComponentsContainerConfig.class)).findAny();
        if (optional.isPresent()) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", optional.get().getName()));
        }
    }

    private Map<Integer, List<ParsedConfiguration>> getBeanCreationMethods(Class<?>... configClasses) {
        final Map<Integer, List<ParsedConfiguration>> parsedConfigurations = new TreeMap<>();
        Arrays.stream(configClasses).forEach(c -> handleConfigClass(c, parsedConfigurations));
        return parsedConfigurations;
    }

    private void handleConfigClass(Class<?> clazz, Map<Integer, List<ParsedConfiguration>> parsedConfigurations) {
        final List<Method> classBeans = Arrays.stream(clazz.getDeclaredMethods())
                .filter(c -> c.isAnnotationPresent(AppComponent.class)).collect(Collectors.toList());
        if (!classBeans.isEmpty()) {
            for (Method classBean : classBeans) {
                final int order = classBean.getAnnotation(AppComponent.class).order();
                final List<ParsedConfiguration> classConfigurations = parsedConfigurations.computeIfAbsent(order, o -> new ArrayList<>());
                boolean isAdded = false;
                for (ParsedConfiguration classConfiguration : classConfigurations) {
                    if (classConfiguration.getClazz().equals(clazz)) {
                        classConfiguration.getBeans().add(classBean);
                        isAdded = true;
                    }
                }
                if (!isAdded) {
                    classConfigurations.add(new ParsedConfiguration(clazz, classBean));
                }
            }
        }
    }

    private void initializeBeans(Map<Integer, List<ParsedConfiguration>> beans) {
        for (Map.Entry<Integer, List<ParsedConfiguration>> entry : beans.entrySet()) {
            for (ParsedConfiguration parsedConfiguration : entry.getValue()) {
                for (Method bean : parsedConfiguration.getBeans()) {
                    final Object instance = getConfigClassInstance(parsedConfiguration.getClazz());
                    final Class<?>[] parameterTypes = bean.getParameterTypes();
                    Object[] parameters = {};
                    if (parameterTypes.length > 0) {
                        parameters = getBeanParameters(parameterTypes);
                    }
                    initializeBean(bean, instance, parameters);
                }
            }
        }
    }

    private Object getConfigClassInstance(Class<?> clazz) {
        for (Object configClassesInstance : configClassesInstances) {
            if (configClassesInstance.getClass().isAssignableFrom(clazz)) {
                return configClassesInstance;
            }
        }
        try {
            final Object instance = clazz.getConstructor().newInstance();
            configClassesInstances.add(instance);
            return instance;
        } catch (Exception e) {
            throw new BeanInitializationException("Can not receive config class instance", e);
        }
    }

    private Object[] getBeanParameters(Class<?>[] parameterTypes) {
        List<Object> parameters = new ArrayList<>();
        for (int i = 0; i < parameterTypes.length; i++) {
            for (Object appComponent : appComponents) {
                if (parameterTypes[i].isAssignableFrom(appComponent.getClass())) {
                    parameters.add(appComponent);
                    break;
                }
            }
            if (parameters.size() != i + 1) {
                throw new BeanInitializationException(String.format("No bean for %s class", parameterTypes[i].getName()));
            }
        }
        return parameters.toArray();
    }

    private void initializeBean(Method beanMethod, Object instance, Object[] parameters) {
        try {
            String beanName = beanMethod.getAnnotation(AppComponent.class).name();
            checkDuplicates(beanName);
            addBeanToStorage(beanMethod, instance, parameters, beanName);
        } catch (Exception e) {
            throw new BeanInitializationException("Can not initialize bean", e);
        }
    }

    private void checkDuplicates(String beanName) {
        if (appComponentsByName.containsKey(beanName)) {
            throw new BeanInitializationException(String.format("Bean '%s' is duplicated", beanName));
        }
    }

    private void addBeanToStorage(Method beanMethod, Object instance, Object[] parameters, String beanName) throws IllegalAccessException, InvocationTargetException {
        final Object bean = beanMethod.invoke(instance, parameters);
        appComponents.add(bean);
        appComponentsByName.put(beanName, bean);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        for (Object appComponent : appComponents) {
            if (componentClass.isAssignableFrom(appComponent.getClass())) {
                return (C) appComponent;
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}