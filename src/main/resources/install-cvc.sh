#!/usr/bin/env bash
#
# install-cvc.sh
# Installs the currencyconverter CLI as `cvc` by placing a launcher in /usr/local/bin

set -e

# 1) Locate your built JAR (adjust if your artifactId/version differ)
JAR_PATH="$(pwd)/target/currencyconverter-1.0-SNAPSHOT.jar"

if [[ ! -f "$JAR_PATH" ]]; then
  echo "ERROR: Cannot find JAR at $JAR_PATH"
  exit 1
fi

# 2) Create launcher script
LAUNCHER="/usr/local/bin/cvc"
sudo tee "$LAUNCHER" > /dev/null <<EOF
#!/usr/bin/env bash
java -jar "$JAR_PATH" "\$@"
EOF

# 3) Make it executable
sudo chmod +x "$LAUNCHER"

echo "Installed 'cvc' launcher to $LAUNCHER"
echo "You can now run 'cvc' from any terminal."
