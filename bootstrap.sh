#!/usr/bin/env bash

echo "Bootstrapping SXE SDK."

set -e

# Required because envsetup is a submodule.
echo "Updating git submodules."
git submodule init
git submodule update

echo "Setting up environment."
. ./envsetup/envsetup.sh

# XXX: Buster's glfw3, boost, and vulkan are too old.
echo "Installing dependencies with apt."
sudo apt update
sudo apt install \
    build-essential \
    pkg-config \
    cmake \
    ninja-build \
    libboost-dev \
    libboost-filesystem-dev  \
    libglfw3-dev \
    zlib1g-dev \
    libarchive-dev \
    libcppunit-dev \
    nlohmann-json3-dev \
    libglm-dev \
    libglbinding-dev \
    libvulkan-dev

echo 'Please do ". ./envsetup/envsetup.sh" to setup your environment.'
echo 'Run ".\configure.sh" to use the recommended cmake configuration.'
