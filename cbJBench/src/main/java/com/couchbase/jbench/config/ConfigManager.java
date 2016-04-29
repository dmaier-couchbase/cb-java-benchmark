
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

package com.couchbase.jbench.config;

import java.io.IOException;

/**
 * To access the configurations
 * 
 * @author david.maier@couchbase.com
 */
public class ConfigManager {
    
    
    private static CouchbaseConfig cbConfig;  
    private static BenchmarkConfig bmConfig;
    
    
    public static CouchbaseConfig getCBConfig()
    {
        if (cbConfig == null)
            try {
                cbConfig = new CouchbaseConfig();
        } catch (IOException ex) {
            
            throw new RuntimeException("Could not load the Couchbase configuration");
        }
        
        return cbConfig;
    }
    
    public static BenchmarkConfig getBMConfig()
    {
        if (bmConfig == null)
            try {
                bmConfig = new BenchmarkConfig();
        } catch (IOException ex) {
            
            throw new RuntimeException("Could not load the benchmark configuration");
        }
        
        return bmConfig;
    }
}
