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
helm install hazelcast --version 3.7.0 hazelcast/hazelcast
kubectl run application --image leszko/distributed-locking:single-instance

kubectl delete pod application
helm uninstall hazelcast
```

# RedLock

```
docker build -t leszko/distributed-locking:redlock -f Dockerfile.redlock .

kubectl run redis-1 --image redis:6.2.1 --port 7181 --expose
kubectl run redis-2 --image redis:6.2.1 --port 7181 --expose
kubectl run redis-3 --image redis:6.2.1 --port 7181 --expose

kubectl run application --image leszko/distributed-locking:redlock

```