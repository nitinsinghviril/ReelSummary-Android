#!/bin/bash
# Create directory structure
mkdir -p $HOME/android-sdk/cmdline-tools

# Download and extract tools
cd $HOME/android-sdk/cmdline-tools
wget https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip
unzip commandlinetools-linux-*.zip
mv cmdline-tools latest

# Set Environment Variables for this session
export ANDROID_HOME=$HOME/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# Accept licenses
echo "y" | sdkmanager --licenses

echo "SDK Setup Complete! You can now run ./gradlew assembleDebug"
