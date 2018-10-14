#!/bin/bash
export SLIMERJSLAUNCHER=/root/firefox/firefox
pwd && cd template && pwd && slimerjs --headless index.js "$1" "$2"
