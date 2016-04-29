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

import com.couchbase.jbench.OpDesc;
import java.io.IOException;

/**
 * The benchmark config
 * 
 * @author david.maier@couchbase.com
 */
public class BenchmarkConfig extends BaseConfig {
    
    public static final String FILE_NAME = "benchmark.properties";
    
    public BenchmarkConfig() throws IOException {
        super(FILE_NAME);
    }
    
    public int getBatchSize() {
        
        return Integer.parseInt(getOverrideOrDefault("bm.batchsize"));
    }
    
    public int getNumItems() {
        
        return Integer.parseInt(getOverrideOrDefault("bm.numitems"));
    }
    
    public String getPrefix() {
        
        return getOverrideOrDefault("bm.prefix");
    }
    
    public int getPercentage() {
        
        return Integer.parseInt(getOverrideOrDefault("bm.percentage"));
    }
    
    public boolean isPopulationSkipped() {
        
        return Boolean.parseBoolean(getOverrideOrDefault("bm.nopopulation"));
    }
    
    public OpDesc.DocType getDocType() {
        
        OpDesc.DocType result = OpDesc.DocType.NONE;
        
        String docTypeStr = getOverrideOrDefault("bm.doctype");
        
        if ("binary".equals(docTypeStr))  result = OpDesc.DocType.BINARY;
        if ("json".equals(docTypeStr)) result = OpDesc.DocType.JSON;
        
        return result;
    }
    
    public int getMinSize() {
        
        return Integer.parseInt(getOverrideOrDefault("bm.minsize"));
    }
    
    public int getMaxSize() {
        
        return Integer.parseInt(getOverrideOrDefault("bm.maxsize"));
    }

    public int getCycles() {
     
        return Integer.parseInt(getOverrideOrDefault("bm.cycles"));
    }
    
    public int getNumThreads() {
     
        return Integer.parseInt(getOverrideOrDefault("bm.threads"));
    }
}
