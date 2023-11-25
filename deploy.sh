#instead of using explicit params, you can change ~/.m2/setting.xml file and specify in configuration how to deploy project
DEPLOY_USERNAME=deployment
DEPLOY_PASSWORD=123
mvn tomcat7:deploy -Dtomcat.username=${DEPLOY_USERNAME} -Dtomcat.password=${DEPLOY_PASSWORD}

