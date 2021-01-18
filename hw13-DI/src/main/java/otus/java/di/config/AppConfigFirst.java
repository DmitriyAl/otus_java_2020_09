package otus.java.di.config;

import otus.java.di.appcontainer.api.AppComponent;
import otus.java.di.appcontainer.api.AppComponentsContainerConfig;
import otus.java.di.services.*;

@AppComponentsContainerConfig(order = 1)
public class AppConfigFirst {

    @AppComponent(order = 0, name = "equationPreparer")
    public EquationPreparer equationPreparer(){
        return new EquationPreparerImpl();
    }

    @AppComponent(order = 1, name = "playerService")
    public PlayerService playerService(IOService ioService) {
        return new PlayerServiceImpl(ioService);
    }
}