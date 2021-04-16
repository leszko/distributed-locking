```
docker build -t leszko/distributed-locking:single-instance .
kubectl run hazelcast --image hazelcast/hazelcast:4.2 --port 5701 --expose
kubectl run application --image leszko/distributed-locking:single-instance

kubectl delete pod application
kubectl delete service hazelcast
kubectl delete pod hazelcast
```