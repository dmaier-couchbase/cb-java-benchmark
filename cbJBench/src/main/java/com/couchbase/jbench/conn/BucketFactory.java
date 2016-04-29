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

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.jbench.config.ConfigManager;
import com.couchbase.jbench.config.CouchbaseConfig;


/**
 * Creates a single instance of a bucket
 * 
 * @author david.maier@couchbase.com
 */
public class BucketFactory {

    private static Bucket bucket;
    
    public static Bucket getBucket()
    {
    
        if (bucket == null)
            createBucketCon();
        
        return bucket;
    }
    
    public static AsyncBucket getAsyncBucket()
    {
    
        if (bucket == null)
            createBucketCon();
        
        return bucket.async();
    }
    
    
    public static Bucket createBucketCon()
    {
        

        CouchbaseConfig cfg = ConfigManager.getCBConfig();
        
        Cluster cluster = ClusterFactory.getCluster();
       
        if (cfg.getPassword() != null && !cfg.getPassword().equals(""))
        {
            bucket = cluster.openBucket(cfg.getBucket(), cfg.getPassword());
        }
        else
        {
            bucket = cluster.openBucket(cfg.getBucket());
        }
       
        return bucket;
    }
}
