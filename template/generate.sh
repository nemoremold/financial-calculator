#!/bin/bash
pwd && cd template && pwd && slimerjs --headless index.js "$1" "$2"