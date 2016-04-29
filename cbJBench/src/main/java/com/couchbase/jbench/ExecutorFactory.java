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

import com.couchbase.jbench.config.ConfigManager;;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A simple helper for returning Thread Executor Services
 * 
 * @author david.maier@couchbase.com
 */
public class ExecutorFactory {

    public static final int NUM_THREADS = ConfigManager.getBMConfig().getNumThreads();
    
    
    
    /**
     * Create a new thread pool executor
     * @return 
     */
    public static ScheduledThreadPoolExecutor create() {
        
        ScheduledThreadPoolExecutor exec = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(NUM_THREADS);
        
        return exec;
    }
    
    /**
     * Destroy the last created executor
     * @param exec
     * @throws java.lang.InterruptedException
     */
    public static void destroy(ExecutorService exec) throws InterruptedException {
        
        exec.shutdown();
        exec.awaitTermination(1, TimeUnit.DAYS);
    }
    
}
