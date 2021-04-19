# Distributed Locking in Kubernetes

This repository presents different approaches to using distributed locking in the Kubernetes environment.

## Single-instance

This approach presents useing a single instance of database / caching server as lock manager.

You can build your own image with the following command. If you skip this step you'll use the image `leszko/distributed-locking:hazelcast`.

```
./gradlew build
docker build -t <YOUR-USERNAME>/distributed-locking:hazelcast .
```

Deploy Lock Manager and Application.
```
kubectl run hazelcast --image hazelcast/hazelcast:4.2 --port 5701 --expose
kubectl run application --image leszko/distributed-locking:single-instance

kubectl delete pod application
kubectl delete service hazelcast
kubectl delete pod hazelcast
```

## Multi-instance

This approach presents using multiple instances of database / caching server as lock manager.

You can build your own image with the following command. If you skip this step you'll use the image `leszko/distributed-locking:hazelcast`.

```
./gradlew build
docker build -t <YOUR-USERNAME>/distributed-locking:hazelcast .
```
Deploy Lock Manager and Application.

```
helm repo add hazelcast https://hazelcast-charts.s3.amazonaws.com/
helm install hazelcast hazelcast/hazelcast --version 3.7.0
kubectl run application --image leszko/distributed-locking:hazelcast

kubectl delete pod application
helm uninstall hazelcast
```

## RedLock

This approach presents using RedLock.

You can build your own image with the following command. If you skip this step you'll use the image `leszko/distributed-locking:redlock`.

```
./gradlew build -PmainClass=com.rafalleszko.locking.RedLock
docker build -t <YOUR-USERNAME>/distributed-locking:redlock .
```

Deploy Lock Manager and Application.

```
kubectl run redis-1 --image redis:6.2.1 --port 6379 --expose
kubectl run redis-2 --image redis:6.2.1 --port 6379 --expose
kubectl run redis-3 --image redis:6.2.1 --port 6379 --expose

kubectl run application --image leszko/distributed-locking:redlock

kubectl delete pod application
kubectl delete service redis-1 redis-2 redis-3
kubectl delete pod redis-1 redis-2 redis-3
```

## Consensus-Based

This approach presents using multiple instances with consensus algorithm as lock manager.

You can build your own image with the following command. If you skip this step you'll use the image `leszko/distributed-locking:hazelcast`.

```
./gradlew build
docker build -t <YOUR-USERNAME>/distributed-locking:hazelcast .
```

Deploy Lock Manager and Application.

```
helm repo add hazelcast https://hazelcast-charts.s3.amazonaws.com/
helm install hazelcast hazelcast/hazelcast --version 3.7.0 --set hazelcast.yaml.hazelcast.cp-subsystem.cp-member-count=3
kubectl run application --image leszko/distributed-locking:single-instance

kubectl delete pod application
helm uninstall hazelcast
```

## Fenced

This approach presents using multiple instances with consensus algorithm and fencing token as lock manager.

You can build your own image with the following command. If you skip this step you'll use the image `leszko/distributed-locking:hazelcast`.

```
./gradlew build -PmainClass=com.rafalleszko.locking.Fenced
docker build -t <YOUR-USERNAME>/distributed-locking:fenced .
```

Deploy Lock Manager and Application.

```
helm repo add hazelcast https://hazelcast-charts.s3.amazonaws.com/
helm install hazelcast hazelcast/hazelcast --version 3.7.0 --set hazelcast.yaml.hazelcast.cp-subsystem.cp-member-count=3
kubectl run application --image leszko/distributed-locking:fenced

kubectl delete pod application
helm uninstall hazelcast
```