#!/usr/bin/env bash

ROOT="$(dirname $0)/.."
CONFIG_FILE="$ROOT/src/main/resources/application.properties"
SAMPLE_CONFIG_FILE="$CONFIG_FILE.sample"

set -o xtrace

[[ -f "$CONFIG_FILE" ]] && sed -E -e 's/=.+/=/g' "$CONFIG_FILE" > "$SAMPLE_CONFIG_FILE"
