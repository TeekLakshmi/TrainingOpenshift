# Day 4

## Lab - Deploy custom spring-boot application from source code into OpenShift cluster
```
https://github.com/tektutor/spring-ms
```

## Lab - Deploying an application from CLI using source strategy
You can use any container image that suits your application from the Red Hat Container Image portal
https://catalog.redhat.com/software/containers/search

```
oc new-app registry.access.redhat.com/ubi8/openjdk-11~https://github.com/tektutor/spring-ms.git --strategy=source
```
Expected output
<pre>
[jegan@tektutor.org spring-ms]$ oc new-app registry.access.redhat.com/ubi8/openjdk-11~https://github.com/tektutor/spring-ms.git --strategy=source
--> Found container image a6b53e1 (4 weeks old) from registry.access.redhat.com for "registry.access.redhat.com/ubi8/openjdk-11"

    Java Applications 
    ----------------- 
    Platform for building and running plain Java applications (fat-jar and flat classpath)

    Tags: builder, java

    * An image stream tag will be created as "openjdk-11:latest" that will track the source image
    * A source build using source code from https://github.com/tektutor/spring-ms.git will be created
      * The resulting image will be pushed to image stream tag "spring-ms:latest"
      * Every time "openjdk-11:latest" changes a new build will be triggered

--> Creating resources ...
    imagestream.image.openshift.io "openjdk-11" created
    imagestream.image.openshift.io "spring-ms" created
    buildconfig.build.openshift.io "spring-ms" created
    deployment.apps "spring-ms" created
    service "spring-ms" created
--> Success
    Build scheduled, use 'oc logs -f buildconfig/spring-ms' to track its progress.
    Application is not exposed. You can expose services to the outside world by executing one or more of the commands below:
     'oc expose service/spring-ms' 
    Run 'oc status' to view your app.  
</pre>

You may check the logs as shown below
```
oc logs -f bc/spring-ms
```

You need to create a route
```
oc get svc
oc expose svc/spring-ms
oc get route
```

Now from the webconsole --> Developer context -> topology you can click on the route link(arrow point upward) to access the route public url to see the web page.


## Lab - Deploying an appliction from github using Docker strategy from CLI
```
oc new-app https://github.com/tektutor/spring-ms.git --strategy=docker
```

Expected output
<pre>
[jegan@tektutor.org]$ oc new-app https://github.com/tektutor/spring-ms.git --strategy=docker
--> Found container image a6b53e1 (4 weeks old) from registry.access.redhat.com for "registry.access.redhat.com/ubi8/openjdk-11"

    Java Applications 
    ----------------- 
    Platform for building and running plain Java applications (fat-jar and flat classpath)

    Tags: builder, java

    * An image stream tag will be created as "openjdk-11:latest" that will track the source image
    * A Docker build using source code from https://github.com/tektutor/spring-ms.git will be created
      * The resulting image will be pushed to image stream tag "spring-ms:latest"
      * Every time "openjdk-11:latest" changes a new build will be triggered

--> Creating resources ...
    imagestream.image.openshift.io "openjdk-11" created
    imagestream.image.openshift.io "spring-ms" created
    buildconfig.build.openshift.io "spring-ms" created
    deployment.apps "spring-ms" created
    service "spring-ms" created
--> Success
    Build scheduled, use 'oc logs -f buildconfig/spring-ms' to track its progress.
    Application is not exposed. You can expose services to the outside world by executing one or more of the commands below:
     'oc expose service/spring-ms' 
    Run 'oc status' to view your app.  
</pre>

You can check the application build progress as shown below
```
oc logs -f bc/spring-ms
```

Expected output
<pre>
jegan@tektutor.org]$ oc logs -f bc/spring-ms
Cloning "https://github.com/tektutor/spring-ms.git" ...
	Commit:	82552fb8a8eb3a7cc7e8165b8878dc5e47e50db3 (Renamed deploy.yml to deploy.yaml)
	Author:	Jeganathan Swaminathan <mail2jegan@gmail.com>
	Date:	Wed Feb 15 15:11:17 2023 +0530
Replaced Dockerfile FROM image registry.access.redhat.com/ubi8/openjdk-11
time="2024-03-21T11:33:49Z" level=info msg="Not using native diff for overlay, this may cause degraded performance for building images: kernel has CONFIG_OVERLAY_FS_REDIRECT_DIR enabled"
I0321 11:33:49.342803       1 defaults.go:112] Defaulting to storage driver "overlay" with options [mountopt=metacopy=on].
Caching blobs under "/var/cache/blobs".

Pulling image docker.io/maven:3.6.3-jdk-11 ...
Trying to pull docker.io/library/maven:3.6.3-jdk-11...
Getting image source signatures
Copying blob sha256:6c215442f70bd949a6f2e8092549943905e2d4f9c87a4f532d7740ae8647d33a
Copying blob sha256:5d6f1e8117dbb1c6a57603cb4f321a861a08105a81bcc6b01b0ec2b78c8523a5
Copying blob sha256:234b70d0479d7f16d7ee8d04e4ffdacc57d7d14313faf59d332f18b2e9418743
Copying blob sha256:004f1eed87df3f75f5e2a1a649fa7edd7f713d1300532fd0909bb39cd48437d7
Copying blob sha256:48c2faf66abec3dce9f54d6722ff592fce6dd4fb58a0d0b72282936c6598a3b3
Copying blob sha256:d7eb6c022a4e6128219b32a8e07c8c22c89624ff440ebac1506121794bc15ccc
Copying blob sha256:355e8215390faee903502a9fddfc65cd823f1606f053376ba2575adce66974a1
Copying blob sha256:cf5eb43522f68d7e2347e19ad70dadcf1594d25b792ede0464c2936ff902c4c6
Copying blob sha256:4fee0489a65b64056f81358639bfe85fd87776630830fd02ce8c15e34928bf9c
Copying blob sha256:413646e6fa5d7bcd9722d3e400fc080a77deb505baed79afa5fedae23583af25
Copying config sha256:e23b595c92ada5c9f20a27d547ed980a445f644eb1cbde7cfb27478fa38c4691
Writing manifest to image destination

Pulling image registry.access.redhat.com/ubi8/openjdk-11@sha256:45cd320e359633795b9166e397c2b07e186b7fec7f6dacce3c52ecab8cba8023 ...
Trying to pull registry.access.redhat.com/ubi8/openjdk-11@sha256:45cd320e359633795b9166e397c2b07e186b7fec7f6dacce3c52ecab8cba8023...
Getting image source signatures
Copying blob sha256:0bb48edf8994fcf133c612f92171d68f572091fb0b1113715eab5f3e5e7f54e5
Copying blob sha256:74e0c06e5eac269967e6970582b9b25168177df26dffed37ccde09369302a87a
Copying config sha256:a6b53e10c7678edc1d2e8090ed0a0b40d147f8e110ac2277931828ef11276f96
Writing manifest to image destination
Adding transient rw bind mount for /run/secrets/rhsm
[1/2] STEP 1/3: FROM docker.io/maven:3.6.3-jdk-11 AS stage1
[1/2] STEP 2/3: COPY . .
--> 5ed50734e87f
[1/2] STEP 3/3: RUN mvn clean package
[INFO] Scanning for projects...
Downloading from central: https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-parent/2.4.2/spring-boot-starter-parent-2.4.2.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-parent/2.4.2/spring-boot-starter-parent-2.4.2.pom (8.6 kB at 20 kB/s)

[2/2] STEP 6/6: LABEL "io.openshift.build.commit.author"="Jeganathan Swaminathan <mail2jegan@gmail.com>" "io.openshift.build.commit.date"="Wed Feb 15 15:11:17 2023 +0530" "io.openshift.build.commit.id"="82552fb8a8eb3a7cc7e8165b8878dc5e47e50db3" "io.openshift.build.commit.message"="Renamed deploy.yml to deploy.yaml" "io.openshift.build.commit.ref"="master" "io.openshift.build.name"="spring-ms-1" "io.openshift.build.namespace"="jegan" "io.openshift.build.source-location"="https://github.com/tektutor/spring-ms.git"
[2/2] COMMIT temp.builder.openshift.io/jegan/spring-ms-1:565d9d19
--> 5148c0e02824
Successfully tagged temp.builder.openshift.io/jegan/spring-ms-1:565d9d19
5148c0e02824b36eb1a1f4ae90898cd7c0f3391babf8330ba2a5cd8682220af7

Pushing image image-registry.openshift-image-registry.svc:5000/jegan/spring-ms:latest ...
Getting image source signatures
Copying blob sha256:4328970bb17139aa835c745ad0d72072f5b4721c3da9ca602f772c39453e5bc8
Copying blob sha256:0bb48edf8994fcf133c612f92171d68f572091fb0b1113715eab5f3e5e7f54e5
Copying blob sha256:74e0c06e5eac269967e6970582b9b25168177df26dffed37ccde09369302a87a
Copying config sha256:5148c0e02824b36eb1a1f4ae90898cd7c0f3391babf8330ba2a5cd8682220af7
Writing manifest to image destination
Successfully pushed image-registry.openshift-image-registry.svc:5000/jegan/spring-ms@sha256:1c8742b631b1ddaf8ad5508dc5a0855684212e1bc12baf2f711da4cd94d099cd
Push successful
</pre>

You need to create a route
```
oc get svc
oc expose svc/spring-ms
oc get route
```
Access the route url from the Openshift Webconsole -> Developer context --> Topology

## Lab - Deploying an application from a Custom Docker Image from Docker Hub
```
oc new-app --image=tektutor/spring-ms:1.0
oc get svc
oc expose svc/spring-ms
```

Expected output
<pre>
[jegan@tektutor.org]$ oc new-app --image=tektutor/spring-ms:1.0
--> Found container image 9175b94 (19 months old) from Docker Hub for "tektutor/spring-ms:1.0"

    * An image stream tag will be created as "spring-ms:1.0" that will track this image

--> Creating resources ...
    imagestream.image.openshift.io "spring-ms" created
    deployment.apps "spring-ms" created
    service "spring-ms" created
--> Success
    Application is not exposed. You can expose services to the outside world by executing one or more of the commands below:
     'oc expose service/spring-ms' 
    Run 'oc status' to view your app.
	
[jegan@tektutor.org spring-ms]$ oc get svc
NAME        TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)    AGE
spring-ms   ClusterIP   172.30.238.230   <none>        8080/TCP   13s
	
[jegan@tektutor.org spring-ms]$ oc expose svc/spring-ms
route.route.openshift.io/spring-ms exposed
	
[jegan@tektutor.org spring-ms]$ oc get route
NAME        HOST/PORT                                    PATH   SERVICES    PORT       TERMINATION   WILDCARD
spring-ms   spring-ms-jegan.apps.ocp.tektutor.org.labs          spring-ms   8080-tcp                 None
	
[jegan@tektutor.org spring-ms]$ curl spring-ms-jegan.apps.ocp.tektutor.org.labs
Greetings from Spring Boot!	
</pre>

## Lab - Create an edge route (https based public route url)

Find your base domain of your openshift cluster
```
oc get ingresses.config/cluster -o jsonpath={.spec.domain}
```

Expected output
<pre>
[root@tektutor.org auth]# oc get ingresses.config/cluster -o jsonpath={.spec.domain}
apps.ocp.tektutor.org.labs	
</pre>

Installing openssl from source code ( Already installed on Lab machines, so kindly skip this installation)
```
sudo yum -y remove openssl openssl-devel
sudo yum groupinstall 'Development Tools'
sudo yum install perl-IPC-Cmd perl-Test-Simple -y
cd /usr/src
wget https://www.openssl.org/source/openssl-3.0.0.tar.gz
tar -zxf openssl-3.0.0.tar.gz
rm openssl-3.0.0.tar.gz
cd /usr/src/openssl-3.0.0
./config
make
make test
make install

sudo ln -s /usr/local/lib64/libssl.so.3 /usr/lib64/libssl.so.3
sudo ln -s /usr/local/lib64/libcrypto.so.3 /usr/lib64/libcrypto.so.3

sudo ldconfig
sudo tee /etc/profile.d/openssl.sh<<EOF
export PATH=/usr/local/bin:$PATH
export LD_LIBRARY_PATH=/usr/local/openssl/lib:/usr/local/openssl/lib64:\$LD_LIBRARY_PATH
EOF

which openssl
openssl version
```

Let's deploy a microservice and create an edge route as shown below.

First, let's generate a private key
```
openssl genrsa -out key.key
```

We need to create a public key using the private key with specific with your organization domain
```
openssl req -new -key key.key -out csr.csr -subj="/CN=hello-jegan.apps.ocp.tektutor.org.labs"
```

Sign the public key using the private key and generate certificate(.crt)
```
openssl x509 -req -in csr.csr -signkey key.key -out crt.crt
oc create route edge --service spring-ms --hostname hello-jegan.apps.ocp4.tektutor.org.labs --key key.key --cert crt.crt
```

Expected output
<pre>
[jegan@tektutor.org edge-route]$ oc get svc
NAME        TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
spring-ms   ClusterIP   172.30.208.33   <none>        8080/TCP   87m
[jegan@tektutor.org edge-route]$ oc expose deploy/nginx --port=8080
service/nginx exposed
  
[jegan@tektutor.org edge-route]$ oc get svc
NAME        TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)    AGE
nginx       ClusterIP   172.30.16.165   <none>        8080/TCP   4s
spring-ms   ClusterIP   172.30.208.33   <none>        8080/TCP   87m

[jegan@tektutor.org edge-route]$ oc get ingresses.config/cluster -o jsonpath={.spec.domain}
apps.ocp4.tektutor.org.labs
  
[jegan@tektutor.org edge-route]$ oc project
Using project "jegan-devops" on server "https://api.ocp4.tektutor.org.labs:6443".
  
[jegan@tektutor.org edge-route]$ openssl req -new -key key.key -out csr.csr -subj="/CN=nginx-jegan-devops.apps.ocp4.tektutor.org.labs"
  
[jegan@tektutor.org edge-route]$ openssl x509 -req -in csr.csr -signkey key.key -out crt.crt
  
[jegan@tektutor.org edge-route]$ oc create route edge --service nginx --hostname nginx-jegan-devops.apps.ocp4.tektutor.org.labs --key key.key --cert crt.crt
route.route.openshift.io/nginx created
  
[jegan@tektutor.org edge-route]$ oc get route
NAME    HOST/PORT                                        PATH   SERVICES   PORT    TERMINATION   WILDCARD
nginx   nginx-jegan-devops.apps.ocp4.tektutor.org.labs   nginx      <all>   edge          None
</pre>

## Lab - Scheduling pods matching a specific node criteria
There are 2 types of Node Affinity possible
1. Preferred During Scheduling Ignored During Execution
2. Required During Scheduling Ignored During Execution

Preferred 
- If the scheduler will try to find a Node that matches the preferred criteria, if it find then it would deploy the Pod onto the node that matches the preferred condition
- If the scheduler is not able to find a Node that maches the preferred condition, then it will still deploy onto some node even though that doesn't meet that criteria
- Use cases
  - if the Pod prefers to run on a node that has SSD disk
  - if a .Net application prefers to run on a Windows Compute Node in OpenShift cluster

First if you can if there are any nodes that has disk=ssd label
```
oc get nodes -l disk=ssd
```

Assuming, there are no nodes that has the disk=ssd label, let's proceed.

```
cd ~/openshift-april-2024
git pull

cd Day4/scheduling
oc apply -f preferred-affinity.yml
```

Let's check on which the pod is scheduled
```
oc get po -o wide
```

Let's now apply disk=ssd label to worker 2 node
```
oc label node worker-2.ocp.tektutor.org.labs disk=ssd
```

If you notice, once the pod is scheduled even if the scheduler find a node that maches the node affinity condition put forth by the pod is available, the Pod won't be moved to the node that matches the criteria once it is already scheduled to some node in the Openshift cluster.

```
oc get po -o wide
```

Now, let's delete the pod
```
cd ~/openshift-march-2024

cd Day4/scheduling
oc delete -f preferred-affinity.yml
```

Now, let's remove the label from worker-2.ocp.tektutor.org.labs node
```
oc label worker-2.ocp.tektutor.org.labs disk-
```
Expected output
<pre>
[jegan@tektutor.org scheduling]$ oc get nodes -l disk=ssd
No resources found
  
[jegan@tektutor.org scheduling]$ oc label node worker-2.ocp.tektutor.org.labs disk=ssd
node/worker-2.ocp.tektutor.org.labs labeled
  
[jegan@tektutor.org scheduling]$ oc get nodes -l disk=ssd
NAME                             STATUS   ROLES    AGE     VERSION
worker-2.ocp.tektutor.org.labs   Ready    worker   7h25m   v1.27.6+f67aeb3
  
[jegan@tektutor.org scheduling]$ oc label node worker-2.ocp.tektutor.org.labs disk-
node/worker-2.ocp.tektutor.org.labs unlabeled
  
[jegan@tektutor.org scheduling]$ oc get nodes -l disk=ssd
No resources found  
</pre>


Let's deploy the pod with required node affinity condition
```
cd ~/openshift-april-2024

cd Day4/scheduling
oc apply -f required-affinity.yml
```

Let's check if the pod status
```
oc get po -o wide
```

Since, the scheduler is not able to find a node that has disk=ssd label, the Pod will remain the Pending status.

Now, let's apply the disk=ssd label to worker2
```
oc label node worker-2.ocp.tektutor.org.labs disk=ssd
```

Now, let's list all the nodes that has label disk=ssd
```
oc get nodes -l disk=ssd
```

Now, we expect the pod to be deployed onto the worker2 as it meets the required criteria
```
oc get po -o wide
```

Finally, let's clean up the pod
```
oc delete -f required-affinity.yml
oc label node worker-2.ocp.tektutor.org.labs disk-
```

## Info - What is a Storage Class in Kubernetes/OpenShift?
- Normally when application needs external storage they request openshift via Persistent Volume Claim(PVC)
- For a PVC to get an external there has to a corresponding matching Persistent Volume whose size, accessmode, storageclass(if any) should match
- In case, openshift doesn't find a PV to satify the PVC request, then the Pod that depends on the PVC will be in the Pending until it gets one Persistent Volume
- Persistent volumes can be provisioned
  - Manually
  - Dynamically using Storage Class
- We can create different types of Storage
  - AWS EBS
  - Azure Disk
  - NFS, etc.,

  ## Lab - Using NFS Storage Class to dynamically provision Persistent Volumes
  ```
  cd ~/openshift-april-2024
  git pull

  cd Day4/nfs-storage-class
  ./deploy.sh
  ```
