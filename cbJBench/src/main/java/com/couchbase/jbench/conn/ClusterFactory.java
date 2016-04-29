 /*
  * Copyright 2016 Couchbase, Inc.
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

package com.couchbase.jbench.conn;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.jbench.config.ConfigManager;
import com.couchbase.jbench.config.CouchbaseConfig;
import java.util.Arrays;
import java.util.List;

/**
 * To create a single cluster reference
 * 
 * @author david.maier@couchbase.com
 */
public class ClusterFactory {
    
    private static Cluster cluster;
    
    
    public static Cluster getCluster()
    {
        if (cluster == null)
            createCluster();
       
        return cluster;
    }
    
    public static Cluster createCluster()
    {
        CouchbaseConfig cfg = ConfigManager.getCBConfig();
        
        
        //Create the cluster reference
        String[] hosts = cfg.getHosts();
        
        List<String> nodes = Arrays.asList(hosts);
                          
        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder().kvEndpoints(cfg.getNumEndpoints()).build();
      
        cluster = CouchbaseCluster.create(env, nodes);
        
        return cluster;
    }
}
