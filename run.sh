#!/bin/bash

# Navigate to the script's directory so it can be run from anywhere
cd "$(dirname "$0")"

# Create bin directory if it doesn't exist
mkdir -p bin

# Find all Java source files
find src -name "*.java" > sources.txt

# Compile all Java files
javac -d bin -cp "lib/*" @sources.txt

# Remove the temporary file
rm sources.txt

# Run the main class
java -cp "bin:lib/*" frontend.LoginFrame
