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

import com.couchbase.jbench.tests.CBPreLoadBenchmark;
import com.couchbase.jbench.config.ConfigManager;
import com.couchbase.jbench.tests.CBCombinedLoadBenchmark;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



/**
 * The entry point of the application
 * 
 * @author david.maier@couchbase.com
 */
public class Start {
   
    public static void main(String[] args) throws Exception {
         
        prepload();
        load();
      
    }
    
    /**
     * Load documents to the Couchbase bucket
     * 
     * @throws InterruptedException 
     */
    public static void prepload() throws InterruptedException {
        
        
        System.out.println();
        
        if (!ConfigManager.getBMConfig().isPopulationSkipped()) {
        
        
        //Preload
        ScheduledThreadPoolExecutor preloadExec = ExecutorFactory.create();
        
        for (int i = 0; i < ExecutorFactory.NUM_THREADS; i++) {
            
            preloadExec.execute(new CBPreLoadBenchmark());
        
        }
        
        ExecutorFactory.destroy(preloadExec);
        
        }
    }
    
    /**
     * Mixed workload
     * 
     * @throws InterruptedException 
     */
    public static void load() throws InterruptedException {
        
        //Load
        ScheduledThreadPoolExecutor loadExec = ExecutorFactory.create();
        
        int finished = ConfigManager.getBMConfig().getCycles();
        
        
        while (finished != 0) {
          
            //Executing without scheduling leads to spiky behaviour, caused by a long taks queue?
            loadExec.schedule(new CBCombinedLoadBenchmark(),100, TimeUnit.MILLISECONDS);
            finished--;
        } 
        
        ExecutorFactory.destroy(loadExec);
    }
    
}
