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

import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReplicateTo;
import java.io.IOException;

/**
 * The Couchbase configuration
 * 
 * @author david.maier@couchbase.com
 */
public class CouchbaseConfig extends BaseConfig {

    public static final String FILE_NAME = "cb.properties";

    
    public enum OpType {
        DELETE, SET, GET, VIEW, N1QL
    }
    
    
    public CouchbaseConfig() throws IOException {
        super(FILE_NAME);
    }

    public String[] getHosts() {
         
        String hostsStr = getOverrideOrDefault("cb.con.hosts");
       
        return hostsStr.split(",");
    }
    
    public int getPort() {

        String portStr = getOverrideOrDefault("cb.con.port");
        return Integer.parseInt(portStr);
    }
    
    
    public String getBucket() {
        
        return getOverrideOrDefault("cb.con.bucket.name");
        
    }
    
    public String getPassword() {
     
        return getOverrideOrDefault("cb.con.bucket.pwd"); 
    }
    
    
    public int getRetryWait() {
        
        String retryStr = this.props.getProperty("cb.op.retry.wait");
        return Integer.parseInt(retryStr);  
    }
    
    public int getRetryMax() {
        
        String retryStr = this.props.getProperty("cb.op.retry.max");
        return Integer.parseInt(retryStr);  
    }
   
    
    public int getNumEndpoints() {
        
        return Integer.parseInt(this.props.getProperty("cb.con.endpoints"));
        
    }
    
    public PersistTo getPersistTo() {
     
        String persistTo = this.props.getProperty("cb.observe.persist");
        int asNum = Integer.parseInt(persistTo);
        
        PersistTo result = PersistTo.NONE;
        
        switch (asNum) {
            
            case -1:
                result = PersistTo.MASTER;
                break;
            case 0:
                result =  PersistTo.NONE;
                break;
            case 1:
                result = PersistTo.ONE;
                break;
            case 2:
                result = PersistTo.TWO;
                break;
            case 3:
                result = PersistTo.THREE;
                break;
            case 4: 
                result = PersistTo.FOUR;
                break;
        }
        
        return result;
    } 
    
    public ReplicateTo getReplicateTo() {
        
        String replicateTo = this.props.getProperty("cb.observe.replicate");
        int asNum = Integer.parseInt(replicateTo);
        
        ReplicateTo result = ReplicateTo.NONE;
        
        switch (asNum) {
            
            case 0:
                result =  ReplicateTo.NONE;
                break;
            case 1:
                result = ReplicateTo.ONE;
                break;
            case 2:
                result = ReplicateTo.TWO;
                break;
            case 3:
                result = ReplicateTo.THREE;
                break;
        }
        
        return result; 
    }
    
    public int getOpTimeout(OpType type) {
         
         String param = "";
         
         switch (type) {
            case SET:
                param = "cb.op.set.timeout"; 
                break;
                    
            case GET:
                param = "cb.op.get.timeout";
                break;
                         
            case DELETE:
                param = "cb.op.del.timeout";
                break;
                
            case VIEW:
                param = "cb.op.view.timeout";
                break;
                
            case N1QL:
                param = "cb.op.query.timeout";
                break;
                        
            default:
                param = "cb.op.get.timeout";
                break;
        }
       
        String paramValue = this.props.getProperty(param);
        return Integer.parseInt(paramValue);
      
     }    
}
