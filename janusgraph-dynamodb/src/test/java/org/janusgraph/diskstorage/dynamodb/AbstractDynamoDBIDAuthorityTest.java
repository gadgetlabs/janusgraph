/*
 * Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.janusgraph.diskstorage.dynamodb;

import java.util.Collections;

import org.janusgraph.TestGraphUtil;
import org.junit.After;

import org.janusgraph.diskstorage.BackendException;
import org.janusgraph.diskstorage.IDAuthorityTest;
import org.janusgraph.diskstorage.configuration.BasicConfiguration;
import org.janusgraph.diskstorage.configuration.WriteConfiguration;
import org.janusgraph.diskstorage.keycolumnvalue.KeyColumnValueStoreManager;
import org.janusgraph.graphdb.configuration.GraphDatabaseConfiguration;

/**
*
* @author Alexander Patrikalakis
*
*/
public abstract class AbstractDynamoDBIDAuthorityTest extends IDAuthorityTest {

    protected final BackendDataModel model;
    protected AbstractDynamoDBIDAuthorityTest(WriteConfiguration baseConfig,
            BackendDataModel model) {
        super(TestGraphUtil.instance().appendStoreConfig(model, baseConfig.copy(), Collections.singletonList("ids")));
        this.model = model;
    }
    
    @Override
    public KeyColumnValueStoreManager openStorageManager() throws BackendException {
        final BasicConfiguration config = new BasicConfiguration(GraphDatabaseConfiguration.ROOT_NS, super.baseStoreConfiguration,
            BasicConfiguration.Restriction.NONE);
        return new DynamoDBStoreManager(config);
    }

    @After
    public void cleanUpTables() throws Exception {
        TestGraphUtil.instance().cleanUpTables();
    }
}
