#!/bin/bash
export CATALINA_OPTS="-server -Xms40m -Xmx512m -XX:PermSize=256m -XX:MaxPermSize=256m -Djava.net.preferIPv4Stack=true -Djava.util.logging.config.file=conf/javalogger.properties -Dorg.omg.CORBA.ORBClass=org.jacorb.orb.ORB
-Dorg.omg.CORBA.ORBSingletonClass=org.jacorb.orb.ORBSingleton -Dorg.omg.PortableInterceptor.ORBInitializerClass.standard_init=org.jacorb.orb.standardInterceptors.IORInterceptorInitializer -Djacorb.config.dir=conf/"
