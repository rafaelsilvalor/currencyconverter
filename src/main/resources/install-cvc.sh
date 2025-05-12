#!/usr/bin/env bash
#
# scripts/install-cvc.sh
# Installs the currencyconverter CLI as `cvc` by copying the jar into /usr/local/share/cvc
# Expects this script in scripts/ alongside currencyconverter.jar in the parent dir.

set -euo pipefail

# 1) Locate your built JAR next to this script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAR_SOURCE="$SCRIPT_DIR/../currencyconverter.jar"
if [[ ! -f "$JAR_SOURCE" ]]; then
  echo "ERROR: Cannot find JAR at $JAR_SOURCE"
  echo "Make sure this script lives in scripts/ and jar is next to it."
  exit 1
fi

# 2) Define install locations
SHARE_DIR="/usr/local/share/cvc"
BIN_LAUNCHER="/usr/local/bin/cvc"
JAR_NAME="$(basename "$JAR_SOURCE")"
DEST_JAR="$SHARE_DIR/$JAR_NAME"

# 3) Copy the JAR into /usr/local/share/cvc
echo "Creating $SHARE_DIR (if needed) and copying JAR..."
sudo mkdir -p "$SHARE_DIR"
sudo cp "$JAR_SOURCE" "$DEST_JAR"
sudo chown root:root "$DEST_JAR"
sudo chmod 644 "$DEST_JAR"

# 4) Create the launcher script in /usr/local/bin
echo "Writing launcher to $BIN_LAUNCHER..."
sudo tee "$BIN_LAUNCHER" > /dev/null <<EOF
#!/usr/bin/env bash
# launcher for currencyconverter CLI
# lives in /usr/local/bin, points to jar in /usr/local/share/cvc
exec java -jar "$DEST_JAR" "\$@"
EOF
sudo chown root:root "$BIN_LAUNCHER"
sudo chmod 755 "$BIN_LAUNCHER"

echo
echo "Installation complete!"
echo "  • JAR copied to:     $DEST_JAR"
echo "  • Launcher created:  $BIN_LAUNCHER"
echo
echo "You can now run 'cvc' from any terminal session."
