# install-cvc.ps1
# Installs the currencyconverter CLI as `cvc` by creating a batch stub and adding to user PATH

param(
    [string]$JarPath = (Join-Path (Get-Location) "target\currencyconverter-1.0-SNAPSHOT.jar")
)

if (-not (Test-Path $JarPath)) {
    Write-Error "Cannot find JAR at $JarPath"; exit 1
}

# 1) Create the stub batch file in a local bin folder
$binDir = Join-Path $HOME "bin"
if (-not (Test-Path $binDir)) {
    New-Item -ItemType Directory -Path $binDir | Out-Null
}

$stubPath = Join-Path $binDir "cvc.bat"
@"
@echo off
java -jar "$JarPath" %*
"@ | Set-Content -Path $stubPath -Encoding ASCII

# 2) Add $HOME\bin to the user PATH if not already present
$userPath = [Environment]::GetEnvironmentVariable("Path", "User")
if ($userPath -notlike "*$binDir*") {
    [Environment]::SetEnvironmentVariable("Path", "$userPath;$binDir", "User")
    Write-Host "Added $binDir to user PATH"
} else {
    Write-Host "$binDir is already in user PATH"
}

Write-Host "Installed 'cvc' stub to $stubPath"
Write-Host "You may need to restart your terminal for PATH changes to take effect."
