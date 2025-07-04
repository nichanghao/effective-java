package net.sunday.effective.dubbo.test.common.model;

import lombok.Getter;
import org.apache.dubbo.common.resource.Disposable;
import org.apache.dubbo.rpc.model.FrameworkModel;

@Getter
public class FooBeanWithFrameworkModel implements Disposable {
    private final FrameworkModel frameworkModel;
    private boolean destroyed;

    public FooBeanWithFrameworkModel(FrameworkModel frameworkModel) {
        this.frameworkModel = frameworkModel;
    }

    @Override
    public void destroy() {
        this.destroyed = true;
    }

}
