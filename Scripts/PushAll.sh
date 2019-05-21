#!/bin/bash

progress-bar() {
  local duration=${1}


    already_done() { for ((done=0; done<$elapsed; done++)); do printf "▇"; done }
    remaining() { for ((remain=$elapsed; remain<$duration; remain++)); do printf " "; done }
    percentage() { printf "| %s%%" $(( (($elapsed)*100)/($duration)*100/100 )); }
    clean_line() { printf "\r"; }

  for (( elapsed=1; elapsed<=$duration; elapsed++ )); do
      already_done; remaining; percentage
      sleep 1
      clean_line
  done
  clean_line
}

clear
progress-bar 12
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


