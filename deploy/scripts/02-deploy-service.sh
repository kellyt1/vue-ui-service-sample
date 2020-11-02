#! /bin/bash
# 02-deploy-service

source service-env.sh

aws cloudformation deploy --template-file ${TEMPLATEFILE} \
    --stack-name ${STACKNAME} \
    --capabilities CAPABILITY_NAMED_IAM \
    --parameter-overrides \
        Cluster=${CLUSTERNAME} \
        VPCId=${VPC_ID} \
        ContainerName=${CONTAINERNAME} \
        ContainerPort=${CONTAINERPORT} \
        Image=${ECR_IMAGE} \
        Listener=${LISTENER} \
        DesiredCount=${INSTANCE_COUNT} \
        MinHealthyPct=${MIN_HEALTHY_PERCENT} \
        ServiceDomain=${SUBDOMAIN}.${HOSTED_ZONE} \
        AcmCertArn=${CERT_ARN} \
        Ac2Code=${AC2_CODE}