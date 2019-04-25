package common.jdbc.shard;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ShardDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {

        String key = ShardKeyStore.get();
        if (logger.isDebugEnabled()) {
            logger.debug("Shard key: " + key);
        }

        return key;
    }
}
