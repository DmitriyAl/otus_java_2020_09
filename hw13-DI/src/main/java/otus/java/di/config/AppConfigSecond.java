package otus.java.di.config;

import otus.java.di.appcontainer.api.AppComponent;
import otus.java.di.appcontainer.api.AppComponentsContainerConfig;
import otus.java.di.services.*;

@AppComponentsContainerConfig(order = 1)
public class AppConfigSecond {

    @AppComponent(order = 2, name = "gameProcessor")
    public GameProcessor gameProcessor(IOService ioService,
                                       PlayerService playerService,
                                       EquationPreparer equationPreparer) {
        return new GameProcessorImpl(ioService, equationPreparer, playerService);
    }

    @AppComponent(order = 0, name = "ioService")
    public IOService ioService() {
        return new IOServiceConsole(System.out, System.in);
    }
}