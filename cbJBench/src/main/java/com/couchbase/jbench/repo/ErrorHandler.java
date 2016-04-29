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

package com.couchbase.jbench.repo;

import com.couchbase.client.java.document.Document;
import rx.functions.Func1;

/**
 * The default error handler
 * 
 * @author david.maier@couchbase.com
 */
public class ErrorHandler implements Func1<Throwable, Document> {

    
    @Override
    public Document call(Throwable t) {
        
        
         CouchbaseRepository.LOG.severe(t.toString());
         t.printStackTrace();
         return null;    
    }
    
}
