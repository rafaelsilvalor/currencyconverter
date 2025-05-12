@echo off
REM =====================================================================
REM scripts\install-cvc.bat
REM Installs the currencyconverter CLI as "cvc" for Windows CMD users
REM Expects this .bat to live in scripts\ alongside currencyconverter.jar in the parent dir.
REM =====================================================================

REM 1) Locate the JAR next to this script
set "SCRIPT_DIR=%~dp0"
set "JAR=%SCRIPT_DIR%..\currencyconverter.jar"
if not exist "%JAR%" (
    echo ERROR: Cannot find "%JAR%". Make sure this script is in scripts\ and jar is in the parent folder.
    pause
    exit /b 1
)

REM 2) Create user bin directory if needed
set "BIN_DIR=%USERPROFILE%\bin"
if not exist "%BIN_DIR%" (
    echo Creating "%BIN_DIR%"...
    mkdir "%BIN_DIR%"
)

REM 3) Copy the JAR into %USERPROFILE%\bin
echo Copying jar to "%BIN_DIR%"...
copy /Y "%JAR%" "%BIN_DIR%\" >nul || (
    echo ERROR: Failed to copy jar into "%BIN_DIR%".
    pause
    exit /b 1
)

REM 4) Write the launcher stub
set "JAR_NAME=currencyconverter.jar"
set "STUB=%BIN_DIR%\cvc.bat"
> "%STUB%" (
    echo @echo off
    echo REM Launcher for currencyconverter CLI
    echo REM Stub lives alongside the jar in %%~dp0
    echo java -jar "%%~dp0\%JAR_NAME%" %%*
)
if exist "%STUB%" (
    echo Installed launcher to "%STUB%"
) else (
    echo ERROR: Failed to create launcher stub "%STUB%".
    pause
    exit /b 1
)

REM 5) Add bin folder to User PATH if missing
echo %PATH% | findstr /I /C:"%BIN_DIR%" >nul
if errorlevel 1 (
    echo Adding "%BIN_DIR%" to User PATH...
    setx PATH "%%PATH%%;%BIN_DIR%"
    echo Restart your CMD sessions to pick up the new PATH.
) else (
    echo "%BIN_DIR%" is already in your PATH.
)

echo.
echo Installation complete! You can now run "cvc" from any CMD window.
pause
