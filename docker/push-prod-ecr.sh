$(aws ecr get-login --no-include-email --region us-east-2)
mvn -f ../pom.xml clean package -DskipTests
cp ../target/miic-vaccine-management-service-*.jar service.jar
docker build --build-arg profile=prod -t miic-vaccine-management-service:latest .
docker tag miic-vaccine-management-service:latest 938619397650.dkr.ecr.us-east-2.amazonaws.com/prod/miic-vaccine-management-service
docker push 938619397650.dkr.ecr.us-east-2.amazonaws.com/prod/miic-vaccine-management-service
rm service.jar