package otus.java.di;

import otus.java.di.appcontainer.AppComponentsContainerImpl;
import otus.java.di.appcontainer.api.AppComponentsContainer;
import otus.java.di.config.AppConfigFirst;
import otus.java.di.config.AppConfigSecond;
import otus.java.di.services.GameProcessor;
import otus.java.di.services.GameProcessorImpl;

/*
В классе AppComponentsContainerImpl реализовать обработку, полученной в конструкторе конфигурации,
основываясь на разметке аннотациями из пакета appcontainer. Так же необходимо реализовать методы getAppComponent.
В итоге должно получиться работающее приложение. Менять можно только класс AppComponentsContainerImpl.

PS Приложение представляет из себя тренажер таблицы умножения)
*/

public class App {

    public static void main(String[] args) throws Exception {
        AppComponentsContainer container = new AppComponentsContainerImpl(AppConfigFirst.class, AppConfigSecond.class);

        // Приложение должно работать в каждом из указанных ниже вариантов
        GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
//        GameProcessor gameProcessor = container.getAppComponent(GameProcessorImpl.class);
//        GameProcessor gameProcessor = container.getAppComponent("gameProcessor");

        gameProcessor.startGame();
    }
}