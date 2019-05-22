#!/bin/bash
echo "Updating..."
cd ..
git pull --recurse-submodules
git submodule update --remote
git pull origin master
clear
echo "Done"


