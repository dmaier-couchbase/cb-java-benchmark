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

package com.couchbase.jbench.tests;

import com.couchbase.jbench.CBBenchmark;
import com.couchbase.jbench.Counters;
import com.couchbase.jbench.OpDesc;
import rx.Observable;
import com.couchbase.jbench.OpDesc.OpType;
import java.util.logging.Level;

/**
 * Populates per configured thread a given number of documents
 * 
 * @author david
 */
public class CBPreLoadBenchmark extends CBBenchmark {

    /**
     * Prepare 
     */
    @Override
    protected void exec() {
        
        int numDocsPerThread = BMCFG.getNumItems() / BMCFG.getNumThreads();
        int numBatches = numDocsPerThread / BMCFG.getBatchSize();
        
        LOG.log(Level.INFO, "Using #numDocsPerThread: {0}", numDocsPerThread);
        LOG.log(Level.INFO, "Processing #batches: {0}", numBatches);

        for (int i = 0; i < numBatches; i++) {
                
            Observable<OpDesc> batch = Observable.from(
                    prepareBatch(BMCFG.getBatchSize(), OpType.INSERT, BMCFG.getDocType())
            );
                   
            
            batch.flatMap(opD -> execOp(opD)).toBlocking().last(); 
        }
        
    }


    /**
     * Generate the next document id
     * 
     * @return 
     */
    @Override
    protected String genId() {
        
        StringBuilder sb = new StringBuilder(BMCFG.getPrefix());
        sb.append(Counters.COUNT_DOCS.incrementAndGet());
        
        return sb.toString();
    }
    
}
