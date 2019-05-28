#!/bin/bash
echo "Updating..."

cd ..
cd RSdocs/
git checkout master
git pull origin master
git add .
git commit -m "script commit | RSdocs"
git push origin master

cd ..
cd RSelectro/
git checkout master
git pull origin master
git add .
git commit -m "script commit | RSelectro"
git push origin master

cd ..
cd RSinfo/
git checkout master
git pull origin master
git add .
git commit -m "script commit | RSinfo"
git push origin master

cd ..
cd RSmech/
git checkout master
git pull origin master
git add .
git commit -m "script commit | RSmech"
git push origin master

cd ..
git checkout master
git pull origin master
git add .
git commit -m "script commit | RSmaster"
git push origin master

clear
echo "Done"


