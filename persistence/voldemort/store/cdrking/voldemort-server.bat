set VOLDEMORT_HOME=../../runtimes/voldemort
set CLASSPATH=%VOLDEMORT_HOME%/lib/*;%VOLDEMORT_HOME%/dist/*;
set VOLD_OPTS=-Xmx100m -server -Dcom.sun.management.jmxremote
set LOG4JCONF=-Dlog4j.configuration=log4j.properties
set CONFIG_PATH=%VOLDEMORT_HOME%/../../store/cdrking
java %LOG4JCONF% %VOLD_OPTS% -cp %CLASSPATH% voldemort.server.VoldemortServer %CONFIG_PATH%

pause