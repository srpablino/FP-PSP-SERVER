package py.org.fundacionparaguaya.pspserver.surveys.entities;

import com.google.common.collect.ForwardingList;

import java.util.List;

/**
 * Created by rodrigovillalba on 10/16/17.
 */
public class Properties extends ForwardingList<SnapshotPropertyEntity> {

    private final List<SnapshotPropertyEntity> delegate;


    private Properties(List<SnapshotPropertyEntity> delegate) {
        this.delegate = delegate;
    }

    public static Properties of(List<SnapshotPropertyEntity> delegate) {
        return new Properties(delegate);
    }

    @Override
    protected List<SnapshotPropertyEntity> delegate() {
        return delegate;
    }


}
