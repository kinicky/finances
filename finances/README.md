PA-Finances
=============================

Simple Google App Engine application that reads statements from a CSV file and show some graphs.

## how to run
mvn clean package
mvn appengine:devserver

prod version: http://pa-finances.appspot.com


## deploy in the web
mvn appengine:update


## Erasing DB
remove local_db.bin in 
E:\dev\git\local\finances\target\finances-1.0-SNAPSHOT\WEB-INF\appengine-generated\local_db.bin

## Installing local jar in maven repo
mvn install:install-file -Dfile="E:\dev\git\gmultipart\target\gmultipart-0.4.jar" -DgroupId=gmultipart -DartifactId=gmultipart -Dversion=0.4 -Dpackaging=jar

## Encountered Issues:

-When trying to update GAE:
"Either the access code is invalid or the OAuth token is revoked"
delete C:\Users\kinicky\.appcfg_oauth2_tokens_java


-When trying to upload a file:
http://stackoverflow.com/questions/36477286/spring-4-multipart-nullpointerexception-issue
Had to add ""<property name="java.io.tmpdir" value="/"/> in appengine-web.xml
java.lang.NullPointerException
at java.io.File.<init> (File.java:312)
at org.apache.commons.fileupload.disk.DiskFileItem.getTempFile (DiskFileItem.java:582)
at org.apache.commons.fileupload.disk.DiskFileItem.getOutputStream (DiskFileItem.java:528)
at org.apache.commons.fileupload.FileUploadBase.parseRequest (FileUploadBase.java:347)




