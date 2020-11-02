$(aws ecr get-login --no-include-email --region us-east-2)
mvn -f ../pom.xml clean package -DskipTests
cp ../target/bodyart-service-*.jar service.jar
docker build --build-arg profile=nonprod -t bodyart-api-service:latest .
docker tag bodyart-api-service:latest 938619397650.dkr.ecr.us-east-2.amazonaws.com/nonprod/bodyart-api-service
docker push 938619397650.dkr.ecr.us-east-2.amazonaws.com/nonprod/bodyart-api-service
rm service.jar
aws ecs update-service --cluster hrd-cluster --service 	arn:aws:ecs:us-east-2:938619397650:service/bodyart-api-service-ecs-service-stack-Service-HTBI1S7ZFGZ7 --force-new-deployment
