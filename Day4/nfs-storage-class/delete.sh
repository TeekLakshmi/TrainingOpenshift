
oc delete -f nfs-dynamic-pvc.yml
oc delete -f nfs-second-pvc.yml
oc delete -f nfs-storage-class.yml
oc delete -f nfs-pv-provisioner.yml
oc delete -f rbac.yml
