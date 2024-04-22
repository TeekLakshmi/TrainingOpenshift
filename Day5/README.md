# Day 5

## Rolling update
- helps us upgrade your microservice or application from one version to other without any downtime
- you just need to update the container image version to whatever version you need to upgrade and apply the yaml files
- you could even undo, check all revisions, downgrade to any lower version of your application

## Lab - Nginx Rolling update from v1.18 to v1.19

Deploy nginx v1.18

When you edit the deploy, make sure you are not really changing anything but observe the version of nginx image. You should see the deployment using bitnami/nginx:1.18
```
cd ~/openshift-april-2024
git pull

cd Day5/rolling-update
oc apply -f nginx-deploy.yml
oc apply -f nginx-clusterip-svc.yml
oc apply -f nginx-route.yml
oc get deploy,rs,po
oc edit deploy/nginx
```

Now let's do rolling update ( upgrade nginx from v1.18 to v1.19. Edit the nginx-deploy.yml file and update the bitnami/nginx:1.18 to bitnami/nginx:1.19 save the file and apply
```
oc apply -f nginx-deploy.yml
oc edit deploy/nginx
```

You could check the status of the rolling update
```
oc rollout status deploy/nginx
```

You list all revisions
```
oc rollout history deploy/nginx
```

You can undo back to older version
```
oc rollout undo deploy/nginx
```
![rolling update](rolling-update.png)

## Knative Serverless
<pre>
- Knative is an open source community project 
- adds components for deploying, running, and managing serverless, cloud-native applications to Kubernetes/Openshift
- The serverless cloud computing model can lead to increased developer productivity and reduced operational costs
- Knative eliminates the tasks of provisioning and managing servers
- code only runs when it needs to, with Knative starting and stopping instances automatically
- Operations costs can be reduced — pay for cloud-based compute time on demand, instead of running and managing your own servers all the time

- Knative consists of 3 primary components:
  - Build - A flexible approach to building source code into containers
  - Serving - Enables rapid deployment and automatic scaling of containers through a request-driven model for serving workloads based on demand
  - Eventing - An infrastructure for consuming and producing events to stimulate applications. Applications can be triggered by a variety of sources, such as events from your own applications, cloud services from multiple providers, Software-as-a-Service (SaaS) systems, and Red Hat AMQ streams
</pre>

## Lab - Deploying a knative service
```
kn service create hello \
--image ghcr.io/knative/helloworld-go:latest \
--port 8080 \
--env TARGET=World
```

Expected output
<pre>
[jegan@tektutor.org ~]$ kn service create hello \
--image ghcr.io/knative/helloworld-go:latest \
--port 8080 \
--env TARGET=World
Warning: Kubernetes default value is insecure, Knative may default this to secure in a future release: spec.template.spec.containers[0].securityContext.allowPrivilegeEscalation, spec.template.spec.containers[0].securityContext.capabilities, spec.template.spec.containers[0].securityContext.runAsNonRoot, spec.template.spec.containers[0].securityContext.seccompProfile
Creating service 'hello' in namespace 'jegan':

  0.051s The Route is still working to reflect the latest desired specification.
  0.063s ...
  0.084s Configuration "hello" is waiting for a Revision to become ready.
  5.347s ...
  5.385s Ingress has not yet been reconciled.
  5.457s Waiting for load balancer to be ready
  5.618s Ready to serve.

Service 'hello' created to latest revision 'hello-00001' is available at URL:
https://hello-jegan.apps.ocp4.tektutor.org.labs  
</pre>

Listing the knative services
```
kn service list
kubectl get ksvc
```

#### Accessing the knative application
```
curl -k https://hello-jegan.apps.ocp4.tektutor.org.labs
```

Expected output
<pre>
[jegan@tektutor.org ~]$ curl -k https://hello-jegan.apps.ocp4.tektutor.org.labs
Hello World!
</pre>

#### Update the service
```
kn service update hello \
--env TARGET=Knative

kn revisions list
```

Expected output
<pre>
[jegan@tektutor.org ~]$ kn service update hello \
--env TARGET=Knative
Warning: Kubernetes default value is insecure, Knative may default this to secure in a future release: spec.template.spec.containers[0].securityContext.allowPrivilegeEscalation, spec.template.spec.containers[0].securityContext.capabilities, spec.template.spec.containers[0].securityContext.runAsNonRoot, spec.template.spec.containers[0].securityContext.seccompProfile
Updating Service 'hello' in namespace 'jegan':

  0.035s The Configuration is still working to reflect the latest desired specification.
  3.410s Traffic is not yet migrated to the latest revision.
  3.454s Ingress has not yet been reconciled.
  3.509s Waiting for load balancer to be ready
  3.666s Ready to serve.

Service 'hello' updated to latest revision 'hello-00002' is available at URL:
https://hello-jegan.apps.ocp4.tektutor.org.labs  

[jegan@tektutor.org ~]$ kn revision list
NAME          SERVICE   TRAFFIC   TAGS   GENERATION   AGE   CONDITIONS   READY   REASON
hello-00002   hello     100%             2            55s   4 OK / 4     True    
hello-00001   hello                      1            10m   3 OK / 4     True    
</pre>

#### Splitting the traffic between two revisions
```
kn service update hello \
--traffic hello-00001=50 \
--traffic @latest=50

kn revisions list
```

Expected output
<pre>
[jegan@tektutor.org ~]$ kn service update hello \
--traffic hello-00001=50 \
--traffic @latest=50
Warning: Kubernetes default value is insecure, Knative may default this to secure in a future release: spec.template.spec.containers[0].securityContext.allowPrivilegeEscalation, spec.template.spec.containers[0].securityContext.capabilities, spec.template.spec.containers[0].securityContext.runAsNonRoot, spec.template.spec.containers[0].securityContext.seccompProfile
Updating Service 'hello' in namespace 'jegan':

  0.035s The Route is still working to reflect the latest desired specification.
  0.064s Ingress has not yet been reconciled.
  0.084s Waiting for load balancer to be ready
  0.277s Ready to serve.

Service 'hello' with latest revision 'hello-00002' (unchanged) is available at URL:
https://hello-jegan.apps.ocp4.tektutor.org.labs
  
[jegan@tektutor.org ~]$ kn revision list
NAME          SERVICE   TRAFFIC   TAGS   GENERATION   AGE   CONDITIONS   READY   REASON
hello-00002   hello     50%              2            88s   3 OK / 4     True    
hello-00001   hello     50%              1            10m   3 OK / 4     True      
</pre>

#### Accessing the knative application
```
kn revision list
curl -H 'Pragma: no-cache' -k "$(kn service describe hello -o url)"
curl -H 'Pragma: no-cache' -k "$(kn service describe hello -o url)"
curl -H 'Pragma: no-cache' -k "$(kn service describe hello -o url)"
curl -H 'Pragma: no-cache' -k "$(kn service describe hello -o url)"
```

Expected output
<pre>
[jegan@tektutor.org ~]$ kn revision list
NAME          SERVICE   TRAFFIC   TAGS   GENERATION   AGE     CONDITIONS   READY   REASON
hello-00002   hello     50%              2            3m34s   3 OK / 4     True    
hello-00001   hello     50%              1            12m     3 OK / 4     True    
[jegan@tektutor.org ~]$ #curl -H 'Pragma: no-cache' -k "$(kn service describe hello -o url)"
[jegan@tektutor.org ~]$ curl -H 'Pragma: no-cache' -k "$(kn service describe hello -o url)"
Hello Knative!
[jegan@tektutor.org ~]$ curl -H 'Pragma: no-cache' -k "$(kn service describe hello -o url)"
Hello World!
[jegan@tektutor.org ~]$ curl -H 'Pragma: no-cache' -k "$(kn service describe hello -o url)"
Hello Knative!
[jegan@tektutor.org ~]$ curl -H 'Pragma: no-cache' -k "$(kn service describe hello -o url)"
Hello Knative!
</pre>

## Lab - Delete the knative service
```
kn service list
kn service delete hello
kn service list
```

Expected output
<pre>
[jegan@tektutor.org ~]$ kn service list
NAME    URL                                               LATEST        AGE   CONDITIONS   READY   REASON
hello   https://hello-jegan.apps.ocp4.tektutor.org.labs   hello-00002   16m   3 OK / 3     True    
[jegan@tektutor.org ~]$ kn service delete hello
Service 'hello' successfully deleted in namespace 'jegan'.
  
[jegan@tektutor.org ~]$ kn service list
No services found.  
</pre>

## Lab - knative eventing

