@echo off

REM 1st param is the ip of the client itself. second param is the IP of the server
java -jar "%~dp0/desktop/build/libs/desktop-1.0.jar" localhost localhost
pause