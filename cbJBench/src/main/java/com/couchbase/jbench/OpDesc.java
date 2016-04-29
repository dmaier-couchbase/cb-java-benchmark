
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


/**
 * Describes how an operation is looking like
 * 
 * @author david.maier@couchbase.com
 */
public class OpDesc {

     public enum OpType {
        INSERT, UPSERT, REPLACE, GET
     }
     
     public enum DocType {
         
         BINARY, JSON, NONE
     }
       
    private final OpType type;
    private final String key;
    private final DocType docType;
    private final Object value;
    
    
    public OpDesc(OpType type, String key, DocType docType) {
        
        this(type, key, docType, null);
    }
    
    public OpDesc(OpType type, String key, DocType docType, Object value) {
        
        this.type = type;
        this.key = key;
        this.docType = docType;
        this.value = value;
        
    }

    public String getKey() {
        return key;
    }

    public OpType getType() {
        return type;
    }

    public DocType getDocType() {
        return docType;
    }

    public Object getValue() {
        return value;
    }
    
}
