oc apply -f rbac.yml
oc apply -f nfs-pv-provisioner.yml
oc apply -f nfs-storage-class.yml

# Creates a Persistent Volume claim
oc apply -f nfs-dynamic-pvc.yml

# Creates a Persistent Volume Claim 2 with different size and access mode
oc apply -f nfs-second-pvc.yml

