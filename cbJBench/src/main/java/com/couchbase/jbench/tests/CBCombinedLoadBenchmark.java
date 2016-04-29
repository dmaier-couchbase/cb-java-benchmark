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
import java.util.Random;
import java.util.logging.Level;
import rx.Observable;

/**
 * Performs one batch of operations as a combined workload
 * 
 * @author david
 */
public class CBCombinedLoadBenchmark extends CBBenchmark {

    @Override
    protected void init() {
        
        super.init();
        if (Counters.COUNT_DOCS.get() == 0) Counters.COUNT_DOCS.set(BMCFG.getNumItems());
    }


    @Override
    protected void exec() {
        
        int numWrites = Math.round((float) BMCFG.getBatchSize() * 0.01f * (float) BMCFG.getPercentage());
        int numReads = BMCFG.getBatchSize() - numWrites;
        
        LOG.log(Level.INFO, "Executing {0} writes per batch", numWrites);
        LOG.log(Level.INFO, "Executing {0} reads per batch", numReads); 
        
       Observable<OpDesc> batch = Observable.from(prepareBatch(numReads, OpDesc.OpType.GET, BMCFG.getDocType()))
               .mergeWith(Observable.from(prepareBatch(numWrites, OpDesc.OpType.UPSERT, BMCFG.getDocType())));
                
               
       batch.flatMap(opD -> execOp(opD)).toBlocking().last();
                
    }

    /**
     * Pick a random id of a previously loaded document
     * 
     * @return 
     */
    @Override
    protected String genId() {
       
         StringBuilder sb = new StringBuilder(BMCFG.getPrefix());
         
         final int MAX = Counters.COUNT_DOCS.get();
         
         //Random value between 1 and MAX
         Random random = new Random();
         int value = random.nextInt(MAX - 1) + 1;

         sb.append(value);
         
        return sb.toString();
        
    }
    
}
