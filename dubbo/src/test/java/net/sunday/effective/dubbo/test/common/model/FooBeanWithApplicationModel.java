package net.sunday.effective.dubbo.test.common.model;

import lombok.Getter;
import org.apache.dubbo.common.resource.Disposable;
import org.apache.dubbo.rpc.model.ApplicationModel;

@Getter
public class FooBeanWithApplicationModel implements Disposable {
    private final ApplicationModel applicationModel;
    private boolean destroyed;

    public FooBeanWithApplicationModel(ApplicationModel applicationModel) {
        this.applicationModel = applicationModel;
    }

    @Override
    public void destroy() {
        this.destroyed = true;
    }

}
