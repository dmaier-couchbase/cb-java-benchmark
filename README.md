# cbJBench
A Java benchmark tool for Couchbase

## How to use

The basic benchmark configuration can be done via the files 'benchmark.properties' and 'cb.properties'.

* cb.properties
```
#Connection properties
cb.con.hosts=ubuntu-server
cb.con.port=8091
cb.con.bucket.name=test
cb.con.bucket.pwd=test
cb.con.timeout=2000
cb.con.endpoints=8

#Operation timeouts
cb.op.get.timeout=200
cb.op.set.timeout=200
cb.op.del.timout=200
cb.op.view.timeout=5000
cb.op.query.timeout=5000

#Operation retry
cb.op.retry.wait=500
cb.op.retry.max=2


#Persist params
cb.observe.persist=0
cb.observe.replicate=0

```

> The tool is still under development and so not all params might be used yet

* benchmark.properties

```
# Batch size
bm.batchsize=1000
# Number of items to work on
bm.numitems=1000000
# Document prefix
bm.prefix=bm::
#0 = only reads, 100 = only writes
bm.percentage=50
# Skip preload phase? Only use with a bucket which was filled by an earlier run!
bm.nopopulation=true
# binary or json
bm.doctype=binary
# Document sizes
bm.minsize=1200
bm.maxsize=1500
#-1 = forever
bm.cycles=1000000
# Number of threads executing batches
bm.threads=8
```

The entry point of the benchmark is the class:

* com.couchbase.jbench.Start


