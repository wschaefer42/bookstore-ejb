# Bookstore EJB Sample

## Setup
Glassfish was installed using Homebrew on macOS
````shell
asadmin create-domain --instanceport 8092 --adminport 4852 domain2
asadmin start-domain -v domain2
export GLASSFISH_HOME=/opt/homebrew/opt/glassfish/libexec
````

### Glassfish
Set DEPLOYMENT_DIR environment variable
````shell
export DEPLOYMENT_DIR=$GLASSFISH_HOME/glassfish/domains/domain2/autodeploy
````

### WildFly
JBoss WildFly was installed with Homebrew on macOS
````shell
export JBOSS_HOME=/opt/homebrew/opt/wildfly-as/libexec
export DEPLOYMENT_DIR=$JBOSS_HOME/standalone/deployments
````