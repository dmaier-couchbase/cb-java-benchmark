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

package com.couchbase.jbench;

import com.couchbase.jbench.config.BenchmarkConfig;
import com.couchbase.jbench.config.ConfigManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic benchmark has an init, execution and shutdown phase
 * 
 * 
 * @author david.maier@couchbase.com
 */
public abstract class BaseBenchmark implements Runnable {
     
    protected final Logger LOG = Logger.getLogger(this.getClass().getName());
    protected  final BenchmarkConfig BMCFG = ConfigManager.getBMConfig();
    
    /**
     * Initialize the test
     */
    protected void init() {
       
        LOG.log(Level.INFO, "START {0}:{1}", new Object[]{this.getClass().getName(), Thread.currentThread().getName()});
       
    }

    /**
     * Run the benchmark test phases
     */
    @Override
    public void run() {
       
        init();
        exec();
        shutdown();
        
    }
 
    /**
     * Execute the test
     */
    abstract protected void exec();
   
    
    /**
     * Shutdown the test
     */
    public void shutdown() {
        
        LOG.log(Level.INFO, "STOP {0}:{1}", new Object[]{this.getClass().getName(), Thread.currentThread().getName()});

    }
}
