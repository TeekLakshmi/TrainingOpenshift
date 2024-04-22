oc apply -f mongodb-pv.yml
oc apply -f mongodb-pvc.yml
oc apply -f mongodb-deploy.yml
oc apply -f mongodb-svc.yml

oc get pv,pvc,po

