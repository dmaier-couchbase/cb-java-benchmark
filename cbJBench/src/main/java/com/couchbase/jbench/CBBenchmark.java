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

import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import com.couchbase.client.java.document.BinaryDocument;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.jbench.OpDesc.OpType;
import com.couchbase.jbench.OpDesc.DocType;
import com.couchbase.jbench.repo.CouchbaseRepository;
import com.couchbase.jbench.repo.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import rx.Observable;

/**
 * A Couchbase benchmark provides access to the Couchbase repo
 * 
 * 
 * @author david.maier@couchbase.com
 */
public abstract class CBBenchmark extends BaseBenchmark {
    
    protected final Repository repo = new CouchbaseRepository();
     
    /**
     * Prepare a batch
     * 
     * @param size
     * @param opType
     * @param docType
     * @return 
     */
    protected List<OpDesc> prepareBatch(int size, OpType opType, DocType docType) {
        
        
        LOG.log(Level.INFO, "Preparing batch {0}", Counters.COUNT_BATCHES.incrementAndGet());
        
        List<OpDesc> batch = new ArrayList<>();
        
        for (int i = 0; i < size; i++) {
    
            Document doc = null;
            String key = genId();
            
            if (opType != OpType.GET) {
            
                if (docType == DocType.BINARY) {
                
                    ByteBuf toWrite = Unpooled.copiedBuffer(randomStrValue(), CharsetUtil.US_ASCII);
                    doc = BinaryDocument.create(key, toWrite );
                
                }
            
                if (docType == DocType.JSON) {
                
                    doc = JsonDocument.create(key, createJsonObj());
                
                }
            }
                 
            batch.add(new OpDesc(opType, key, docType, doc));
           
        }
        
        return batch;
    }
   
   /**
    * Generate a Couchbase document id
    * 
    * @return 
    */
   abstract protected String genId();
    
    
    /**
     * Create a JsonObject
     * 
     * @return 
     */
    protected JsonObject createJsonObj() {
        
        String value = randomStrValue();
        
        JsonObject json = JsonObject.create();
        json.put("value", value);
        
        return json;
    }
       
    /**
     * Generate a random string value by taking the min and max size into account
     * 
     * @return 
     */
    protected String randomStrValue() {
        
        StringBuilder value  = new StringBuilder();
        
        Random random = new Random();
        int size = random.nextInt(BMCFG.getMaxSize() - BMCFG.getMinSize()) + BMCFG.getMinSize();
    
        for (int i = 0; i < size; i++) {

            char rChar = (char)(random.nextInt(26) + 'a');
            value.append(rChar);   
        }
        
        return value.toString();
    }
    
    /**
     * Execute an operation based on the operation description
     * 
     * @param desc
     * @return 
     */
    protected Observable<Document> execOp(OpDesc desc) {
        
        Observable<Document> result = null;
        
        Document doc = null;
        
        if (desc.getValue() != null) {
        
            if (desc.getDocType() == DocType.BINARY) doc = (BinaryDocument) desc.getValue();
            if (desc.getDocType() == DocType.JSON) doc = (JsonDocument) desc.getValue();
        
        }
        
         switch (desc.getType()) {
            
             case INSERT:
                             
                 result = repo.insert(doc);
                 
             break;
             
             case UPSERT:
                 
                 result = repo.upsert(doc);
                 
             break;
             
             case REPLACE:
                 
                 result = repo.replace(doc);
                 
             break;
             
             case GET:
                 
                 result = repo.get(desc.getKey(), desc.getDocType());
             
             break;
            
         }
         
         return result;
    }     
}
