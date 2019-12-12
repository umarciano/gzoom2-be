echo off
rem -Dgzoom.conf.dir=C://projects/Gzoom2/config -Dserver.port=8081
echo on
set JAVA_HOME="C:\app\jdk-11.0.2"
"%JAVA_HOME%\bin\java" -Xms128M -Xmx512M -XX:MaxPermSize=128m -Dserver.port=6000 -Dspring.config.additional-location=C:\\app\\GZoom_PrivacyGDPR2\\config\\gzoom.properties -Dgzoom.conf.dir=C:\\app\\GZoom_PrivacyGDPR2\\config -jar rest-boot.jar > C:\\app\\GZoom_PrivacyGDPR2\\logs\\gzoom2-be.out
