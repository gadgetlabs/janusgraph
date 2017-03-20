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
package org.janusgraph.graphdb.dynamodb;

import org.janusgraph.TestGraphUtil;
import org.janusgraph.diskstorage.dynamodb.BackendDataModel;
import org.junit.AfterClass;

import org.janusgraph.core.TitanFactory;
import org.janusgraph.diskstorage.BackendException;
import org.janusgraph.graphdb.SpeedTestSchema;
import org.janusgraph.graphdb.TitanGraphSpeedTest;
import org.janusgraph.graphdb.configuration.GraphDatabaseConfiguration;
import org.janusgraph.graphdb.database.StandardTitanGraph;

/**
*
* @author Alexander Patrikalakis
*
*/
public abstract class AbstractDynamoDBGraphSpeedTest extends TitanGraphSpeedTest {
    private static StandardTitanGraph graph;
    private static SpeedTestSchema schema;
    protected final BackendDataModel model;

    protected AbstractDynamoDBGraphSpeedTest(BackendDataModel model) throws BackendException {
        super(TestGraphUtil.instance().graphConfig(model));
        this.model = model;
    }

    @AfterClass
    public static void deleteTables() throws BackendException {
        TestGraphUtil.instance().cleanUpTables();
    }

    @Override
    protected StandardTitanGraph getGraph() throws BackendException {
        if (null == graph) {
            GraphDatabaseConfiguration graphconfig = new GraphDatabaseConfiguration(conf);
            graphconfig.getBackend().clearStorage();
            graph = (StandardTitanGraph) TitanFactory.open(conf);
            initializeGraph(graph);
        }
        return graph;
    }

    @Override
    protected SpeedTestSchema getSchema() {
        if (null == schema) {
            schema = SpeedTestSchema.get();
        }
        return schema;
    }

}
