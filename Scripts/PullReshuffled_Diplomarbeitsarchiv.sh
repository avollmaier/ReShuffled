#!/bin/bash
echo "Updating..."
cd /
cd home/alois/Schreibtisch/Drive/
cd Reshuffled_Diplomarbeitsarchiv/
git pull --recurse-submodules
git submodule update --remote

git pull origin master
clear
echo "Done"


