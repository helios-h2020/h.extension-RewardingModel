if [ $# -ne 3 ]; then
    echo "Please, enter the project and package name as parameter: createProject.sh PROJECT_NAME PACKAGE_DOMAIN.PACKAGE_NAME.PACKAGE_SUFIX OS_BASH: GIT, OSX"
else
    git clone https://gitlab.kazan.atosworldline.com/android-tempos-21/kotlin_multiplatform_base &
    PID=$!
    wait $PID
    path="$(pwd)/kotlin_multiplatform_base"
    echo $path
    if [ -d $path ]; then
        cd $path
        PID=$!
        wait $PID
        if [ $3 = "OSX" ]; then
            sh copy_osx_bash.sh $1 $2
        else
            sh copy_git_bash.sh $1 $2
        fi
        rm -rf $path
    else
        echo "There was an application error. Please, open an issue here: https://gitlab.kazan.atosworldline.com/android-tempos-21/kotlin_multiplatform_base"
    fi

fi
