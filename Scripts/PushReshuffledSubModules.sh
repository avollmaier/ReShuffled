#!/bin/bash
echo "Updating..."

cd /
cd home/alois/Schreibtisch/Drive/Reshuffled_Diplomarbeitsarchiv/
cd Reshuffled_Dokumentation/
git checkout master
git pull origin master
git add .
git commit -m "general commit of submodule"
git push origin master

cd /
cd home/alois/Schreibtisch/Drive/Reshuffled_Diplomarbeitsarchiv/
cd Reshuffled_Elektronik/
git checkout master
git pull origin master
git add .
git commit -m "general commit of submodule"
git push origin master

cd /
cd home/alois/Schreibtisch/Drive/Reshuffled_Diplomarbeitsarchiv/
cd Reshuffled_Informatik/
git checkout master
git pull origin master
git add .
git commit -m "general commit of submodule"
git push origin master

cd /
cd home/alois/Schreibtisch/Drive/Reshuffled_Diplomarbeitsarchiv/
cd Reshuffled_Mechanik/
git checkout master
git pull origin master
git add .
git commit -m "general commit of submodule"
git push origin master

cd /
cd home/alois/Schreibtisch/Drive/Reshuffled_Diplomarbeitsarchiv/

git checkout master
git pull origin master
git add .
git commit -m "general commit of master module"
git push origin master

echo "Done"


