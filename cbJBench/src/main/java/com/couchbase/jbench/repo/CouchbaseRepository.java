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

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonLongDocument;
import com.couchbase.jbench.OpDesc.*;
import com.couchbase.jbench.TypeMap;
import com.couchbase.jbench.config.ConfigManager;
import com.couchbase.jbench.config.CouchbaseConfig;
import com.couchbase.jbench.conn.BucketFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import rx.Observable;

/**
 * The Couchbase Repo
 * 
 * @author david.maier@couchbase.com
 */
public class CouchbaseRepository implements Repository {

    public static final Logger LOG = Logger.getLogger(CouchbaseRepository.class.getName());
    
    final AsyncBucket bucket = BucketFactory.getAsyncBucket();
    final CouchbaseConfig CFG = ConfigManager.getCBConfig();
    final TypeMap TYPES = new TypeMap();
    final ErrorHandler ERROR_FUNC = new ErrorHandler();

    
    @Override
    public Observable<Document> insert(Document doc) {
       
       
        return Observable.defer(() -> bucket.insert(doc, CFG.getPersistTo(), CFG.getReplicateTo())
                .timeout(CFG.getOpTimeout(CouchbaseConfig.OpType.SET), TimeUnit.MILLISECONDS)
                .onErrorReturn(ERROR_FUNC));
    }

    @Override
    public Observable<Document> upsert(Document doc) {

        return Observable.defer(() -> bucket.upsert(doc, CFG.getPersistTo(), CFG.getReplicateTo())
                .timeout(CFG.getOpTimeout(CouchbaseConfig.OpType.SET), TimeUnit.MILLISECONDS)
                .onErrorReturn(ERROR_FUNC));
        
    }

    @Override
    public Observable<Document> replace(Document doc) {
        
        return Observable.defer(() -> bucket.replace(doc, CFG.getPersistTo(), CFG.getReplicateTo())
                .timeout(CFG.getOpTimeout(CouchbaseConfig.OpType.SET), TimeUnit.MILLISECONDS)
                .onErrorReturn(ERROR_FUNC));
    }

    @Override
    public Observable<Document> get(String key, DocType type) {
      
        return Observable.defer(() -> bucket.get(key, TYPES.get(type) )
                .timeout(CFG.getOpTimeout(CouchbaseConfig.OpType.SET), TimeUnit.MILLISECONDS)
                .onErrorReturn(ERROR_FUNC));
    }

    @Override
    public Observable<JsonLongDocument> incr(String key) {
 
        return Observable.defer(() -> bucket.counter(key, 1, CFG.getPersistTo(), CFG.getReplicateTo())
                .timeout(CFG.getOpTimeout(CouchbaseConfig.OpType.SET), TimeUnit.MILLISECONDS)
                .onErrorReturn(e -> {LOG.severe(e.getMessage());return null;}));
    }
    
    
    
}

