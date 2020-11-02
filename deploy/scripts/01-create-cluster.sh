#! /bin/bash
# 01-create-cluster

source cluster-env.sh

aws cloudformation deploy --template-file ${TEMPLATEFILE} \
    --stack-name ${STACKNAME} \
    --capabilities CAPABILITY_NAMED_IAM \
    --parameter-overrides \
        EnvironmentName=${CLUSTERNAME} \
        VPCId=${VPC_ID} \
        PublicSubnetIds=${PUBLIC_SUBNETS} \
        PrivateSubnetIds=${PRIVATE_SUBNETS} \
        InstanceType=${INSTANCE_TYPE} \
        ClusterSize=${INSTANCE_COUNT} \
        HostedZoneName=${HOSTED_ZONE} \
        AcmCertArn=${CERT_ARN} \
        Ac2Code=${AC2_CODE}