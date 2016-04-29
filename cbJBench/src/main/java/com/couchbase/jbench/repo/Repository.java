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
import com.couchbase.client.java.document.JsonLongDocument;
import com.couchbase.jbench.OpDesc.*;
import rx.Observable;

/**
 * Describes a repo
 * 
 * @author david.maier@couchbase.com
 */
public interface Repository {
    
    public Observable<Document> insert(Document doc);
    public Observable<Document> upsert(Document doc);
    public Observable<Document> replace(Document doc);
    public Observable<Document> get(final String key, DocType type);
    public Observable<JsonLongDocument> incr(final String key);
}
