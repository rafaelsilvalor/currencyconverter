# scripts\install-cvc.ps1
# Installs the currencyconverter CLI as `cvc` by copying the JAR into $HOME\bin
# Expects this .ps1 in scripts\ alongside currencyconverter.jar in the parent dir.

param(
    [string]$ScriptRoot = $PSScriptRoot
)

# 1) Locate the JAR next to this script
$jarSource = Join-Path $ScriptRoot "..\currencyconverter.jar"
if (-not (Test-Path $jarSource)) {
    Write-Error "ERROR: Cannot find JAR at '$jarSource'. Ensure script is in scripts\ and jar is in parent folder."
    exit 1
}

# 2) Create user bin directory if needed
$binDir = Join-Path $HOME "bin"
if (-not (Test-Path $binDir)) {
    Write-Host "Creating directory '$binDir'..."
    New-Item -ItemType Directory -Path $binDir | Out-Null
}

# 3) Copy the JAR into $HOME\bin
$jarName = "currencyconverter.jar"
$destJar = Join-Path $binDir $jarName
Write-Host "Copying '$jarSource' to '$destJar'..."
try {
    Copy-Item -Path $jarSource -Destination $destJar -Force
} catch {
    Write-Error "ERROR: Failed to copy JAR into '$binDir'."
    exit 1
}

# 4) Create the launcher stub
$stubPath = Join-Path $binDir "cvc.bat"
@"
@echo off
REM Launcher for currencyconverter CLI
REM Stub lives alongside the jar in %~dp0
java -jar "%~dp0\$jarName" %*
"@ | Set-Content -Path $stubPath -Encoding ASCII

if (Test-Path $stubPath) {
    Write-Host "Installed launcher stub to '$stubPath'."
} else {
    Write-Error "ERROR: Failed to create launcher stub at '$stubPath'."
    exit 1
}

# 5) Add $HOME\bin to the user PATH if missing
$userPath = [Environment]::GetEnvironmentVariable("Path", "User")
if ($userPath -notlike "*$binDir*") {
    Write-Host "Adding '$binDir' to your User PATH..."
    [Environment]::SetEnvironmentVariable("Path", "$userPath;$binDir", "User")
    Write-Host "You may need to restart your terminal for PATH changes to take effect."
} else {
    Write-Host "'$binDir' is already in your User PATH."
}

Write-Host "Installation complete! You can now run 'cvc' from any new CMD or PowerShell session."
