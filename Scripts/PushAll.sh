#!/bin/bash
echo "Updating..."
cd ..
cd Reshuffled_Dokumentation/
git checkout master
git pull origin master
git add .
git commit -m "general commit of submodule dokumentation"
git push origin master
cd ..
cd Reshuffled_Elektronik/
git checkout master
git pull origin master
git add .
git commit -m "general commit of submodule elektronik"
git push origin master
cd ..
cd Reshuffled_Informatik/
git checkout master
git pull origin master
git add .
git commit -m "general commit of submodule informatik"
git push origin master
cd ..
cd Reshuffled_Mechanik/
git checkout master
git pull origin master
git add .
git commit -m "general commit of submodule mechanik"
git push origin master
cd ..
git checkout master
git pull origin master
git add .
git commit -m "general commit of master module"
git push origin master
clear
echo "Done"

echo -ne '#####                     (33%)\r'
sleep 1
echo -ne '#############             (66%)\r'
sleep 1
echo -ne '#######################   (100%)\r'
echo -ne '\n'


