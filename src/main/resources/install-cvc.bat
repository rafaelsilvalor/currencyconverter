@echo off
REM =====================================================================
REM install-cvc.bat
REM Installs the currencyconverter CLI as "cvc" for Windows CMD users
REM Usage: run this script from the repo root in a regular (non-admin) CMD
REM =====================================================================

REM 1) Ensure the JAR exists
set JAR=target\currencyconverter-1.0-SNAPSHOT.jar
if not exist "%JAR%" (
    echo ERROR: Cannot find %JAR%. Please run "mvn clean package" first.
    pause
    exit /b 1
)

REM 2) Create user bin directory if needed
set BIN_DIR=%USERPROFILE%\bin
if not exist "%BIN_DIR%" (
    echo Creating "%BIN_DIR%"...
    mkdir "%BIN_DIR%"
)

REM 3) Write the launcher stub
set STUB=%BIN_DIR%\cvc.bat
> "%STUB%" (
    echo @echo off
    echo java -jar "%~dp0\%JAR%" %%*
)
if exist "%STUB%" (
    echo Installed launcher to "%STUB%"
) else (
    echo Failed to create "%STUB%".
    pause
    exit /b 1
)

REM 4) Add bin folder to User PATH if not already present
echo %PATH% | findstr /I /C:"%BIN_DIR%" >nul
if errorlevel 1 (
    echo Adding "%BIN_DIR%" to your User PATH...
    setx PATH "%%PATH%%;%BIN_DIR%"
    echo Restart your CMD sessions to pick up the new PATH.
) else (
    echo "%BIN_DIR%" is already in your PATH.
)

echo Installation complete! You can now run "cvc" from any CMD window.
pause
