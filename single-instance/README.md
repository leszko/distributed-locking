# Single-instance

```
docker build -t leszko/distributed-locking:single-instance .
kubectl run hazelcast --image hazelcast/hazelcast:4.2 --port 5701 --expose
kubectl run application --image leszko/distributed-locking:single-instance

kubectl delete pod application
kubectl delete service hazelcast
kubectl delete pod hazelcast
```

# Multi-instance

```
helm repo add hazelcast https://hazelcast-charts.s3.amazonaws.com/
helm install hazelcast hazelcast/hazelcast --version 3.7.0
kubectl run application --image leszko/distributed-locking:single-instance

kubectl delete pod application
helm uninstall hazelcast
```

# RedLock

```
docker build -t leszko/distributed-locking:redlock -f Dockerfile.redlock .

kubectl run redis-1 --image redis:6.2.1 --port 6379 --expose
kubectl run redis-2 --image redis:6.2.1 --port 6379 --expose
kubectl run redis-3 --image redis:6.2.1 --port 6379 --expose

kubectl run application --image leszko/distributed-locking:redlock

kubectl delete pod application
kubectl delete service redis-1 redis-2 redis-3
kubectl delete pod redis-1 redis-2 redis-3
```

# Consensus-Based Distributed Locking

```
helm repo add hazelcast https://hazelcast-charts.s3.amazonaws.com/
helm install hazelcast hazelcast/hazelcast --version 3.7.0 --set hazelcast.yaml.hazelcast.cp-subsystem.cp-member-count=3
kubectl run application --image leszko/distributed-locking:single-instance

kubectl delete pod application
helm uninstall hazelcast
```

