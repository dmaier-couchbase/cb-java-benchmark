
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

import com.couchbase.client.java.document.BinaryDocument;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import java.util.HashMap;

/**
 * Mapping to document classes
 * 
 * @author david.maier@couchbase.com
 */
public class TypeMap extends HashMap<OpDesc.DocType, Class>{

    public TypeMap() {
    
        this.put(OpDesc.DocType.BINARY, BinaryDocument.class);
        this.put(OpDesc.DocType.JSON, JsonDocument.class);
        this.put(OpDesc.DocType.NONE, Document.class);
    }
    
}
